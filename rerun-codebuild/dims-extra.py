#!/usr/bin/env python3
"""Extrai dimensões que o paper não reporta, do XML JCov mesclado."""
import re,sys,collections
CNT=re.compile(r'<(catch|cond|br|goto|tg|case|fall|switch)\b[^>]*count="(\d+)"')
BL =re.compile(r'<bl\b[^>]*count="(\d+)"')
ATH=re.compile(r'<exit\b[^>]*opcode="athrow"')
MET=re.compile(r'<meth\b')
def medir(path):
    cov=collections.Counter(); tot=collections.Counter()
    athrow=0; meth=0; blcov=bltot=0
    with open(path,errors="replace") as f:
        for line in f:
            for t,c in CNT.findall(line):
                tot[t]+=1
                if int(c)>0: cov[t]+=1
            for c in BL.findall(line):
                bltot+=1
                if int(c)>0: blcov+=1
            athrow+=len(ATH.findall(line)); meth+=len(MET.findall(line))
    return cov,tot,athrow,meth,blcov,bltot
alvos=[("baseline","merge-jdk-v1/out/baseline+wit-context.xml")]  # placeholder
import pathlib
conj=[("Baseline","merge-jdk-v1/out/.."),]
rows={}
for rot,p in [("V1 Direct","merge-jdk-v1/out/baseline+direct-tests.xml"),
              ("V1 WIT","merge-jdk-v1/out/baseline+wit-context.xml"),
              ("V2 Direct","merge-jdk-v2/out/baseline+direct-tests.xml"),
              ("V2 WIT","merge-jdk-v2/out/baseline+wit-context.xml")]:
    if not pathlib.Path(p).exists(): continue
    rows[rot]=medir(p)
print(f"  {'':12} {'catch cob/tot':>16} {'%':>6} {'cond %':>8} {'athrow':>8} {'métodos':>8}")
for rot,(cov,tot,ath,meth,bc,bt) in rows.items():
    pc = 100*cov['catch']/tot['catch'] if tot['catch'] else 0
    pcond = 100*cov['cond']/tot['cond'] if tot['cond'] else 0
    print(f"  {rot:12} {cov['catch']:>7}/{tot['catch']:<8} {pc:>5.1f}% {pcond:>7.1f}% {ath:>8} {meth:>8}")
print()
print("  Detalhe por tipo de branch (cobertos/total):")
tipos=["cond","br","goto","tg","case","fall","switch","catch"]
print(f"  {'':12} " + " ".join(f"{t:>10}" for t in tipos))
for rot,(cov,tot,ath,meth,bc,bt) in rows.items():
    print(f"  {rot:12} " + " ".join(
        f"{100*cov[t]/tot[t]:>9.1f}%" if tot[t] else f"{'-':>10}" for t in tipos))
