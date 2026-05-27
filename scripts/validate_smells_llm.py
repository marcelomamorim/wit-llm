#!/usr/bin/env python3
"""
validate_smells_llm.py — LLM-based test smell detection via OpenAI Batch API

Aplica a metodologia de Melo et al. "Agentic LMs: Hunting Down Test Smells"
(arXiv:2504.07277), adaptada para testes no formato jtreg.

Detecta 5 smells canônicos do paper (3 sobrepostos com detect_test_smells.py
+ 2 novos: Duplicate Assert e Magic Number) para cross-validar os resultados
da análise estática.

Fases (pipeline independente — cada uma pode ser rodada separadamente):

  Fase 1 — Preparar:
    python3 scripts/validate_smells_llm.py --run-dir <path> --prepare
    → gera smells-results/llm-validation/batch_requests.jsonl

  Fase 2 — Submeter ao OpenAI Batch:
    OPENAI_API_KEY=... python3 scripts/validate_smells_llm.py \\
        --run-dir <path> --submit
    → imprime batch_id, salva em smells-results/llm-validation/batch_state.json

  Fase 3 — Coletar resultados (quando batch estiver completo):
    OPENAI_API_KEY=... python3 scripts/validate_smells_llm.py \\
        --run-dir <path> --collect [--batch-id <id>]
    → salva smells-results/llm-validation/batch_results.jsonl

  Fase 4 — Analisar e cross-validar:
    python3 scripts/validate_smells_llm.py --run-dir <path> --analyze
    → gera smells-results/llm-validation/comparison.json
       e imprime tabela de concordância

Estimativa de custo (gpt-4.1-nano batch, 631 testes × 5 smells/chamada):
  Tokens input:  ~1,2M  → $0,06
  Tokens output: ~0,25M → $0,05
  Total: ~$0,10 para o run atual (631 testes)

Referência:
  Melo et al. (2025). Agentic LMs: Hunting Down Test Smells. arXiv:2504.07277
"""

import argparse
import json
import os
import sys
import time
from pathlib import Path
from typing import Optional

# ── Definições dos smells (adaptadas para jtreg) ──────────────────────────────
# Baseadas nas definições do paper (Seção 2) com ajuste para ausência de
# assertThrows no jtreg. O padrão esperado de exception no jtreg é:
#   try { method(); throw new AssertionError("expected"); } catch (E e) { }
# Este padrão é CORRETO e NÃO é um smell.

SMELL_DEFINITIONS = {
    "assertion_roulette": {
        "label": "Assertion Roulette",
        "definition": (
            "The test method contains more than one assertion statement "
            "(assertEquals, assertTrue, assertFalse, assertNotNull, assertNull, etc.) "
            "without a descriptive explanation message. An explanation message is a "
            "String literal passed as the first argument to the assertion. "
            "To mitigate this smell, add a message to each assertion."
        ),
    },
    "conditional_logic": {
        "label": "Conditional Test Logic",
        "definition": (
            "The test method contains one or more control statements: "
            "if/else, for loop, while loop, or switch statement. "
            "This makes the test non-deterministic and harder to understand. "
            "Each branch of logic should have its own dedicated test method."
        ),
    },
    "duplicate_assert": {
        "label": "Duplicate Assert",
        "definition": (
            "The test method contains more than one assertion statement with "
            "exactly the same arguments (same method and same parameter values). "
            "Duplicate assertions test the same condition multiple times without "
            "adding any additional value."
        ),
    },
    "exception_handling": {
        "label": "Exception Handling",
        "definition": (
            "The test method contains a try-catch block that is NOT the standard "
            "jtreg expected-exception testing pattern. "
            "The CORRECT jtreg expected-exception pattern (NOT a smell) is: "
            "  try { methodUnderTest(); throw new AssertionError(\"Expected exception not thrown\"); } "
            "  catch (SpecificExpectedException e) { /* expected — empty body or brief comment */ } "
            "A try-catch IS a smell when: (a) it catches a very broad exception type like "
            "Exception or Throwable without good reason, (b) the catch block silently "
            "suppresses errors with no assertion or rethrow, or (c) the try block does NOT "
            "contain a throw new AssertionError(...) statement before the catch. "
            "Note: jtreg tests do NOT have access to assertThrows()."
        ),
    },
    "magic_number": {
        "label": "Magic Number",
        "definition": (
            "The test method contains an assertion with a hard-coded numeric literal "
            "that is not assigned to a named constant or a local variable with a "
            "descriptive name. For example: assertEquals(42, result) is a smell when "
            "42 has no associated name explaining what it represents. "
            "String literals are not considered Magic Number."
        ),
    },
}

