#!/usr/bin/env python3
"""
analyze_smells_comparison.py — Comparação estatística de test smells entre variantes.

Gera:
  - comparison_table.csv   : contagens e densidades por smell type
  - comparison_stats.json  : qui-quadrado + odds ratio por smell (proporção de arquivos afetados)
  - smell_density.csv      : smells/arquivo por variante (para box plot)

Uso:
  python3 analyze_smells_comparison.py \
    --run-dir generated/experiments/jdk-global-impact-study/<run_stamp>
"""

import argparse
import csv
import json
import math
import sys
from pathlib import Path

# ── Qui-quadrado 2×2 simples (sem scipy) ─────────────────────────────────────
def chi2_pvalue(a, b, c, d):
    """
    Tabela de contingência 2×2:
       afetado  não-afetado
    A:   a          b
    B:   c          d
    Retorna (chi2_stat, p_value) com correção de Yates.
    """
    n = a + b + c + d
    if n == 0:
        return 0.0, 1.0
    expected_a = (a + b) * (a + c) / n
    expected_c = (a + b) * (c + d) / n  # noqa: not used directly
    # Chi-quadrado com correção de continuidade (Yates)
    chi2 = (n * (abs(a * d - b * c) - n / 2) ** 2) / (
        (a + b) * (c + d) * (a + c) * (b + d)
    ) if ((a + b) and (c + d) and (a + c) and (b + d)) else 0.0
    # p-value aproximado pela distribuição chi² com 1 grau de liberdade
    # usando integração numérica simples (série de regularized gamma)
    p = _chi2_sf(chi2, df=1)
    return chi2, p


def _chi2_sf(x, df=1):
    """Survival function (1 - CDF) da distribuição chi² para df=1."""
    if x <= 0:
        return 1.0
    # Para df=1: P(chi²>x) = erfc(sqrt(x/2))
    return _erfc(math.sqrt(x / 2))


def _erfc(x):
    """Complementary error function via série de Horner."""
    # Aproximação de Abramowitz & Stegun 7.1.26
    t = 1.0 / (1.0 + 0.3275911 * x)
    poly = t * (0.254829592 + t * (-0.284496736 + t * (
        1.421413741 + t * (-1.453152027 + t * 1.061405429))))
    return poly * math.exp(-x * x)


def odds_ratio(a, b, c, d):
    """OR = (a/b) / (c/d) com suavização de Laplace se houver zero."""
    a, b, c, d = a + 0.5, b + 0.5, c + 0.5, d + 0.5
    return (a * d) / (b * c)


# ── Carregar dados ─────────────────────────────────────────────────────────────
def load_smells(path: Path) -> dict:
    with open(path) as f:
        return json.load(f)


def files_with_smell(data: dict, smell: str) -> int:
    """Número de arquivos com pelo menos 1 instância do smell."""
    return sum(
        1 for f in data["files"]
        if any(s["smell"] == smell for s in f.get("smells", []))
    )


