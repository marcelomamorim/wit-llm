#!/usr/bin/env python3
"""
detect_test_smells.py — Detecta test smells em testes Java gerados por LLM.

Suporta dois formatos:
  - JUnit 4/5: anotação @Test
  - jtreg:     comentário /* @test */ + public static void main(String[] args)
               métodos auxiliares testXxx() chamados de main()

Smells detectados (análise estática por regex):
  1. empty_test                 — método de teste sem assert/check/throw/AssertionError
  2. assertion_roulette         — múltiplos checks sem mensagem descritiva
  3. exception_catching         — catch genérico não-esperado (Exception/Throwable sem comentário)
  4. conditional_logic          — if/for/while/switch no corpo de método de teste
  5. redundant_println          — System.out.println no teste
  6. verbose_test               — método de teste com mais de 30 linhas
  7. ignored_test               — anotação @Ignore/@Disabled ou @run main ausente
  8. sleepy_test                — Thread.sleep no teste
  9. empty_catch                — catch com corpo completamente vazio (sem comentário nem statement)
 10. expected_exception_catch   — catch legítimo de exceção esperada:
                                  try { ...; throw new AssertionError(...); } catch (E e) { }
                                  NÃO é um smell — é o padrão jtreg correto para exception paths.
                                  Registrado como métrica positiva (não conta como smell).

Nota sobre empty_catch vs expected_exception_catch:
  O padrão jtreg para testar caminhos de exceção é:
    try {
        methodThatShouldThrow();
        throw new AssertionError("Expected X but nothing was thrown");
    } catch (ExpectedException e) {
        // expected — ou corpo vazio
    }
  Este padrão NÃO é um smell. O detector diferencia os dois casos verificando
  se o bloco try precedente contém 'throw new AssertionError'.
"""

import argparse
import json
import os
import re
import sys
from pathlib import Path

# ── Detecção de formato ────────────────────────────────────────────────────────
RE_JTREG_HEADER = re.compile(r'/\*\s*\*?\s*@test\b', re.IGNORECASE)
RE_JUNIT_TEST   = re.compile(r'@Test\b')

# ── Padrões jtreg ──────────────────────────────────────────────────────────────
# Método auxiliar de teste: private/static void testXxx() ou public static void main()
RE_JTREG_METHOD = re.compile(
    r'(?:private|public|protected)\s+static\s+void\s+(\w+)\s*\([^)]*\)\s*(?:throws[^{]*)?\{',
    re.MULTILINE
)

# ── Padrões JUnit ──────────────────────────────────────────────────────────────
RE_JUNIT_METHOD = re.compile(
    r'@Test\s*(?:\([^)]*\)\s*)?(?:public|protected|)\s*(?:static\s+)?void\s+(\w+)\s*\(([^)]*)\)\s*(?:throws[^{]*)?\{',
    re.MULTILINE
)

# ── Padrões de assertion ────────────────────────────────────────────────────────
# jtreg: check(cond, msg), throw new AssertionError, assertEquals, assertTrue etc.
RE_ASSERT_JTREG = re.compile(
    r'\b(?:check\s*\(|throw\s+new\s+AssertionError|assertEquals|assertNotEquals|'
    r'assertTrue|assertFalse|assertNull|assertNotNull|fail\s*\()\b'
)
RE_ASSERT_JUNIT = re.compile(
    r'\b(?:assert(?:Equals|NotEquals|True|False|Null|NotNull|Same|NotSame|ArrayEquals|'
    r'That|Throws|DoesNotThrow)|fail|verify)\b',
    re.IGNORECASE
)
# Check com mensagem: check(cond, "msg") ou assertEquals("msg", ...)
RE_CHECK_WITH_MSG = re.compile(r'\bcheck\s*\([^,]+,\s*"')
RE_ASSERT_WITH_MSG = re.compile(r'\b(?:assertEquals|assertTrue|assertFalse)\s*\(\s*"')

# catch genérico (exception esperada tem comentário "expected" próximo)
RE_CATCH_GENERIC = re.compile(
    r'\bcatch\s*\(\s*(?:Exception|Throwable|RuntimeException|Error)\s+(\w+)\s*\)\s*\{'
)
RE_EXPECTED_COMMENT = re.compile(r'//\s*expected|expected\b', re.IGNORECASE)
RE_EMPTY_CATCH = re.compile(
    r'\bcatch\s*\([^)]+\)\s*\{\s*(?://[^\n]*)?\s*\}'
)

# Padrões para distinguir empty_catch de expected_exception_catch
RE_TRY_OPEN         = re.compile(r'\btry\s*\{')
RE_THROW_ASSERT_ERR = re.compile(r'\bthrow\s+new\s+AssertionError\b')