# Mapeamento: smell LLM → smell(s) correspondente(s) no detect_test_smells.py
SMELL_MAPPING_TO_STATIC = {
    "assertion_roulette": ["assertion_roulette"],
    "conditional_logic":  ["conditional_logic"],
    "duplicate_assert":   [],           # novo — sem correspondência direta
    "exception_handling": ["exception_catching", "empty_catch"],
    "magic_number":       [],           # novo — sem correspondência direta
}

# Prompt de sistema (mencionado apenas uma vez via cache de contexto)
SYSTEM_PROMPT = (
    "You are a coding assistant with many years of experience that detects test smells.\n"
    "These tests use jtreg format: they have a /* @test */ comment header and a "
    "public static void main(String[] args) method instead of @Test annotations. "
    "There is no assertThrows() available in jtreg.\n"
    "The pattern:\n"
    "  try {\n"
    "      methodUnderTest();\n"
    "      throw new AssertionError(\"Expected exception not thrown\");\n"
    "  } catch (SomeSpecificException e) { /* expected */ }\n"
    "is CORRECT jtreg code for testing expected exceptions and is NOT an Exception Handling smell."
)

# Template de prompt para detecção (um por smell)
DETECT_PROMPT_TEMPLATE = (
    "Your goal is to determine if the provided test code exhibits the test smell "
    '"{smell_label}".\n\n'
    "{smell_definition}\n\n"
    "Test code to analyze:\n"
    "```java\n{code}\n```\n\n"
    'If the test code contains {smell_label}, respond with EXACTLY "YES" on the first line '
    "and explain why in 1-2 sentences. "
    'If it does not contain the smell, respond with EXACTLY "NO" on the first line '
    "and explain why not in 1 sentence. Ignore code comments."
)

# Prompt alternativo: detectar todos os smells em uma única chamada (mais barato)
ALL_SMELLS_PROMPT_TEMPLATE = (
    "Analyze the following jtreg test code for EXACTLY these 5 test smells.\n"
    "For each smell, output one line: SMELL_KEY: YES|NO — reason (1 sentence).\n\n"
    "Smells to check:\n"
    "1. assertion_roulette — {assertion_roulette}\n"
    "2. conditional_logic — {conditional_logic}\n"
    "3. duplicate_assert — {duplicate_assert}\n"
    "4. exception_handling — {exception_handling}\n"
    "5. magic_number — {magic_number}\n\n"
    "Test code:\n"
    "```java\n{code}\n```\n\n"
    "Output format (exactly 5 lines, one per smell):\n"
    "assertion_roulette: YES|NO — reason\n"
    "conditional_logic: YES|NO — reason\n"
    "duplicate_assert: YES|NO — reason\n"
    "exception_handling: YES|NO — reason\n"
    "magic_number: YES|NO — reason"
)


# ── Utilitários ───────────────────────────────────────────────────────────────

def load_test_files(variants_root: Path) -> dict[str, list[Path]]:
    """Retorna {variant_name: [path, ...]} de todos os .java gerados."""
    result = {}
    for variant in ["direct-tests", "wit-context"]:
        gen_dir = variants_root / variant / "test" / "jdk" / "witup" / "generated"
        if gen_dir.exists():
            files = sorted(gen_dir.rglob("*.java"))
            result[variant] = files
            print(f"  {variant}: {len(files)} testes encontrados")
        else:
            print(f"  AVISO: {gen_dir} não encontrado", file=sys.stderr)
            result[variant] = []
    return result


