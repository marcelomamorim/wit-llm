#!/usr/bin/env python3
"""Cobertura restrita aos métodos-alvo, com denominador idêntico entre braços.

Por que não usar a linha ALL: do RepGen: cada merge tem um universo diferente
de classes, porque o XML inclui as classes de teste geradas e as classes extra
que os testes carregaram. O baseline tem 4.016 classes, direct 4.165 e wit
4.163 — comparar numeradores desses universos sobre um denominador alheio não
mede nada.

Aqui o denominador é fixo: os métodos do manifest que aparecem em QUALQUER um
dos relatórios (união). Ausência de um método num relatório significa que a
classe nunca foi carregada naquela execução, o que é cobertura zero — não dado
faltante. Usar só o baseline como fonte do denominador excluiria os métodos que
os testes alcançaram e o baseline não, enviesando contra os braços.

Granularidade: (classe, nome do método). O manifest traz assinatura em sintaxe
Java e o RepGen em descritor da JVM; converter entre as duas é frágil, então
sobrecargas do mesmo nome são agregadas. A regra vale igual para os três
braços, então a comparação permanece justa — mas é mais grossa que por
assinatura, e isso precisa ser dito ao reportar.
"""
import csv, re, sys, pathlib, collections

RX_PKG = re.compile(r'^PKG[+-]:\s+(\S+)')
RX_CLS = re.compile(r'^CLS[+-]:\s+(\S+)')
RX_MTH = re.compile(r'^MTH([+-]):\s+(\S+?)\((.*?)\)(\S*)\s+hits:\s+(\d+)\s+(.*)$')
RX_MET = re.compile(r'(blocks|branches|lines):\s*(?:(\d+)%\s*\((\d+)/(\d+)\)|-)')


def ler_rep(caminho):
    """(classe_qualificada, metodo) -> dict agregado de hits e métricas."""
    out = collections.defaultdict(lambda: {"hits": 0, "blocks": [0, 0],
                                           "branches": [0, 0], "lines": [0, 0]})
    pkg = cls = None
    for ln in open(caminho, encoding="utf-8", errors="replace"):
        m = RX_PKG.match(ln)
        if m:
            pkg = m.group(1); continue
        m = RX_CLS.match(ln)
        if m:
            cls = m.group(1); continue
        m = RX_MTH.match(ln)
        if not m or cls is None:
            continue
        nome, hits, resto = m.group(2), int(m.group(5)), m.group(6)
        d = out[(f"{pkg}.{cls}" if pkg else cls, nome)]
        d["hits"] += hits
        for k, _p, cob, tot in RX_MET.findall(resto):
            if cob and tot:
                d[k][0] += int(cob); d[k][1] += int(tot)
    return out


def ler_manifest(caminho):
    """(classe_qualificada, metodo) dos métodos-alvo."""
    alvo = set()
    for r in csv.DictReader(open(caminho, encoding="utf-8", errors="replace")):
        cont = r["container_name"]
        nome = r["method_name"]
        simples = cont.rsplit(".", 1)[-1]
        # construtor: o manifest repete o nome da classe, o RepGen usa <init>
        alvo.add((cont, "<init>" if nome == simples else nome))
    return alvo


def main(manifest, base, *bracos):
    alvo = ler_manifest(manifest)
    reps = {"baseline": ler_rep(base)}
    for b in bracos:
        reps[pathlib.Path(b).name.replace("rep-", "")] = ler_rep(b)

    # denominador = união: presente em qualquer relatório
    uniao = set().union(*(set(r) for r in reps.values()))
    denom = {k for k in alvo if k in uniao}
    so_base = {k for k in alvo if k in reps["baseline"]}
    print(f"métodos-alvo (classe,nome) : {len(alvo)}")
    print(f"no denominador (união)     : {len(denom)}  ({100*len(denom)/len(alvo):.1f}%)")
    print(f"  dos quais no baseline    : {len(so_base)}")
    print(f"  só alcançados pelos braços: {len(denom-so_base)}")
    print(f"nunca carregados por ninguém: {len(alvo)-len(denom)}")
    print()

    # totais estruturais: de qualquer relatório que tenha o método
    tot = {}
    for k in denom:
        for met in ("blocks", "branches", "lines"):
            for r in reps.values():
                v = r.get(k, {}).get(met, [0, 0])
                if v[1]:
                    tot.setdefault(met, {})[k] = v[1]; break

    print(f"{'braço':14} {'métodos c/ hit':>15} {'blocks':>17} {'branches':>17} {'lines':>17}")
    base_row = None
    for nome, rep in reps.items():
        mh = sum(1 for k in denom if rep.get(k, {}).get("hits", 0) > 0)
        agg = {}
        for met in ("blocks", "branches", "lines"):
            c = sum(rep.get(k, {}).get(met, [0, 0])[0] for k in denom)
            t = sum(tot.get(met, {}).values())
            agg[met] = (c, t)
        row = (mh, agg)
        if base_row is None:
            base_row = row
        def f(met):
            c, t = agg[met]
            return f"{c:>7,}/{t:<8,}" if t else "—"
        print(f"{nome:14} {mh:>7,}/{len(denom):<7,} {f('blocks')} {f('branches')} {f('lines')}")

    print()
    print("— delta sobre o baseline, mesmo denominador:")
    bm, bagg = base_row
    for nome, rep in reps.items():
        if nome == "baseline":
            continue
        mh = sum(1 for k in denom if rep.get(k, {}).get("hits", 0) > 0)
        parts = [f"métodos {mh-bm:+,}"]
        for met in ("blocks", "branches", "lines"):
            c = sum(rep.get(k, {}).get(met, [0, 0])[0] for k in denom)
            bc, t = bagg[met]
            if t:
                parts.append(f"{met} {c-bc:+,} ({100*(c-bc)/t:+.2f}pp)")
        print(f"   {nome:14} " + "  ".join(parts))


if __name__ == "__main__":
    main(*sys.argv[1:])
