#!/usr/bin/env python3
"""
sample_smells.py — Amostrador de smell específico para análise qualitativa.

Exibe N exemplos de um smell (ex: empty_catch) de cada variante,
mostrando: nome do arquivo, classe JDK coberta, e o método/bloco relevante.

Uso:
  python3 sample_smells.py \
    --run-dir generated/experiments/jdk-global-impact-study/<run_stamp> \
    --smell empty_catch \
    --n 5
"""

import argparse
import json
import re
from pathlib import Path


def extract_snippet(source: str, smell_method: str, max_lines: int = 20) -> str:
    """Extrai o trecho do método que contém o smell."""
    # Buscar o método pelo nome
    pattern = re.compile(
        rf'(?:private|public|protected|static|\s)+void\s+{re.escape(smell_method)}\s*\(',
        re.MULTILINE
    )
    m = pattern.search(source)
    if not m:
        # Fallback: procurar por main
        m = re.search(r'public\s+static\s+void\s+main\s*\(', source)
    if not m:
        return "(método não localizado)"

    start = source.rfind('\n', 0, m.start()) + 1
    lines = source[start:].split('\n')
    return '\n'.join(lines[:max_lines])


def get_jdk_class(file_path: str) -> str:
    """Extrai o nome da classe JDK a partir do nome do arquivo de teste."""
    name = Path(file_path).stem  # ex: StringWitupTest
    # Remove sufixo WitupTest
    return re.sub(r'WitupTest$', '', name)


def sample_smell(smells_json: Path, variants_root: Path, variant_name: str,
                 smell_type: str, n: int):
    """Exibe N amostras de um smell de uma variante."""
    with open(smells_json) as f:
        data = json.load(f)

    print(f"\n{'='*70}")
    print(f"Variante: {variant_name}  |  Smell: {smell_type}  |  Top {n}")
    print(f"{'='*70}")

    count = 0
    for fdata in data["files"]:
        matching = [s for s in fdata.get("smells", []) if s["smell"] == smell_type]
        if not matching:
            continue

        file_path = Path(fdata["file"])
        jdk_class = get_jdk_class(file_path.name)

        # Tentar ler o arquivo fonte para extrair snippet
        # Dentro do container seria /data/..., no host é diferente
        source = ""
        # Tentar caminho relativo ao variants_root
        relative = file_path
        candidates = [
            variants_root / variant_name / "test" / "jdk" / "witup" / "generated" / file_path.name,
            file_path,
        ]
        # Mais robusto: buscar recursivamente
        found = list(variants_root.rglob(file_path.name)) if variants_root.exists() else []
        if found:
            try:
                source = found[0].read_text(encoding='utf-8', errors='replace')
            except Exception:
                pass

        for smell_inst in matching:
            method = smell_inst.get("method", "?")
            print(f"\n📄 {file_path.name}  →  JDK: {jdk_class}")
            print(f"   método: {method}  |  smell: {smell_type}")

            if "check_count" in smell_inst:
                print(f"   checks sem msg: {smell_inst['check_count']}")
            if "line_count" in smell_inst:
                print(f"   linhas: {smell_inst['line_count']}")

            if source:
                snippet = extract_snippet(source, method)
                print(f"\n   --- snippet ---")
                for line in snippet.split('\n')[:15]:
                    print(f"   {line}")
                print(f"   --- fim snippet ---")

            count += 1
            if count >= n:
                return

    if count == 0:
        print(f"  (nenhum arquivo com smell '{smell_type}' encontrado)")


def main():
    parser = argparse.ArgumentParser(description="Amostrador de test smells para análise qualitativa")
    parser.add_argument("--run-dir", required=True)
    parser.add_argument("--smell", default="empty_catch",
                        help="Tipo de smell a amostrar (default: empty_catch)")
    parser.add_argument("--n", type=int, default=5,
                        help="Número de exemplos por variante (default: 5)")
    args = parser.parse_args()

    run_dir = Path(args.run_dir)
    smells_dir = run_dir / "smells-results"
    variants_root = run_dir / "variants"

    for variant in ["direct-tests", "wit-context"]:
        smells_json = smells_dir / f"{variant}-smells.json"
        if not smells_json.exists():
            print(f"[SKIP] {smells_json} não encontrado")
            continue
        sample_smell(smells_json, variants_root, variant, args.smell, args.n)

    print()


if __name__ == "__main__":
    main()