def count_tokens_approx(text: str) -> int:
    """Estimativa rápida: 1 token ≈ 4 chars para código Java."""
    return len(text) // 4


def estimate_batch_cost(requests: list[dict]) -> dict:
    """Estima custo do batch em USD (gpt-4.1-nano batch pricing)."""
    total_input = 0
    total_output_est = 0
    for req in requests:
        msgs = req["body"]["messages"]
        for msg in msgs:
            total_input += count_tokens_approx(msg["content"])
        # Output estimado: ~300 tokens por arquivo (5 linhas YES/NO com justificativa)
        total_output_est += 300
    # gpt-4.1-nano batch: $0.05/1M input, $0.20/1M output
    cost_input  = (total_input  / 1_000_000) * 0.05
    cost_output = (total_output_est / 1_000_000) * 0.20
    return {
        "estimated_input_tokens":  total_input,
        "estimated_output_tokens": total_output_est,
        "estimated_cost_usd":      round(cost_input + cost_output, 4),
        "pricing_note":            "gpt-4.1-nano batch: $0.05/1M input, $0.20/1M output",
    }


# ── Fase 1: Preparar ──────────────────────────────────────────────────────────

def cmd_prepare(run_dir: Path, model: str, strategy: str) -> None:
    """
    Cria smells-results/llm-validation/batch_requests.jsonl
    strategy: 'combined'  → 1 chamada por arquivo (todos os smells juntos, mais barato)
              'per_smell'  → 5 chamadas por arquivo (uma por smell, mais preciso)
    """
    out_dir = run_dir / "smells-results" / "llm-validation"
    out_dir.mkdir(parents=True, exist_ok=True)

    variants_root = run_dir / "variants"
    print("Carregando testes...")
    files_by_variant = load_test_files(variants_root)

    all_definitions = {k: v["definition"] for k, v in SMELL_DEFINITIONS.items()}

    requests = []
    file_index = []   # rastreia (custom_id → variant, file_path, smell[optional])

    for variant, files in files_by_variant.items():
        for java_file in files:
            try:
                code = java_file.read_text(encoding="utf-8", errors="replace")
            except Exception as e:
                print(f"  SKIP {java_file}: {e}", file=sys.stderr)
                continue

            # Truncar arquivos grandes para conter dentro do limite do modelo
            # Paper usa ≤30 LOC — nós usamos até 200 linhas para cobrir mais casos
            lines = code.splitlines()
            if len(lines) > 200:
                code = "\n".join(lines[:200]) + "\n// [truncado em 200 linhas]"

            rel_path = str(java_file.relative_to(run_dir))

            if strategy == "combined":
                # Uma chamada por arquivo, todos os 5 smells
                custom_id = f"{variant}|{rel_path}"
                user_content = ALL_SMELLS_PROMPT_TEMPLATE.format(
                    code=code, **all_definitions
                )
                req = {
                    "custom_id": custom_id,
                    "method": "POST",
                    "url": "/v1/chat/completions",
                    "body": {
                        "model": model,
                        "messages": [
                            {"role": "system", "content": SYSTEM_PROMPT},
                            {"role": "user",   "content": user_content},
                        ],
                        "temperature": 0.0,
                        "max_tokens": 400,
                    },
                }
                requests.append(req)
                file_index.append({
                    "custom_id": custom_id,
                    "variant": variant,
                    "file": rel_path,
                    "strategy": "combined",
                    "loc": len(lines),
                })

            else:  # per_smell
                for smell_key, smell_info in SMELL_DEFINITIONS.items():
                    custom_id = f"{variant}|{rel_path}|{smell_key}"
                    user_content = DETECT_PROMPT_TEMPLATE.format(
                        smell_label=smell_info["label"],
                        smell_definition=smell_info["definition"],
                        code=code,
                    )
                    req = {
                        "custom_id": custom_id,
                        "method": "POST",
                        "url": "/v1/chat/completions",
                        "body": {
                            "model": model,
                            "messages": [
                                {"role": "system", "content": SYSTEM_PROMPT},
                                {"role": "user",   "content": user_content},
                            ],
                            "temperature": 0.0,
                            "max_tokens": 200,
                        },
                    }
                    requests.append(req)
                    file_index.append({
                        "custom_id": custom_id,
                        "variant": variant,
                        "file": rel_path,
                        "smell": smell_key,
                        "strategy": "per_smell",
                        "loc": len(lines),
                    })

    # Salvar JSONL
    jsonl_path = out_dir / "batch_requests.jsonl"
    with open(jsonl_path, "w", encoding="utf-8") as f:
        for req in requests:
            f.write(json.dumps(req, ensure_ascii=False) + "\n")

    # Salvar índice
    index_path = out_dir / "file_index.json"
    with open(index_path, "w", encoding="utf-8") as f:
        json.dump(file_index, f, indent=2, ensure_ascii=False)

    # Estimativa de custo
    cost = estimate_batch_cost(requests)

    # Salvar metadata
    meta = {
        "model": model,
        "strategy": strategy,
        "total_requests": len(requests),
        "variants": {v: len(fs) for v, fs in files_by_variant.items()},
        "cost_estimate": cost,
        "jsonl_path": str(jsonl_path),
        "index_path": str(index_path),
    }
    meta_path = out_dir / "prepare_meta.json"
    with open(meta_path, "w", encoding="utf-8") as f:
        json.dump(meta, f, indent=2, ensure_ascii=False)

    print(f"\n✅ Preparação concluída:")
    print(f"   Requisições: {len(requests)}")
    print(f"   Estratégia:  {strategy}")
    print(f"   Tokens input estimados: {cost['estimated_input_tokens']:,}")
    print(f"   Custo estimado:         USD ${cost['estimated_cost_usd']:.4f}")
    print(f"   JSONL:  {jsonl_path}")
    print(f"   Índice: {index_path}")
    print(f"\nPróximo passo:")
    print(f"  OPENAI_API_KEY=... python3 {sys.argv[0]} --run-dir {run_dir} --submit")


