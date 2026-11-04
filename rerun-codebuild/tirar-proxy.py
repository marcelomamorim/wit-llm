#!/usr/bin/env python3
"""Remove o pacote com.sun.proxy dos XML do JCov.

As classes $ProxyN são geradas em runtime pelo java.lang.reflect.Proxy e
recebem nome sequencial por JVM: o $Proxy1 de um shard proxia uma interface, o
de outro proxia outra. O Merger vê o mesmo nome com estrutura diferente e
aborta o arquivo inteiro ("Method size mismatch"). Com -boe skip isso vira
descarte silencioso do shard, e a cobertura sai calculada sobre menos shards
em um braço que no outro.

Essas classes não existem no fonte do JDK nem entre os arquivos-alvo, então
removê-las não altera a cobertura medida.
"""
import re, sys, pathlib

ABRE = re.compile(r'<package\s+name="com\.sun\.proxy')

def limpar(p: pathlib.Path) -> tuple[int, int]:
    saida, dentro, removidas, prof = [], False, 0, 0
    with p.open(encoding="utf-8", errors="surrogateescape") as fh:
        for ln in fh:
            if not dentro and ABRE.search(ln):
                dentro, prof = True, ln.count("<package") - ln.count("</package>")
                removidas += 1
                if prof <= 0:
                    dentro = False
                continue
            if dentro:
                if "</package>" in ln:
                    prof -= 1
                    if prof <= 0:
                        dentro = False
                continue
            saida.append(ln)
    if removidas:
        tmp = p.with_suffix(".tmp")
        with tmp.open("w", encoding="utf-8", errors="surrogateescape") as fh:
            fh.writelines(saida)
        tmp.replace(p)
    return removidas, len(saida)

if __name__ == "__main__":
    tot = alterados = 0
    for arg in sys.argv[1:]:
        for f in sorted(pathlib.Path().glob(arg)) or [pathlib.Path(arg)]:
            if not f.is_file():
                continue
            n, _ = limpar(f)
            tot += 1
            if n:
                alterados += 1
                print(f"  {f}: removido {n} pacote(s) com.sun.proxy")
    print(f"— processados {tot}, alterados {alterados}")
