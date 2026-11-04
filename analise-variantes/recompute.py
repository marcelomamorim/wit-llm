#!/usr/bin/env python3
"""Recalcula as métricas do paper sobre a base estrita."""
import csv, hashlib, pathlib, collections, re
HERE = pathlib.Path(__file__).parent

# ── status estrito por (arm, hash) ───────────────────────────────────────────
EV = re.compile(r"(thrown|caught|raised|as expected)", re.I)
EX = re.compile(r"\b[A-Za-z_$][\w.$]*(?:Exception|Error)\b")
S = {}
for r in csv.DictReader(open(HERE/"rerun-full-detail.csv")):
    passed = r["passed"] == "True"
    failline = r["stdout_fail"] == "True"
    out = r["stdout"]
    S[(r["arm"], r["hash"])] = dict(
        compiled = r["comp_fail"] != "True",
        pass_rep = passed,                       # como o paper conta hoje
        pass_str = passed and not failline,      # estrito (limite superior)
        pass_ev  = passed and not failline and bool(EV.search(out) and EX.search(out)),
    )

# ── expaths por método ───────────────────────────────────────────────────────
exp = {}
mp = HERE/"manifest.csv"
if not mp.exists():
    import subprocess
    subprocess.run(["unzip","-p",str(pathlib.Path.home()/"Downloads/sast2026-figshare-complete.zip"),
                    "sast2026-artifact-complete/manifest/manifest_jdk_global_methods.csv"],
                   stdout=open(mp,"wb"), check=True)
for r in csv.DictReader(open(mp)):
    exp[r["method_id"]] = int(r["expath_count"])

pairs = []
miss = 0
for r in csv.DictReader(open(HERE/"pair-level.csv")):
    h = hashlib.sha256(r["pair_id"].encode()).hexdigest()[:12]
    d, w = S.get(("direct", h)), S.get(("wit", h))
    if not d or not w: miss += 1; continue
    # densidade: usa TODOS os method_ids (correção do join anterior)
    ids = [x for x in r["covered_method_ids"].split(";") if x]
    cnts = [exp[i] for i in ids if i in exp]
    pairs.append(dict(d=d, w=w, exp=max(cnts) if cnts else None))
print(f"pares casados: {len(pairs)}   sem .jtr: {miss}   sem expath: {sum(1 for p in pairs if p['exp'] is None)}")

N = len(pairs)
def pct(a, b): return f"{100*a/b:.2f}%" if b else "—"
W = 70
def h(s): print("\n"+"="*W); print(s); print("="*W)

h("1. TAXA DE APROVAÇÃO GERAL  (paper: 35,27% vs 38,96%)")
for lab, key in (("reportado","pass_rep"), ("ESTRITO","pass_str"), ("c/ evidência","pass_ev")):
    dv = sum(p["d"][key] for p in pairs); wv = sum(p["w"][key] for p in pairs)
    print(f"  {lab:14} Direct {dv:>5} ({pct(dv,N)})   WIT {wv:>5} ({pct(wv,N)})   Δ {wv-dv:>+5}")

h("2. APROVAÇÃO CONDICIONADA À COMPILAÇÃO  (paper: 79,21% vs 86,68%)")
dc = sum(p["d"]["compiled"] for p in pairs); wc = sum(p["w"]["compiled"] for p in pairs)
for lab, key in (("reportado","pass_rep"), ("ESTRITO","pass_str"), ("c/ evidência","pass_ev")):
    dv = sum(p["d"][key] for p in pairs); wv = sum(p["w"][key] for p in pairs)
    print(f"  {lab:14} Direct {pct(dv,dc):>8} ({dv}/{dc})   WIT {pct(wv,wc):>8} ({wv}/{wc})")

h("3. HEAD-TO-HEAD nos pares que compilam nos DOIS  (paper: 203 vs 103)")
both = [p for p in pairs if p["d"]["compiled"] and p["w"]["compiled"]]
for lab, key in (("reportado","pass_rep"), ("ESTRITO","pass_str"), ("c/ evidência","pass_ev")):
    bb = sum(1 for p in both if p["d"][key] and p["w"][key])
    ow = sum(1 for p in both if p["w"][key] and not p["d"][key])
    od = sum(1 for p in both if p["d"][key] and not p["w"][key])
    nn = len(both)-bb-ow-od
    ratio = f"{ow/od:.2f}x" if od else "—"
    print(f"  {lab:14} ambos {bb:>4}  só WIT {ow:>4}  só Direct {od:>4}  nenhum {nn:>4}   razão {ratio}")
print(f"  (n = {len(both)} pares)")

h("4. DENSIDADE DE EXPATHS  (join corrigido: todos os method_ids)")
def buck(n): return "1" if n==1 else "2" if n==2 else "3-4" if n<=4 else "5+"
agg = collections.defaultdict(list)
for p in pairs:
    if p["exp"]: agg[buck(p["exp"])].append(p)
print(f"  {'expaths':>8} {'n':>5} | {'Direct estrito':>15} {'WIT estrito':>13} {'Δ':>7}")
for b in ["1","2","3-4","5+"]:
    g = agg.get(b) or []
    if not g: continue
    bc = [p for p in g if p["d"]["compiled"] and p["w"]["compiled"]]
    if not bc: continue
    dv = 100*sum(p["d"]["pass_str"] for p in bc)/len(bc)
    wv = 100*sum(p["w"]["pass_str"] for p in bc)/len(bc)
    print(f"  {b:>8} {len(bc):>5} | {dv:>14.1f}% {wv:>12.1f}% {wv-dv:>+6.1f}pp")
print("  (condicionado a ambos compilarem)")