# ── Fase 2: Submeter ──────────────────────────────────────────────────────────

def cmd_submit(run_dir: Path) -> None:
    """Submete o batch_requests.jsonl ao OpenAI Batch API."""
    try:
        from openai import OpenAI
    except ImportError:
        print("ERRO: openai não instalado. Execute: pip install openai", file=sys.stderr)
        sys.exit(1)

    api_key = os.environ.get("OPENAI_API_KEY")
    if not api_key:
        print("ERRO: OPENAI_API_KEY não definida.", file=sys.stderr)
        sys.exit(1)

    out_dir = run_dir / "smells-results" / "llm-validation"
    jsonl_path = out_dir / "batch_requests.jsonl"
    if not jsonl_path.exists():
        print(f"ERRO: {jsonl_path} não encontrado. Execute --prepare primeiro.", file=sys.stderr)
        sys.exit(1)

    client = OpenAI(api_key=api_key)

    print(f"Fazendo upload de {jsonl_path}...")
    with open(jsonl_path, "rb") as f:
        uploaded = client.files.create(file=f, purpose="batch")
    print(f"  file_id: {uploaded.id}")

    print("Submetendo batch...")
    batch = client.batches.create(
        input_file_id=uploaded.id,
        endpoint="/v1/chat/completions",
        completion_window="24h",
        metadata={"description": "witup-llm smell validation", "run_dir": str(run_dir)},
    )
    print(f"  batch_id: {batch.id}")
    print(f"  status:   {batch.status}")

    # Salvar estado
    state = {
        "batch_id":      batch.id,
        "file_id":       uploaded.id,
        "status":        batch.status,
        "submitted_at":  time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
    }
    state_path = out_dir / "batch_state.json"
    with open(state_path, "w") as f:
        json.dump(state, f, indent=2)

    print(f"\n✅ Batch submetido: {batch.id}")
    print(f"   Estado salvo em: {state_path}")
    print(f"\nPróximo passo (quando batch estiver completo):")
    print(f"  OPENAI_API_KEY=... python3 {sys.argv[0]} --run-dir {run_dir} --collect")


# ── Fase 3: Coletar ──────────────────────────────────────────────────────────