def count_smell(data: dict, smell: str) -> int:
    """Total de instâncias do smell."""
    return sum(
        sum(1 for s in f.get("smells", []) if s["smell"] == smell)
        for f in data["files"]
    )


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--run-dir", required=True, help="Diretório do run")
    args = parser.parse_args()

    run_dir = Path(args.run_dir)
    smells_dir = run_dir / "smells-results"

    dt_path = smells_dir / "direct-tests-smells.json"
    wc_path = smells_dir / "wit-context-smells.json"

    if not dt_path.exists() or not wc_path.exists():
        print(f"Erro: arquivos de smells não encontrados em {smells_dir}", file=sys.stderr)
        sys.exit(1)

    dt = load_smells(dt_path)
    wc = load_smells(wc_path)

    n_dt = dt["total_files"]
    n_wc = wc["total_files"]

    # Métricas positivas de qualidade de teste — NÃO contam como smell nos totais
    POSITIVE_METRICS = {"expected_exception_catch"}

    # Todos os smell types presentes em qualquer variante
    all_smells = sorted(
        set(dt.get("smell_summary", {}).keys()) |
        set(wc.get("smell_summary", {}).keys())
    )

    # Totais de smells *reais* (excluindo métricas positivas)
    true_total_dt = sum(
        count_smell(dt, s) for s in all_smells if s not in POSITIVE_METRICS
    )
    true_total_wc = sum(
        count_smell(wc, s) for s in all_smells if s not in POSITIVE_METRICS
    )
    true_files_dt = sum(
        1 for f in dt["files"]
        if any(s["smell"] not in POSITIVE_METRICS for s in f.get("smells", []))
    )
    true_files_wc = sum(
        1 for f in wc["files"]
        if any(s["smell"] not in POSITIVE_METRICS for s in f.get("smells", []))
    )

    print(f"[smells] direct-tests: {n_dt} arquivos, {dt['total_smell_instances']} entradas "
          f"({true_total_dt} smells reais + {dt['total_smell_instances'] - true_total_dt} métricas positivas)")
    print(f"[smells] wit-context:  {n_wc} arquivos, {wc['total_smell_instances']} entradas "
          f"({true_total_wc} smells reais + {wc['total_smell_instances'] - true_total_wc} métricas positivas)")

    # ── Tabela de comparação ─────────────────────────────────────────────────
    rows = []
    stats = {}

    for smell in all_smells:
        cnt_dt = count_smell(dt, smell)
        cnt_wc = count_smell(wc, smell)
        files_dt = files_with_smell(dt, smell)
        files_wc = files_with_smell(wc, smell)

        density_dt = cnt_dt / n_dt if n_dt else 0
        density_wc = cnt_wc / n_wc if n_wc else 0

        pct_dt = 100 * files_dt / n_dt if n_dt else 0
        pct_wc = 100 * files_wc / n_wc if n_wc else 0

        delta_abs = cnt_wc - cnt_dt
        delta_pct = 100 * delta_abs / cnt_dt if cnt_dt else 0

        # Qui-quadrado: arquivos afetados vs não-afetados, direct-tests vs wit-context
        a = files_dt         # dt afetado
        b = n_dt - files_dt  # dt não-afetado
        c = files_wc         # wc afetado
        d = n_wc - files_wc  # wc não-afetado
        chi2, pval = chi2_pvalue(a, b, c, d)
        OR = odds_ratio(a, b, c, d)

        rows.append({
            "smell": smell,
            "is_positive_metric": smell in POSITIVE_METRICS,
            "count_direct_tests": cnt_dt,
            "count_wit_context": cnt_wc,
            "delta_abs": delta_abs,
            "delta_pct": round(delta_pct, 1),
            "density_direct_tests": round(density_dt, 4),
            "density_wit_context": round(density_wc, 4),
            "files_direct_tests": files_dt,
            "files_wit_context": files_wc,
            "pct_files_direct_tests": round(pct_dt, 1),
            "pct_files_wit_context": round(pct_wc, 1),
            "chi2": round(chi2, 4),
            "p_value": round(pval, 4),
            "odds_ratio": round(OR, 4),
            "significant_p05": pval < 0.05,
        })
        stats[smell] = {
            "chi2": round(chi2, 4),
            "p_value": round(pval, 4),
            "odds_ratio": round(OR, 4),
            "significant_p05": pval < 0.05,
        }

    # Linha de totais — apenas smells reais (sem métricas positivas)
    a = true_files_dt; b = n_dt - true_files_dt
    c = true_files_wc; d = n_wc - true_files_wc
    chi2_total, pval_total = chi2_pvalue(a, b, c, d)
    total_dt = true_total_dt   # usado nos outputs abaixo
    total_wc = true_total_wc
    rows.append({
        "smell": "TRUE_SMELLS_TOTAL",
        "is_positive_metric": False,
        "count_direct_tests": true_total_dt,
        "count_wit_context": true_total_wc,
        "delta_abs": true_total_wc - true_total_dt,
        "delta_pct": round(100 * (true_total_wc - true_total_dt) / true_total_dt if true_total_dt else 0, 1),
        "density_direct_tests": round(true_total_dt / n_dt, 4),
        "density_wit_context": round(true_total_wc / n_wc, 4),
        "files_direct_tests": true_files_dt,
        "files_wit_context": true_files_wc,
        "pct_files_direct_tests": round(100 * true_files_dt / n_dt, 1),
        "pct_files_wit_context": round(100 * true_files_wc / n_wc, 1),
        "chi2": round(chi2_total, 4),
        "p_value": round(pval_total, 4),
        "odds_ratio": round(odds_ratio(a, b, c, d), 4),
        "significant_p05": pval_total < 0.05,
    })

    # ── Escrever comparison_table.csv ─────────────────────────────────────────
    csv_path = smells_dir / "comparison_table.csv"
    fieldnames = [
        "smell", "is_positive_metric",
        "count_direct_tests", "count_wit_context", "delta_abs", "delta_pct",
        "density_direct_tests", "density_wit_context",
        "files_direct_tests", "files_wit_context",
        "pct_files_direct_tests", "pct_files_wit_context",
        "chi2", "p_value", "odds_ratio", "significant_p05",
    ]
    with open(csv_path, "w", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames)
        w.writeheader()
        w.writerows(rows)
    print(f"[smells] Tabela de comparação: {csv_path}")

    # ── Escrever comparison_stats.json ────────────────────────────────────────
    stats_out = {
        "n_direct_tests": n_dt,
        "n_wit_context": n_wc,
        "note": "true_smells_total excludes positive quality metrics (expected_exception_catch)",
        "true_smells_direct_tests": true_total_dt,
        "true_smells_wit_context": true_total_wc,
        "true_smells_delta_abs": true_total_wc - true_total_dt,
        "true_smells_delta_pct": round(100 * (true_total_wc - true_total_dt) / true_total_dt if true_total_dt else 0, 1),
        "true_smells_chi2": round(chi2_total, 4),
        "true_smells_p_value": round(pval_total, 4),
        "positive_metrics": {
            s: {"direct_tests": count_smell(dt, s), "wit_context": count_smell(wc, s)}
            for s in POSITIVE_METRICS
        },
        "by_smell": stats,
    }
    stats_path = smells_dir / "comparison_stats.json"
    with open(stats_path, "w") as f:
        json.dump(stats_out, f, indent=2, ensure_ascii=False)
    print(f"[smells] Estatísticas: {stats_path}")

    # ── Imprimir tabela resumo ────────────────────────────────────────────────
    print()
    print(f"{'Smell':<26} {'DT':>6} {'WC':>6} {'Δ':>6} {'Δ%':>7}  {'p':>7}  {'sig':>3}")
    print("-" * 69)
    # Smells reais primeiro
    for r in rows:
        if r.get("is_positive_metric") or r["smell"].startswith("TRUE_SMELLS"):
            continue
        sig = "***" if r["p_value"] < 0.001 else ("**" if r["p_value"] < 0.01
                else ("*" if r["p_value"] < 0.05 else ""))
        marker = " ↑" if r["delta_abs"] > 0 else (" ↓" if r["delta_abs"] < 0 else "")
        print(
            f"{r['smell']:<26} {r['count_direct_tests']:>6} {r['count_wit_context']:>6}"
            f" {r['delta_abs']:>+6} {r['delta_pct']:>+6.1f}%"
            f"  {r['p_value']:>7.4f}  {sig:<3}{marker}"
        )
    # Linha total smells reais
    for r in rows:
        if r["smell"] == "TRUE_SMELLS_TOTAL":
            sig = "***" if r["p_value"] < 0.001 else ("**" if r["p_value"] < 0.01
                    else ("*" if r["p_value"] < 0.05 else ""))
            marker = " ↑" if r["delta_abs"] > 0 else (" ↓" if r["delta_abs"] < 0 else "")
            print("-" * 69)
            print(
                f"{'TRUE_SMELLS_TOTAL':<26} {r['count_direct_tests']:>6} {r['count_wit_context']:>6}"
                f" {r['delta_abs']:>+6} {r['delta_pct']:>+6.1f}%"
                f"  {r['p_value']:>7.4f}  {sig:<3}{marker}"
            )
    # Métricas positivas em seção separada
    positives = [r for r in rows if r.get("is_positive_metric")]
    if positives:
        print()
        print("── Métricas positivas de qualidade (não são smells) ──")
        for r in positives:
            sig = "***" if r["p_value"] < 0.001 else ("**" if r["p_value"] < 0.01
                    else ("*" if r["p_value"] < 0.05 else ""))
            marker = " ↑" if r["delta_abs"] > 0 else (" ↓" if r["delta_abs"] < 0 else "")
            print(
                f"  {r['smell']:<24} {r['count_direct_tests']:>6} {r['count_wit_context']:>6}"
                f" {r['delta_abs']:>+6} {r['delta_pct']:>+6.1f}%"
                f"  {r['p_value']:>7.4f}  {sig:<3}{marker}"
            )

    # ── smell_density.csv: uma linha por arquivo (apenas smells reais) ───────
    density_path = smells_dir / "smell_density.csv"
    with open(density_path, "w", newline="") as f:
        w = csv.writer(f)
        w.writerow(["variant", "file", "smell_count", "true_smell_count"])
        for fdata in dt["files"]:
            true_cnt = sum(1 for s in fdata.get("smells", [])
                           if s["smell"] not in POSITIVE_METRICS)
            w.writerow(["direct-tests", Path(fdata["file"]).name,
                        fdata["smell_count"], true_cnt])
        for fdata in wc["files"]:
            true_cnt = sum(1 for s in fdata.get("smells", [])
                           if s["smell"] not in POSITIVE_METRICS)
            w.writerow(["wit-context", Path(fdata["file"]).name,
                        fdata["smell_count"], true_cnt])
    print(f"[smells] Densidades por arquivo: {density_path}")


if __name__ == "__main__":
    main()
