#!/usr/bin/env python3
"""Classifica o oráculo dos testes gerados, direto das respostas do Batch API."""
import json,re,sys,pathlib,collections,zipfile,hashlib

VAR = sys.argv[1]
D = pathlib.Path("generated/experiments/jdk-variantes")/VAR/"respostas"

def slug(s): return re.sub(r'[^a-z0-9]+','-',s.lower()).strip('-')
TN=re.compile(r"throw\s+new\s+([A-Za-z_$][\w.$]*)")
with zipfile.ZipFile(pathlib.Path.home()/"Downloads/sast2026-figshare-complete.zip") as z:
    wit=json.loads(z.read("sast2026-artifact-complete/wit-expaths/wit_filtered.json"))
doc=collections.defaultdict(set)
for c in wit["classes"]:
    for m in c.get("methods") or []:
        hd=(m.get("qualifiedSignature") or "").split("(")[0]
        if "." in hd:
            cl,me=hd.rsplit(".",1); t=TN.search(m.get("exception") or "")
            if t: doc[f"{slug(cl)}-{slug(me)}"].add(t.group(1).split(".")[-1])

CATCH=re.compile(r"catch\s*\(\s*(?:final\s+)?([\w.$|\s]+?)\s+\w+\s*\)")
SIG=re.compile(r"throw\s+new\s+\w*(?:Exception|Error)|System\.exit\s*\(\s*[1-9]|Asserts?\.fail|\bassert\b|fail\s*\(")
PRINTFAIL=re.compile(r'println\s*\(\s*"FAIL')

agg=collections.defaultdict(collections.Counter); n_json_ruim=0; total=0
for f in sorted(D.rglob("*.jsonl")):
    if "error" in f.name: continue
    for line in open(f):
        try: d=json.loads(line)
        except: continue
        cid=d.get("custom_id","");  p=cid.split("/")
        if len(p)<4: continue
        arm = "wit" if p[1]=="wit-context" else "direct"
        total+=1
        parts=[]
        for o in (d.get("response",{}).get("body",{}).get("output") or []):
            for ct in (o.get("content") or []):
                if isinstance(ct,dict) and isinstance(ct.get("text"),str): parts.append(ct["text"])
        try:
            files=json.loads("".join(parts)).get("files") or []
            src="\n".join(x.get("content","") for x in files if isinstance(x,dict))
        except Exception:
            n_json_ruim+=1; agg[arm]["json inválido"]+=1; continue
        if not src.strip(): agg[arm]["vazio"]+=1; continue
        tail=p[-1]; m=re.match(r"^(.*)-([0-9]+)$",tail)
        tipos=doc.get(m.group(1),set()) if m else set()
        caught={c.strip().split(".")[-1] for g in CATCH.findall(src) for c in g.split("|")}
        voz=bool(SIG.search(src)); mira=bool(tipos & caught) if tipos else False
        if   mira and voz: agg[arm]["verificação forte"]+=1
        elif mira:         agg[arm]["verificação frágil"]+=1
        elif caught and voz: agg[arm]["catch de outro tipo + voz"]+=1
        elif caught:       agg[arm]["catch de outro tipo, mudo"]+=1
        elif voz:          agg[arm]["voz, sem catch"]+=1
        else:              agg[arm]["não verifica"]+=1
        if PRINTFAIL.search(src) and not voz: agg[arm]["(imprime FAIL sem voz)"]+=1

print(f"### {VAR}  — {total} respostas, {n_json_ruim} JSON inválido\n")
cats=["verificação forte","verificação frágil","catch de outro tipo + voz",
      "catch de outro tipo, mudo","voz, sem catch","não verifica","json inválido","vazio"]
print(f"  {'categoria':30} {'Direct':>8} {'WIT':>8}")
for c in cats:
    d,w=agg["direct"][c],agg["wit"][c]
    if d or w: print(f"  {c:30} {d:>8} {w:>8}")
print()
for arm in ("direct","wit"):
    tot=sum(agg[arm][c] for c in cats)
    f=agg[arm]["verificação forte"]; g=agg[arm]["verificação frágil"]
    mira=f+g
    print(f"  {arm.upper():8} mira o tipo={mira:>5}  forte={f:>5} ({100*f/tot if tot else 0:.1f}%)  "
          f"frágil={g:>5} ({100*g/mira if mira else 0:.0f}% da mira)")
