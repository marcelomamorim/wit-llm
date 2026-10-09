#!/usr/bin/env python3
"""Monta o diretório de mutação de uma variante.

O hash no nome do arquivo de teste deriva do pair_id, que inclui a chave da
variante — então os hashes de alvos.csv (do baseline) não servem para as
variantes. O join correto é pelo slug do par, que é o último segmento do
pair_id e é o que a coluna `pair` de alvos.csv guarda.
"""
import csv, shutil, sys, pathlib

R = pathlib.Path(__file__).parent
K = sys.argv[1]                                   # jdk-v2 | jdk-v3a
# 2o argumento opcional: diretorio de saida, para nao sobrescrever um
# resultado anterior que se queira preservar (ex.: mutacao-v4b)
D = R / (sys.argv[2] if len(sys.argv) > 2 else f"mutacao-{K.removeprefix('jdk-')}")
SRC = R / "mutacao"
MAT = R.parent / "generated/experiments/jdk-variantes" / K / "materialized"

man = MAT / "pairing-manifest.csv"
if not man.exists():
    sys.exit(f"sem pairing-manifest: {man}")

# slug do par -> {cenário: caminho materializado}
por_slug = {}
for r in csv.DictReader(open(man, encoding="utf-8", errors="replace")):
    slug = r["pair_id"].rsplit("/", 1)[-1]
    por_slug.setdefault(slug, {})[r["scenario"]] = r["materialized_path"]

for sub in ("testes/direct", "testes/wit", "mut", "work"):
    (D / sub).mkdir(parents=True, exist_ok=True)
for f in ("Mutador.java", "rodar.sh"):
    shutil.copy2(SRC / f, D / f)
for dd in ("classes", "orig"):
    if (SRC / dd).exists() and not (D / dd).exists():
        shutil.copytree(SRC / dd, D / dd)

ARM = {"direct": "direct-tests", "wit": "wit-context"}
linhas, achados = [], {"direct": 0, "wit": 0}
with open(SRC / "alvos.csv", encoding="utf-8") as fh:
    rd = csv.reader(fh)
    cab = next(rd)
    for row in rd:
        if len(row) < 7:
            continue
        slug = row[6]
        alvo = por_slug.get(slug)
        if not alvo:
            continue
        # o hash desta variante vem do caminho materializado
        qualquer = next(iter(alvo.values()))
        h = pathlib.Path(qualquer).stem.rsplit("_P", 1)[-1]
        ok = False
        for arm, cen in ARM.items():
            rel = alvo.get(cen)
            if not rel:
                continue
            src = MAT / "variants" / cen / "test/jdk/witup/generated" / rel
            if src.exists():
                shutil.copy2(src, D / "testes" / arm / src.name)
                achados[arm] += 1
                ok = True
        if ok:
            linhas.append(row[:5] + [h, slug])

with open(D / "alvos.csv", "w", newline="", encoding="utf-8") as fh:
    w = csv.writer(fh); w.writerow(cab); w.writerows(linhas)

print(f"  {K} -> {D.name}")
print(f"    sites casados: {len(linhas)} de 275"
      f"   testes direct: {achados['direct']}   wit: {achados['wit']}")
