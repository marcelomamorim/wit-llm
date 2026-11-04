#!/usr/bin/env python3
"""Analisa os 8.250 .jtr da re-execução instrumentada."""
import re, csv, json, pathlib, collections, statistics

HERE = pathlib.Path(__file__).parent
X = HERE / "jtrx"
SEC = re.compile(r"^-{5,}System\.out:\([^)]*\)-{5,}[ \t]*$\n?(.*?)(?=^-{5,}|^#section:|\Z)", re.S | re.M)
ERRSEC = re.compile(r"^-{5,}System\.err:\([^)]*\)-{5,}[ \t]*$\n?(.*?)(?=^-{5,}|^#section:|\Z)", re.S | re.M)
HASH = re.compile(r"_P([0-9a-f]{12})")
FAILL = re.compile(r"^.*\bFAIL(ED)?\b.*$", re.I | re.M)
PASSL = re.compile(r"^.*\bPASS(ED)?\b.*$", re.I | re.M)
ELAPSED = re.compile(r"^elapsed=(\d+)", re.M)
EXECST = re.compile(r"^test result:\s*(.+)$", re.M)
JAVACERR = re.compile(r"error:\s*(.+)")
THROWNEX = re.compile(r"\b([A-Za-z_$][\w.$]*(?:Exception|Error))\b")

def bucket(msg):
    m = msg.lower()
    if "cannot find symbol" in m: return "símbolo inexistente (import/API)"
    if "is not public" in m or "cannot be accessed" in m or "has private access" in m:
        return "API interna inacessível"
    if "cannot be applied to given types" in m or "no suitable" in m:
        return "assinatura incompatível (API alucinada)"
    if "incompatible types" in m: return "tipos incompatíveis"
    if "unreported exception" in m: return "exceção checada não tratada"
    if "is not abstract and does not override" in m: return "abstrata não implementada"
    if "constructor" in m: return "construtor inválido"
    if "already defined" in m or "duplicate" in m: return "definição duplicada"
    if "package" in m and "does not exist" in m: return "pacote inexistente"
    return "outros"

rows = {}
pl = HERE / "pair-level.csv"
if pl.exists():
    import hashlib
    for r in csv.DictReader(open(pl)):
        h = hashlib.sha256(r["pair_id"].encode()).hexdigest()[:12]
        rows[h] = r

R = {}
for arm, scen in (("direct", "direct-tests"), ("wit", "wit-context")):
    st = collections.Counter(); comp = collections.Counter()
    times = []; exc_dyn = 0; passl = 0; faill = 0
    npass = ncompfail = nexecfail = 0
    excs = collections.Counter(); detail = []
    for p in (X / scen).rglob("*.jtr"):
        t = p.read_text(errors="replace")
        out = "\n".join(SEC.findall(t)); err = "\n".join(ERRSEC.findall(t))
        allr = EXECST.findall(t)
        esv = allr[-1] if allr else ""          # última linha 'test result:' = veredito final
        passed = esv.startswith("Passed")
        cfail  = "Compilation failed" in esv
        el = ELAPSED.findall(t)
        if el: times.append(int(el[-1]))
        has_fail = bool(FAILL.search(out)); has_pass = bool(PASSL.search(out))
        passl += len(PASSL.findall(out)); faill += len(FAILL.findall(out))
        if cfail:
            ncompfail += 1
            e = JAVACERR.findall(err) or JAVACERR.findall(t)
            comp[bucket(e[0]) if e else "sem diagnóstico"] += 1
            st["compilação"] += 1
        elif passed:
            npass += 1
            if has_fail: st["PASS mas stdout diz FAIL"] += 1
            elif has_pass: st["pass genuíno"] += 1
            else: st["pass sem saída"] += 1
            if re.search(r"(thrown|caught|raised|as expected)", out, re.I) and THROWNEX.search(out):
                exc_dyn += 1
        else:
            nexecfail += 1; st["falha em execução"] += 1
            m = THROWNEX.findall(esv)
            if m: excs[m[-1]] += 1
        h = HASH.search(p.stem)
        if h:
            detail.append(dict(arm=arm, hash=h.group(1), passed=passed, comp_fail=cfail,
                               stdout_fail=has_fail, elapsed_ms=el[-1] if el else "",
                               exec_status=esv[:120], stdout=out.strip()[:300]))
    R[arm] = dict(st=st, comp=comp, times=times, exc_dyn=exc_dyn, passl=passl, faill=faill,
                  npass=npass, ncompfail=ncompfail, nexecfail=nexecfail, excs=excs, detail=detail)