def cmd_collect(run_dir: Path, batch_id: Optional[str]) -> None:
    """Verifica status e coleta resultados do batch."""
    try:
        from openai import OpenAI
    except ImportError:
        print("ERRO: openai não instalado. Execute: pip install openai", file=sys.stderr)
        sys.exit(1)

    api_key = os.environ.get("OPENAI_API_KEY")
    if not api_key:
        print("ERRO: OPENAI_API_KEY não definida.", file=sys.stderr)
        sys.exit(1)

    out_dir = run_dir / "smells-results" / "llm-validation"

    # Resolver batch_id
    if not batch_id:
        state_path = out_dir / "batch_state.json"
        if not state_path.exists():
            print(f"ERRO: --batch-id não fornecido e {state_path} não existe.", file=sys.stderr)
            sys.exit(1)
        with open(state_path) as f:
            state = json.load(f)
        batch_id = state["batch_id"]

    client = OpenAI(api_key=api_key)
    batch = client.batches.retrieve(batch_id)

    print(f"Batch {batch_id}:")
    print(f"  status:     {batch.status}")
    if batch.request_counts:
        rc = batch.request_counts
        print(f"  requests:   completed={rc.completed}, failed={rc.failed}, total={rc.total}")

    if batch.status not in ("completed", "failed", "expired", "cancelled"):
        # Calcular progresso estimado
        if batch.request_counts and batch.request_counts.total > 0:
            pct = batch.request_counts.completed / batch.request_counts.total * 100
            print(f"  progresso:  {pct:.1f}%")
        print("\nBatch ainda não concluído. Tente novamente mais tarde.")
        # Atualizar estado
        state_path = out_dir / "batch_state.json"
        if state_path.exists():
            with open(state_path) as f:
                state = json.load(f)
            state["status"] = batch.status
            with open(state_path, "w") as f:
                json.dump(state, f, indent=2)
        return

    if batch.status != "completed":
        print(f"\nBatch encerrado com status: {batch.status}", file=sys.stderr)
        sys.exit(1)

    # Baixar resultados
    output_file_id = batch.output_file_id
    print(f"\nBaixando resultados (file_id={output_file_id})...")
    content = client.files.content(output_file_id)
    results_raw = content.text

    results_path = out_dir / "batch_results.jsonl"
    with open(results_path, "w", encoding="utf-8") as f:
        f.write(results_raw)

    # Contar linhas
    n_results = sum(1 for line in results_raw.splitlines() if line.strip())
    print(f"  {n_results} resultados salvos em {results_path}")

    # Baixar erros se houver
    if batch.error_file_id:
        err_content = client.files.content(batch.error_file_id)
        err_path = out_dir / "batch_errors.jsonl"
        with open(err_path, "w", encoding="utf-8") as f:
            f.write(err_content.text)
        n_errors = sum(1 for line in err_content.text.splitlines() if line.strip())
        print(f"  {n_errors} erros salvos em {err_path}")

    # Atualizar estado
    state_path = out_dir / "batch_state.json"
    if state_path.exists():
        with open(state_path) as f:
            state = json.load(f)
        state["status"] = "completed"
        state["output_file_id"] = output_file_id
        state["collected_at"] = time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime())
        with open(state_path, "w") as f:
            json.dump(state, f, indent=2)

    print(f"\n✅ Coleta concluída.")
    print(f"\nPróximo passo:")
    print(f"  python3 {sys.argv[0]} --run-dir {run_dir} --analyze")


# ── Fase 4: Analisar ──────────────────────────────────────────────────────────

def parse_combined_response(text: str) -> dict[str, bool]:
    """
    Parseia resposta 'combined' — 5 linhas no formato:
      assertion_roulette: YES|NO — reason
    Retorna {smell_key: bool}
    """
    detected = {}
    for line in text.strip().splitlines():
        line = line.strip()
        for smell_key in SMELL_DEFINITIONS:
            if line.lower().startswith(smell_key + ":"):
                rest = line[len(smell_key)+1:].strip()
                detected[smell_key] = rest.upper().startswith("YES")
                break
    return detected


