#!/usr/bin/env python3
import re,sys,pathlib,collections
X=pathlib.Path(sys.argv[1]); nome=sys.argv[2]
SEC=re.compile(r"^-{5,}System\.out:\([^)]*\)-{5,}[ \t]*$\n?(.*?)(?=^-{5,}|^#section:|\Z)",re.S|re.M)
RES=re.compile(r"^test result:\s*(.+)$",re.M)
FAIL=re.compile(r"\bFAIL(ED)?\b",re.I)
EV=re.compile(r"(thrown|caught|raised|as expected)",re.I)
EXC=re.compile(r"\b[A-Za-z_$][\w.$]*(?:Exception|Error)\b")
print(f"\n{'='*66}\n{nome}\n{'='*66}")
print(f"  {'':8} {'total':>6} {'compila':>8} {'passa':>7} {'pass|comp':>10} {'c/ evid.':>9}")
R={}
for arm,scen in (("direct","direct-tests"),("wit","wit-context")):
    tot=comp=passed=ev=0
    for p in (X/scen).rglob("*.jtr"):
        t=p.read_text(errors="replace"); tot+=1
        r=RES.findall(t); st=r[-1] if r else ""
        if "Compilation failed" in st: continue
        comp+=1
        if st.startswith("Passed"):
            passed+=1
            out="\n".join(SEC.findall(t))
            if EV.search(out) and EXC.search(out) and not FAIL.search(out): ev+=1
    R[arm]=(tot,comp,passed,ev)
    print(f"  {arm.upper():8} {tot:>6} {comp:>8} {passed:>7} {100*passed/comp if comp else 0:>9.1f}% {ev:>9}")
d,w=R["direct"],R["wit"]
print(f"\n  evidência/compilados: Direct {100*d[3]/d[1]:.1f}%  WIT {100*w[3]/w[1]:.1f}%"
      f"   razão {w[3]/d[3] if d[3] else 0:.2f}x")