ORIG = {"direct": dict(p=1455, c=2288), "wit": dict(p=1607, c=2271)}
W = 66
def h(s): print("\n" + "=" * W); print(s); print("=" * W)

h("1. REPRODUÇÃO  (a execução bate com a original?)")
print(f"{'':10} {'pass novo':>10} {'pass orig':>10} {'Δ':>6} | {'compil. novo':>13} {'orig':>6}")
for a in ("direct", "wit"):
    d = R[a]
    print(f"{a.upper():10} {d['npass']:>10} {ORIG[a]['p']:>10} {d['npass']-ORIG[a]['p']:>+6} | "
          f"{d['ncompfail']:>13} {ORIG[a]['c']:>6}")

h("2. APROVAÇÃO ESTRITA  (o 'pass' era genuíno?)")
for a in ("direct", "wit"):
    d = R[a]; falso = d["st"]["PASS mas stdout diz FAIL"]
    print(f"\n{a.upper()}")
    for k, v in d["st"].most_common(): print(f"   {k:32} {v:>6}")
    print(f"   {'-'*32} {'-'*6}")
    print(f"   {'PASS ESTRITO':32} {d['npass']-falso:>6}   (reportado {d['npass']}, falsos {falso})")
ds = R["direct"]["npass"] - R["direct"]["st"]["PASS mas stdout diz FAIL"]
ws = R["wit"]["npass"] - R["wit"]["st"]["PASS mas stdout diz FAIL"]
print(f"\n   Direct {ds}  vs  WIT {ws}   (Δ {ws-ds:+d})")
print(f"   antes: Direct {R['direct']['npass']} vs WIT {R['wit']['npass']} (Δ {R['wit']['npass']-R['direct']['npass']:+d})")
print(f"   >>> {'WIT SE MANTÉM' if ws>ds else 'INVERTE — Direct à frente'}")

h("3. EVIDÊNCIA DINÂMICA  (a exceção foi comprovadamente lançada?)")
for a in ("direct", "wit"):
    d = R[a]
    print(f"   {a.upper():8} {d['exc_dyn']:>6}  ({100*d['exc_dyn']/d['npass']:.1f}% dos aprovados)")

h("4. TAXONOMIA DAS FALHAS DE COMPILAÇÃO")
cats = sorted(set(R["direct"]["comp"]) | set(R["wit"]["comp"]),
              key=lambda c: -(R["direct"]["comp"][c] + R["wit"]["comp"][c]))
print(f"   {'categoria':36} {'Direct':>8} {'WIT':>8} {'Δ':>7}")
for c in cats:
    a, b = R["direct"]["comp"][c], R["wit"]["comp"][c]
    print(f"   {c:36} {a:>8} {b:>8} {b-a:>+7}")

h("5. ASSERÇÕES E TEMPO")
for a in ("direct", "wit"):
    d = R[a]; tot = d["passl"] + d["faill"]
    md = statistics.median(d["times"]) if d["times"] else 0
    print(f"   {a.upper():8} checagens PASS {d['passl']:>6}  FAIL {d['faill']:>6}  "
          f"acerto {100*d['passl']/tot if tot else 0:5.1f}%   tempo mediano {md/1000:5.2f}s")

h("6. EXCEÇÕES NAS FALHAS DE EXECUÇÃO (top 6)")
for a in ("direct", "wit"):
    print(f"   {a.upper()}: " + ", ".join(f"{k.split('.')[-1]}={v}" for k, v in R[a]["excs"].most_common(6)))

json.dump({a: {"pass": R[a]["npass"], "strict": (ds if a=="direct" else ws),
               "comp_fail": R[a]["ncompfail"], "exc_dyn": R[a]["exc_dyn"],
               "status": dict(R[a]["st"]), "comp_tax": dict(R[a]["comp"])}
           for a in R}, open(HERE/"rerun-full-verdict.json","w"), indent=2, ensure_ascii=False)
with open(HERE/"rerun-full-detail.csv","w",newline="") as fh:
    allr = R["direct"]["detail"] + R["wit"]["detail"]
    w_ = csv.DictWriter(fh, fieldnames=list(allr[0].keys())); w_.writeheader(); w_.writerows(allr)
print(f"\ngravado: rerun-full-verdict.json + rerun-full-detail.csv ({len(R['direct']['detail'])+len(R['wit']['detail'])} linhas)")