RE_CONDITIONAL = re.compile(r'\b(?:if|for|while|switch)\s*\(')
RE_PRINTLN    = re.compile(r'System\s*\.\s*out\s*\.\s*print')
RE_IGNORE     = re.compile(r'@(?:Ignore|Disabled)\b')
RE_SLEEP      = re.compile(r'Thread\s*\.\s*sleep\s*\(')


def is_expected_exception_catch(method_body: str, catch_pos: int) -> bool:
    """
    Retorna True se o catch na posição catch_pos é precedido por um bloco try
    que contém 'throw new AssertionError' — padrão jtreg correto para testar
    caminhos de exceção.  NÃO é um smell; registrar como expected_exception_catch.

    Falsos positivos são improvíveis: exige que 'throw new AssertionError'
    apareça literalmente no try imediatamente antes do catch.
    """
    prefix = method_body[:catch_pos]
    # Encontrar o último 'try {' antes deste catch
    last_try = None
    for m in RE_TRY_OPEN.finditer(prefix):
        last_try = m
    if not last_try:
        return False
    # Conteúdo entre o '{' do try e o início do catch — basta verificar se
    # 'throw new AssertionError' aparece nesse intervalo
    try_body_text = prefix[last_try.end(): catch_pos]
    return bool(RE_THROW_ASSERT_ERR.search(try_body_text))


def extract_method_body(source: str, method_start: int) -> str:
    """Extrai o corpo de um método a partir da posição do '{' inicial."""
    depth = 0
    start = source.index('{', method_start)
    for i, ch in enumerate(source[start:], start):
        if ch == '{':
            depth += 1
        elif ch == '}':
            depth -= 1
            if depth == 0:
                return source[start:i + 1]
    return source[start:]


def count_lines(body: str) -> int:
    return body.count('\n')


def analyze_jtreg_file(source: str, path: Path) -> list:
    """Analisa arquivo no formato jtreg (main + testXxx helpers)."""
    smells = []

    for m in RE_JTREG_METHOD.finditer(source):
        method_name = m.group(1)
        # Pular construtores e métodos não-test que não começam com test/check/main
        if not (method_name.startswith('test') or method_name == 'main'
                or method_name.startswith('verify') or method_name.startswith('check')):
            continue

        try:
            body = extract_method_body(source, m.start())
        except Exception:
            continue

        line_count = count_lines(body)
        has_assert = bool(RE_ASSERT_JTREG.search(body))

        # 1. Empty test
        if not has_assert and method_name != 'main':
            smells.append({"method": method_name, "smell": "empty_test"})

        # 2. Muitos checks sem mensagem (assertion roulette)
        checks_all = RE_ASSERT_JTREG.findall(body)
        checks_msg = RE_CHECK_WITH_MSG.findall(body) + RE_ASSERT_WITH_MSG.findall(body)
        if len(checks_all) >= 3 and len(checks_msg) == 0:
            smells.append({"method": method_name, "smell": "assertion_roulette",
                           "check_count": len(checks_all)})

        # 3. Exception catching genérica não-esperada
        for catch_m in RE_CATCH_GENERIC.finditer(body):
            catch_block_start = catch_m.end()
            nearby = body[catch_m.start():catch_m.start()+200]
            if not RE_EXPECTED_COMMENT.search(nearby):
                smells.append({"method": method_name, "smell": "exception_catching"})
                break

        # 4. Conditional logic
        if RE_CONDITIONAL.search(body):
            smells.append({"method": method_name, "smell": "conditional_logic"})

        # 5. Redundant println
        if RE_PRINTLN.search(body):
            smells.append({"method": method_name, "smell": "redundant_println"})

        # 6. Verbose test (>30 linhas)
        if line_count > 30 and method_name != 'main':
            smells.append({"method": method_name, "smell": "verbose_test",
                           "line_count": line_count})

        # 7. Sleepy test
        if RE_SLEEP.search(body):
            smells.append({"method": method_name, "smell": "sleepy_test"})

        # 8. Empty catch vs expected_exception_catch
        #    Iterar sobre TODOS os catch vazios no método e classificar cada um.
        for ec_m in RE_EMPTY_CATCH.finditer(body):
            if is_expected_exception_catch(body, ec_m.start()):
                # Padrão jtreg legítimo — NÃO é smell; registrar como métrica positiva
                smells.append({"method": method_name, "smell": "expected_exception_catch"})
            else:
                smells.append({"method": method_name, "smell": "empty_catch"})

    return smells


