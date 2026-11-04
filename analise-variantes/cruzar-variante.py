#!/usr/bin/env python3
import json,re,sys,pathlib,collections,zipfile,hashlib
K=sys.argv[1]; ROT=sys.argv[2]
B=pathlib.Path("..")/"generated/experiments/jdk-variantes"/K
X=pathlib.Path(f"jtrx-{K}")
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
# classificação estática por (arm,hash)
est={}
for f in sorted((B/"artifact/responses").glob("*.jsonl")):
    for line in open(f):
        try: d=json.loads(line)
        except: continue
        cid=d.get("custom_id",""); p=cid.split("/")
        if len(p)<4: continue
        arm="wit" if p[1]=="wit-context" else "direct"
        pid="/".join([p[0]]+p[2:]); h=hashlib.sha256(pid.encode()).hexdigest()[:12]
        parts=[]
        for o in (d.get("response",{}).get("body",{}).get("output") or []):
            for ct in (o.get("content") or []):
                if isinstance(ct,dict) and isinstance(ct.get("text"),str): parts.append(ct["text"])
        try: src="\n".join(x.get("content","") for x in json.loads("".join(parts)).get("files",[]))
        except Exception: continue
        m=re.match(r"^(.*)-([0-9]+)$",p[-1]); tipos=doc.get(m.group(1),set()) if m else set()
        caught={c.strip().split(".")[-1] for g in CATCH.findall(src) for c in g.split("|")}
        est[(arm,h)]= "forte" if (tipos&caught and SIG.search(src)) else \
                      "frágil" if (tipos&caught) else "outro"
RES=re.compile(r"^test result:\s*(.+)$",re.M); H=re.compile(r"_P([0-9a-f]{12})")
agg=collections.defaultdict(collections.Counter)
for arm,scen in (("direct","direct-tests"),("wit","wit-context")):
    for p in (X/scen).rglob("*.jtr"):
        h=H.search(p.stem)
        if not h: continue
        cls=est.get((arm,h.group(1)),"?")
        t=p.read_text(errors="replace"); r=RES.findall(t); st=r[-1] if r else ""
        a=agg[(arm,cls)]; a["n"]+=1
        if "Compilation failed" in st: a["nao_compila"]+=1
        elif st.startswith("Passed"): a["passa"]+=1
        else: a["falha_exec"]+=1
print(f"\n{'='*70}\n{ROT}\n{'='*70}")
print(f"  {'classe estática':12} {'':6} {'n':>6} {'compila':>8} {'passa':>7} {'pass|comp':>10}")
for arm in ("direct","wit"):
    for cls in ("forte","frágil","outro"):
        a=agg[(arm,cls)]
        if not a["n"]: continue
        comp=a["n"]-a["nao_compila"]
        print(f"  {cls:12} {arm.upper():6} {a['n']:>6} {comp:>8} {a['passa']:>7} "
              f"{100*a['passa']/comp if comp else 0:>9.1f}%")
print()
for arm in ("direct","wit"):
    f=agg[(arm,"forte")]; comp=f["n"]-f["nao_compila"]
    print(f"  >>> {arm.upper():8} verificação forte que COMPILA E PASSA: {f['passa']}  "
          f"(de {f['n']} gerados, {comp} compilam)")