def parse_per_smell_response(text: str) -> bool:
    """Parseia resposta 'per_smell' — YES ou NO na primeira linha."""
    first_line = text.strip().splitlines()[0].strip().upper() if text.strip() else ""
    return first_line.startswith("YES")


def load_static_results(run_dir: Path, variant: str) -> dict[str, set[str]]:
    """
    Carrega resultados do detect_test_smells.py.
    Retorna {file_rel_path: {smell, ...}}
    """
    smells_file = run_dir / "smells-results" / f"{variant}-smells.json"
    if not smells_file.exists():
        return {}
    with open(smells_file, encoding="utf-8") as f:
        data = json.load(f)

    result = {}
    for entry in data:
        file_path = entry.get("file", "")
        # Normalizar para caminho relativo ao run_dir
        try:
            rel = str(Path(file_path).relative_to(run_dir))
        except ValueError:
            rel = file_path
        smells_set = {s["smell"] for s in entry.get("smells", [])}
        result[rel] = smells_set
    return result


def cmd_analyze(run_dir: Path) -> None:
    """Cross-valida resultados LLM vs análise estática (detect_test_smells.py)."""
    out_dir = run_dir / "smells-results" / "llm-validation"

    # Carregar índice
    index_path = out_dir / "file_index.json"
    if not index_path.exists():
        print(f"ERRO: {index_path} não encontrado. Execute --prepare primeiro.", file=sys.stderr)
        sys.exit(1)
    with open(index_path) as f:
        file_index = {entry["custom_id"]: entry for entry in json.load(f)}

    # Carregar resultados do batch
    results_path = out_dir / "batch_results.jsonl"
    if not results_path.exists():
        print(f"ERRO: {results_path} não encontrado. Execute --collect primeiro.", file=sys.stderr)
        sys.exit(1)

    # Parsear resultados do LLM
    llm_detections: dict[str, dict[str, dict[str, bool]]] = {}
    # estrutura: {variant: {file_rel_path: {smell_key: bool}}}
    n_parsed = 0
    n_errors = 0

    with open(results_path, encoding="utf-8") as f:
        for line in f:
            if not line.strip():
                continue
            try:
                result = json.loads(line)
            except json.JSONDecodeError:
                n_errors += 1
                continue

            custom_id = result.get("custom_id", "")
            if custom_id not in file_index:
                n_errors += 1
                continue

            meta = file_index[custom_id]
            variant  = meta["variant"]
            rel_file = meta["file"]

            # Extrair texto da resposta
            response_body = result.get("response", {}).get("body", {})
            choices = response_body.get("choices", [])
            if not choices:
                n_errors += 1
                continue
            text = choices[0].get("message", {}).get("content", "")

            if meta["strategy"] == "combined":
                detections = parse_combined_response(text)
            else:
                smell_key  = meta["smell"]
                detections = {smell_key: parse_per_smell_response(text)}

            if variant not in llm_detections:
                llm_detections[variant] = {}
            if rel_file not in llm_detections[variant]:
                llm_detections[variant][rel_file] = {}
            llm_detections[variant][rel_file].update(detections)
            n_parsed += 1

    print(f"Resultados LLM parseados: {n_parsed} OK, {n_errors} erros")

    # Carregar resultados estáticos
    static_results: dict[str, dict[str, set[str]]] = {}
    for variant in ["direct-tests", "wit-context"]:
        static_results[variant] = load_static_results(run_dir, variant)
        n = sum(1 for smells in static_results[variant].values() if smells)
        print(f"Estático {variant}: {len(static_results[variant])} arquivos, "
              f"{n} com pelo menos 1 smell")

    # ── Análise por smell ──────────────────────────────────────────────────────
    # Para smells com correspondência direta (assertion_roulette, conditional_logic,
    # exception_handling), calcular concordância.
    # Para smells novos (duplicate_assert, magic_number), reportar prevalência LLM.

    per_smell_stats: dict[str, dict] = {}

    for smell_key in SMELL_DEFINITIONS.keys():
        static_keys = SMELL_MAPPING_TO_STATIC.get(smell_key, [])

        llm_yes_count: dict[str, int] = {"direct-tests": 0, "wit-context": 0}
        both_yes:   dict[str, int] = {"direct-tests": 0, "wit-context": 0}
        llm_yes_static_no: dict[str, int] = {"direct-tests": 0, "wit-context": 0}
        llm_no_static_yes: dict[str, int] = {"direct-tests": 0, "wit-context": 0}
        total_compared: dict[str, int] = {"direct-tests": 0, "wit-context": 0}

        for variant in ["direct-tests", "wit-context"]:
            for rel_file, llm_smells in llm_detections.get(variant, {}).items():
                llm_detected = llm_smells.get(smell_key, False)
                static_smells = static_results.get(variant, {}).get(rel_file, set())
                # Considerar "detected by static" se qualquer smell mapeado estiver presente
                static_detected = any(sk in static_smells for sk in static_keys)

                total_compared[variant] += 1
                if llm_detected:
                    llm_yes_count[variant] += 1
                if llm_detected and static_detected:
                    both_yes[variant] += 1
                if llm_detected and not static_detected:
                    llm_yes_static_no[variant] += 1
                if not llm_detected and static_detected:
                    llm_no_static_yes[variant] += 1

        # Calcular métricas por variante
        per_variant: dict[str, dict] = {}
        for variant in ["direct-tests", "wit-context"]:
            n = total_compared[variant]
            llm_yes = llm_yes_count[variant]
            by = both_yes[variant]
            yn = llm_yes_static_no[variant]
            ny = llm_no_static_yes[variant]
            yy = by
            nn = n - llm_yes - ny
            agreement = (yy + nn) / n * 100 if n > 0 else 0
            per_variant[variant] = {
                "total": n,
                "llm_yes": llm_yes,
                "llm_yes_pct": round(llm_yes / n * 100, 1) if n > 0 else 0,
                "llm_yes_static_no": yn,   # falso positivo do estático (novo para LLM)
                "llm_no_static_yes": ny,   # falso negativo do LLM (nosso script detectou, LLM não)
                "agreement_pct": round(agreement, 1),
                "has_static_mapping": len(static_keys) > 0,
            }
        per_smell_stats[smell_key] = per_variant

    # ── Resumo global ──────────────────────────────────────────────────────────
    comparison = {
        "llm_model":         "gpt-4.1-nano",
        "methodology_ref":   "arXiv:2504.07277 (Melo et al., 2025) — single-agent, adapted for jtreg",
        "parsed_results":    n_parsed,
        "parse_errors":      n_errors,
        "per_smell":         per_smell_stats,
    }

    comparison_path = out_dir / "comparison.json"
    with open(comparison_path, "w", encoding="utf-8") as f:
        json.dump(comparison, f, indent=2, ensure_ascii=False)

    # ── Imprimir tabela de concordância ───────────────────────────────────────
    print()
    print("=" * 80)
    print("CONCORDÂNCIA: LLM (gpt-4.1-nano) vs Análise Estática (detect_test_smells.py)")
    print("Metodologia: single-agent, adapted from arXiv:2504.07277 para jtreg")
    print("=" * 80)

    header = f"{'Smell':<25} {'Mapeamento':<20} {'DT agree%':>10} {'WC agree%':>10} {'DT llm%':>8} {'WC llm%':>8}"
    print(header)
    print("-" * 80)

    for smell_key, info in SMELL_DEFINITIONS.items():
        stats = per_smell_stats[smell_key]
        static_keys = SMELL_MAPPING_TO_STATIC.get(smell_key, [])
        mapping = ", ".join(static_keys) if static_keys else "novo"

        dt = stats["direct-tests"]
        wc = stats["wit-context"]

        if dt["has_static_mapping"]:
            agree_dt = f"{dt['agreement_pct']}%"
            agree_wc = f"{wc['agreement_pct']}%"
        else:
            agree_dt = "n/a"
            agree_wc = "n/a"

        print(f"{smell_key:<25} {mapping:<20} {agree_dt:>10} {agree_wc:>10} "
              f"{dt['llm_yes_pct']:>7}% {wc['llm_yes_pct']:>7}%")

    print("-" * 80)
    print()

    # Novos smells reportados pelo LLM (sem correspondência na análise estática)
    print("Smells novos detectados pelo LLM (não cobertos pela análise estática):")
    for smell_key in ["duplicate_assert", "magic_number"]:
        dt = per_smell_stats[smell_key]["direct-tests"]
        wc = per_smell_stats[smell_key]["wit-context"]
        print(f"  {smell_key:<25}  direct-tests: {dt['llm_yes']} ({dt['llm_yes_pct']}%)  "
              f"wit-context: {wc['llm_yes']} ({wc['llm_yes_pct']}%)")

    print()
    print(f"✅ Análise completa salva em: {comparison_path}")

    # ── Gerar CSV para o paper ─────────────────────────────────────────────────
    csv_path = out_dir / "comparison_table_llm.csv"
    with open(csv_path, "w", encoding="utf-8") as f:
        f.write("smell,static_mapping,dt_agreement_pct,wc_agreement_pct,"
                "dt_llm_prevalence_pct,wc_llm_prevalence_pct,"
                "dt_llm_yes,wc_llm_yes,dt_total,wc_total\n")
        for smell_key in SMELL_DEFINITIONS:
            stats = per_smell_stats[smell_key]
            static_keys = SMELL_MAPPING_TO_STATIC.get(smell_key, [])
            mapping = "|".join(static_keys) if static_keys else "new"
            dt = stats["direct-tests"]
            wc = stats["wit-context"]
            agree_dt = dt["agreement_pct"] if dt["has_static_mapping"] else ""
            agree_wc = wc["agreement_pct"] if wc["has_static_mapping"] else ""
            f.write(f"{smell_key},{mapping},{agree_dt},{agree_wc},"
                    f"{dt['llm_yes_pct']},{wc['llm_yes_pct']},"
                    f"{dt['llm_yes']},{wc['llm_yes']},"
                    f"{dt['total']},{wc['total']}\n")
    print(f"CSV para o paper: {csv_path}")


