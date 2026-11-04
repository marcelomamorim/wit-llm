#!/usr/bin/env python3
"""A1 — minera commits de correção de exceção que tocam os métodos-alvo."""
import subprocess, csv, re, collections, sys, pathlib

G = ["git", "--git-dir=jdk-history/.git"]
BASE = "da75f3c4ad5bdf25167a3ed80e51f567ab3dbd01"
MAN = "rerun-codebuild/manifest.csv"
MAX_DIFF = int(sys.argv[1]) if len(sys.argv) > 1 else 30

# arquivos-alvo e métodos por arquivo
alvo_metodos = collections.defaultdict(set)
for r in csv.DictReader(open(MAN)):
    alvo_metodos[r["file_path"]].add(r["method_name"])
print(f"alvos: {len(alvo_metodos)} arquivos, {sum(len(v) for v in alvo_metodos.values())} métodos")

EXC = re.compile(r"exception|throw|npe\b|ioobe|NullPointer|IllegalArgument|IllegalState|"
                 r"ClassCast|IndexOutOfBounds|UnsupportedOperation|ArithmeticEx",
                 re.I)

# commits pós-base que tocam arquivos-alvo
log = subprocess.run(G + ["log", "--no-merges", "--format=%H%x09%ad%x09%s", "--date=short",
                          f"{BASE}..origin/master", "--"] + list(alvo_metodos),
                     capture_output=True, text=True).stdout.splitlines()
print(f"commits que tocam alvos: {len(log)}")

cands = []
for linha in log:
    h, data, assunto = linha.split("\t", 2)
    if not EXC.search(assunto):
        continue
    # diff só dos arquivos-alvo
    st = subprocess.run(G + ["show", "--numstat", "--format=", h, "--"] + list(alvo_metodos),
                        capture_output=True, text=True).stdout.splitlines()
    tot = 0; arqs = []
    for l in st:
        p = l.split("\t")
        if len(p) == 3 and p[0].isdigit() and p[1].isdigit():
            tot += int(p[0]) + int(p[1]); arqs.append(p[2])
    if not arqs or tot == 0 or tot > MAX_DIFF:
        continue
    # métodos tocados: procura nomes de método-alvo nas hunks
    diff = subprocess.run(G + ["show", h, "--"] + arqs, capture_output=True, text=True).stdout
    tocados = set()
    for a in arqs:
        for m in alvo_metodos.get(a, ()):
            if re.search(rf"^[-+].*\b{re.escape(m)}\s*\(", diff, re.M) or \
               re.search(rf"@@.*\b{re.escape(m)}\b", diff):
                tocados.add(f"{a}::{m}")
    jbs = (re.match(r"^(\d{7,8}):", assunto) or [None, ""])[1] if re.match(r"^\d{7,8}:", assunto) else ""
    cands.append(dict(jbs=jbs, commit=h[:12], data=data, linhas=tot,
                      arquivos=";".join(arqs), metodos=";".join(sorted(tocados)),
                      n_metodos=len(tocados), assunto=assunto[:150]))

cands.sort(key=lambda c: (c["n_metodos"] == 0, c["linhas"]))
out = pathlib.Path("bug-candidatos.csv")
with out.open("w", newline="") as fh:
    w = csv.DictWriter(fh, fieldnames=list(cands[0].keys())); w.writeheader(); w.writerows(cands)
print(f"\ncandidatos (assunto de exceção, diff<={MAX_DIFF} linhas): {len(cands)}")
print(f"  com método-alvo identificado: {sum(1 for c in cands if c['n_metodos'])}")
print(f"\ntop 12 (menor diff, método identificado):")
for c in [c for c in cands if c["n_metodos"]][:12]:
    print(f"  [{c['data']}] {c['linhas']:>3}L  {c['metodos'][:46]:46} {c['assunto'][:62]}")
print(f"\ngravado: {out}")
