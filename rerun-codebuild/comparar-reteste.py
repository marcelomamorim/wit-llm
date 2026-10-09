#!/usr/bin/env python3
"""Compara, teste a teste, duas execuções dos mesmos shards.

Mede a variância da camada de execução: quantos testes mudam de resultado
entre duas rodadas do mesmo código no mesmo ambiente. Compilação é
determinística; a execução usa -timeout:4 com concorrência, então teste
sensível a tempo pode virar.
"""
import collections, pathlib, re, sys, tarfile

RES = re.compile(r"^test result:\s*(.+)$", re.M)

def desfecho(txt):
    r = RES.findall(txt)
    st = r[-1] if r else ""
    if "Compilation failed" in st: return "nao_compila"
    if st.startswith("Passed"):    return "passa"
    if not st:                     return "sem_resultado"
    return "falha"

def ler(raiz: pathlib.Path):
    """nome do teste -> desfecho"""
    out = {}
    for tgz in sorted(raiz.rglob("*.tgz")):
        try:
            with tarfile.open(tgz) as t:
                for m in t.getmembers():
                    if not m.name.endswith(".jtr"): continue
                    f = t.extractfile(m)
                    if f is None: continue
                    out[pathlib.Path(m.name).stem] = desfecho(
                        f.read().decode("utf-8", "replace"))
        except Exception as e:
            print(f"  aviso: {tgz.name}: {e}", file=sys.stderr)
    return out

def main(dir_a, dir_b):
    A, B = ler(pathlib.Path(dir_a)), ler(pathlib.Path(dir_b))
    comuns = set(A) & set(B)
    print(f"testes na execução 1 : {len(A):,}")
    print(f"testes na execução 2 : {len(B):,}")
    print(f"em comum             : {len(comuns):,}")
    print(f"só em uma            : {len(set(A) ^ set(B)):,}")
    if not comuns:
        return
    iguais = sum(1 for k in comuns if A[k] == B[k])
    mudou = [k for k in comuns if A[k] != B[k]]
    print(f"\nmesmo desfecho       : {iguais:,}  ({100*iguais/len(comuns):.2f}%)")
    print(f"MUDOU de desfecho    : {len(mudou):,}  ({100*len(mudou)/len(comuns):.2f}%)")
    if mudou:
        trans = collections.Counter((A[k], B[k]) for k in mudou)
        print("\ntransições:")
        for (a, b), n in trans.most_common():
            print(f"   {a:14} -> {b:14} {n:5}")
        print("\nexemplos:")
        for k in mudou[:8]:
            print(f"   {k[:64]:66} {A[k]} -> {B[k]}")

if __name__ == "__main__":
    main(*sys.argv[1:])