# ── CLI ───────────────────────────────────────────────────────────────────────

def main() -> None:
    parser = argparse.ArgumentParser(
        description="LLM-based test smell validation via OpenAI Batch API",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__,
    )
    parser.add_argument(
        "--run-dir", required=True, type=Path,
        help="Diretório do run do experimento (contém variants/ e smells-results/)",
    )
    parser.add_argument(
        "--model", default="gpt-4.1-nano",
        help="Modelo OpenAI a usar (default: gpt-4.1-nano)",
    )
    parser.add_argument(
        "--strategy", choices=["combined", "per_smell"], default="combined",
        help=(
            "combined: 1 chamada/arquivo (todos smells juntos, ~$0.10 para 631 testes) "
            "per_smell: 5 chamadas/arquivo (~$0.25, mais preciso). Default: combined"
        ),
    )

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument("--prepare", action="store_true",
                       help="Fase 1: gerar batch_requests.jsonl")
    group.add_argument("--submit", action="store_true",
                       help="Fase 2: submeter ao OpenAI Batch API")
    group.add_argument("--collect", action="store_true",
                       help="Fase 3: coletar resultados do batch")
    group.add_argument("--analyze", action="store_true",
                       help="Fase 4: cross-validar LLM vs análise estática")
    group.add_argument("--status", action="store_true",
                       help="Verificar status do batch sem coletar")

    parser.add_argument("--batch-id",
                        help="ID do batch OpenAI (necessário para --collect se state não existir)")

    args = parser.parse_args()

    run_dir = args.run_dir.resolve()
    if not run_dir.exists():
        print(f"ERRO: --run-dir {run_dir} não existe.", file=sys.stderr)
        sys.exit(1)

    if args.prepare:
        cmd_prepare(run_dir, args.model, args.strategy)
    elif args.submit:
        cmd_submit(run_dir)
    elif args.collect:
        cmd_collect(run_dir, args.batch_id)
    elif args.analyze:
        cmd_analyze(run_dir)
    elif args.status:
        cmd_collect(run_dir, args.batch_id)  # collect sem download se incompleto


if __name__ == "__main__":
    main()