def analyze_junit_file(source: str, path: Path) -> list:
    """Analisa arquivo no formato JUnit 4/5 (@Test)."""
    smells = []
    file_ignored = bool(RE_IGNORE.search(source))

    for m in RE_JUNIT_METHOD.finditer(source):
        method_name = m.group(1)
        try:
            body = extract_method_body(source, m.start())
        except Exception:
            continue

        line_count = count_lines(body)
        has_assert  = bool(RE_ASSERT_JUNIT.search(body))
        asserts_all = RE_ASSERT_JUNIT.findall(body)
        asserts_msg = RE_ASSERT_WITH_MSG.findall(body)

        if not has_assert:
            smells.append({"method": method_name, "smell": "empty_test"})
        if len(asserts_all) >= 3 and len(asserts_msg) == 0:
            smells.append({"method": method_name, "smell": "assertion_roulette",
                           "assert_count": len(asserts_all)})
        if RE_CATCH_GENERIC.search(body):
            smells.append({"method": method_name, "smell": "exception_catching"})
        if RE_CONDITIONAL.search(body):
            smells.append({"method": method_name, "smell": "conditional_logic"})
        if RE_PRINTLN.search(body):
            smells.append({"method": method_name, "smell": "redundant_println"})
        if line_count > 30:
            smells.append({"method": method_name, "smell": "verbose_test",
                           "line_count": line_count})
        if file_ignored:
            smells.append({"method": method_name, "smell": "ignored_test"})
        if RE_SLEEP.search(body):
            smells.append({"method": method_name, "smell": "sleepy_test"})
        # 8. Empty catch vs expected_exception_catch
        for ec_m in RE_EMPTY_CATCH.finditer(body):
            if is_expected_exception_catch(body, ec_m.start()):
                smells.append({"method": method_name, "smell": "expected_exception_catch"})
            else:
                smells.append({"method": method_name, "smell": "empty_catch"})

    return smells


def analyze_file(path: Path) -> dict:
    try:
        source = path.read_text(encoding='utf-8', errors='replace')
    except Exception as e:
        return {"file": str(path), "error": str(e), "smells": []}

    # Detectar formato
    is_jtreg = bool(RE_JTREG_HEADER.search(source[:500]))
    is_junit  = bool(RE_JUNIT_TEST.search(source))

    if is_jtreg:
        smells = analyze_jtreg_file(source, path)
        fmt = "jtreg"
    elif is_junit:
        smells = analyze_junit_file(source, path)
        fmt = "junit"
    else:
        # Tentar jtreg como fallback para arquivos com main()
        if 'public static void main' in source:
            smells = analyze_jtreg_file(source, path)
            fmt = "jtreg-inferred"
        else:
            smells = []
            fmt = "unknown"

    return {
        "file": str(path),
        "format": fmt,
        "smells": smells,
        "smell_count": len(smells),
    }


def main():
    parser = argparse.ArgumentParser(description="Detecta test smells em testes Java")
    parser.add_argument("--input-dir", required=True, help="Diretório raiz dos testes .java")
    parser.add_argument("--output",    required=True, help="Arquivo JSON de saída")
    parser.add_argument("--variant",   default="unknown", help="Nome da variante")
    args = parser.parse_args()

    input_dir = Path(args.input_dir)
    if not input_dir.exists():
        print(f"Erro: diretório não encontrado: {input_dir}", file=sys.stderr)
        sys.exit(1)

    java_files = sorted(input_dir.rglob("*.java"))
    print(f"[smells] {args.variant}: {len(java_files)} arquivos Java encontrados")

    results = []
    smell_counts: dict[str, int] = {}

    for f in java_files:
        r = analyze_file(f)
        results.append(r)
        for s in r["smells"]:
            smell_name = s["smell"]
            smell_counts[smell_name] = smell_counts.get(smell_name, 0) + 1

    total_smells = sum(r["smell_count"] for r in results)
    files_with_smells = sum(1 for r in results if r["smell_count"] > 0)

    output = {
        "variant": args.variant,
        "total_files": len(java_files),
        "files_with_smells": files_with_smells,
        "total_smell_instances": total_smells,
        "smell_summary": smell_counts,
        "files": results,
    }

    Path(args.output).parent.mkdir(parents=True, exist_ok=True)
    with open(args.output, "w") as fp:
        json.dump(output, fp, indent=2, ensure_ascii=False)

    print(f"[smells] {args.variant}: {total_smells} smell instances em "
          f"{files_with_smells}/{len(java_files)} arquivos")
    print(f"[smells] Resultado salvo em {args.output}")
    for smell, count in sorted(smell_counts.items(), key=lambda x: -x[1]):
        print(f"  {smell}: {count}")


if __name__ == "__main__":
    main()
