#!/usr/bin/env python3
"""Classifica os 8.250 testes pelo CÓDIGO-FONTE, não pela saída.

Remove a dependência de o teste imprimir: cobre 100% dos testes, incluindo
os 2.306 que passam em silêncio.
"""
import re, csv, json, pathlib, hashlib, collections, zipfile

HERE = pathlib.Path(__file__).parent
SRC  = HERE / "src-tests/sast2026-artifact-complete/generated-tests"

# --- tipo documentado por método (do wit_filtered.json) ----------------------
def slug(s): return re.sub(r'[^a-z0-9]+', '-', s.lower()).strip('-')
THROWNEW = re.compile(r"throw\s+new\s+([A-Za-z_$][\w.$]*)")
doc = collections.defaultdict(set)
with zipfile.ZipFile(pathlib.Path.home()/"Downloads/sast2026-figshare-complete.zip") as z:
    wit = json.loads(z.read("sast2026-artifact-complete/wit-expaths/wit_filtered.json"))
for c in wit["classes"]:
    for m in c.get("methods") or []:
        head = (m.get("qualifiedSignature") or "").split("(")[0]
        if "." not in head: continue
        cls, meth = head.rsplit(".", 1)
        t = THROWNEW.search(m.get("exception") or "")
        if t: doc[f"{slug(cls)}-{slug(meth)}"].add(t.group(1).split(".")[-1])

# --- padrões no código do teste ---------------------------------------------
CATCH   = re.compile(r"catch\s*\(\s*(?:final\s+)?([\w.$|\s]+?)\s+\w+\s*\)")
SIGNAL  = re.compile(r"throw\s+new\s+\w*(?:Exception|Error)|System\.exit\s*\(\s*[1-9]"
                     r"|Asserts?\.fail|\bassert\b|fail\s*\(")
TRYCNT  = re.compile(r"\btry\s*\{")
HASH    = re.compile(r"_P([0-9a-f]{12})")

# --- índice hash -> tipos documentados (via pair_id, como no join corrigido) --
h2types = {}
for r in csv.DictReader(open(HERE/"pair-level.csv")):
    hh = hashlib.sha256(r["pair_id"].encode()).hexdigest()[:12]
    m = re.match(r"^(.*)-([0-9]+)$", r["pair_id"].split("/")[-1])
    if m: h2types[hh] = doc.get(m.group(1), set())

# --- resultado da execução ---------------------------------------------------
run = {}
rd = HERE/"rerun-full-detail.csv"
if rd.exists():
    for r in csv.DictReader(open(rd)):
        run[(r["arm"], r["hash"])] = r

rows = []; agg = collections.defaultdict(collections.Counter)
for arm, scen in (("direct","direct-tests"), ("wit","wit-context")):
    for p in (SRC/scen).rglob("*.java"):
        src = p.read_text(errors="replace")
        h = HASH.search(p.stem)
        h = h.group(1) if h else ""
        types = h2types.get(h, set())
        caught = {c.strip().split(".")[-1] for grp in CATCH.findall(src)
                  for c in grp.split("|")}
        signals = bool(SIGNAL.search(src))
        right  = bool(types & caught) if types else None
        ex = run.get((arm, h), {})
        if right is True and signals:      cls_ = "verificação forte"
        elif right is True:                cls_ = "verificação frágil"
        elif caught and signals:           cls_ = "catch de outro tipo + sinaliza"
        elif caught:                       cls_ = "catch de outro tipo, sem sinal"
        elif signals:                      cls_ = "sinaliza, sem catch"
        else:                              cls_ = "não verifica"
        agg[arm][cls_] += 1
        rows.append(dict(arm=arm, hash=h, file=p.name,
                         doc_types=";".join(sorted(types)), caught=";".join(sorted(caught)),
                         signals_failure=signals, catches_documented=right,
                         try_blocks=len(TRYCNT.findall(src)), classification=cls_,
                         passed=ex.get("passed",""), comp_fail=ex.get("comp_fail","")))

W=72; print("="*W); print("ORÁCULO ESTÁTICO — classificação por código-fonte"); print("="*W)
cats = sorted({c for a in agg for c in agg[a]}, key=lambda c: -(agg["direct"][c]+agg["wit"][c]))
print(f"  {'classificação':34} {'Direct':>8} {'WIT':>8} {'Δ':>7}")
for c in cats:
    d,w = agg["direct"][c], agg["wit"][c]
    print(f"  {c:34} {d:>8} {w:>8} {w-d:>+7}")
for arm in ("direct","wit"):
    tot=sum(agg[arm].values()); strong=agg[arm]["verificação forte"]
    print(f"\n  {arm.upper():8} total={tot}  verificação forte={strong} ({100*strong/tot:.1f}%)")
with open(HERE/"static-oracle.csv","w",newline="") as fh:
    w_=csv.DictWriter(fh,fieldnames=list(rows[0].keys())); w_.writeheader(); w_.writerows(rows)
print(f"\ngravado: static-oracle.csv ({len(rows)} linhas)")
