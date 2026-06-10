#!/usr/bin/env python3
"""
analyze_jcov.py — Extrai métricas completas de um jcov-result.xml.

Uso:
    python3 analyze_jcov.py <jcov-result.xml> [--output summary.json] [--variant NOME]

Saída: JSON com todas as métricas extraíveis do JCov.
"""

import json
import sys
import argparse
import xml.etree.ElementTree as ET
from collections import defaultdict


def parse_args():
    p = argparse.ArgumentParser()
    p.add_argument("xml_file", help="Caminho para o jcov-result.xml")
    p.add_argument("--output", default="-", help="Arquivo de saída JSON (default: stdout)")
    p.add_argument("--variant", default="unknown", help="Nome da variante (baseline, wit-context, direct-tests)")
    return p.parse_args()


def tag(el):
    t = el.tag
    return t.split('}')[-1] if '}' in t else t


def count_val(el):
    try:
        return int(el.get('count', '0'))
    except (ValueError, TypeError):
        return 0


def analyze(xml_file, variant):
    tree = ET.parse(xml_file)
    root = tree.getroot()

    # ── Contadores gerais ──────────────────────────────────────────────────────
    packages = set()
    classes_all = []
    methods_all = []

    # Branch por tipo
    branch_types = ['cond', 'case', 'default', 'fall', 'tg', 'catch']
    branch_cov   = defaultdict(int)
    branch_unc   = defaultdict(int)

    # Cond por val (true/false)
    cond_true_cov  = cond_true_unc  = 0
    cond_false_cov = cond_false_unc = 0

    # Method por modificador
    meth_by_mod = defaultdict(lambda: {'cov': 0, 'unc': 0})

    # Exit opcodes
    exit_cov = defaultdict(int)
    exit_unc = defaultdict(int)

    # Complexidade ciclomática por método
    method_complexity = []

    # ── Parse ──────────────────────────────────────────────────────────────────
    current_pkg   = None
    current_class = None
    current_meth  = None
    current_meth_branches = 0
    current_meth_covered  = 0

    for el in root.iter():
        t = tag(el)

        if t == 'package':
            current_pkg = el.get('name', '')
            packages.add(current_pkg)

        elif t == 'class':
            current_class = {
                'name':      el.get('name', ''),
                'flags':     el.get('flags', ''),
                'interface': el.get('interface', '') == 'true',
                'inner':     el.get('inner', ''),   # '', 'inner', 'anon'
                'package':   current_pkg,
            }
            classes_all.append(current_class)

        elif t == 'meth':
            if current_meth is not None:
                method_complexity.append({
                    'branches': current_meth_branches,
                    'covered':  current_meth_covered,
                })
            flags   = el.get('flags', '')
            cons    = el.get('cons', '') == 'true'
            clinit  = el.get('clinit', '') == 'true'
            current_meth = {
                'name':   el.get('name', ''),
                'flags':  flags,
                'cons':   cons,
                'clinit': clinit,
                'length': int(el.get('length', '0') or '0'),
            }
            current_meth_branches = 0
            current_meth_covered  = 0
            methods_all.append(current_meth)

        elif t == 'methenter':
            if current_meth is not None:
                c = count_val(el)
                current_meth['covered'] = c > 0
                flags  = current_meth['flags']
                cons   = current_meth['cons']
                clinit = current_meth['clinit']

                # por modificador
                if cons:
                    key = 'constructor'
                elif clinit:
                    key = 'static_initializer'
                elif 'private' in flags:
                    key = 'private'
                elif 'protected' in flags:
                    key = 'protected'
                elif 'public' in flags:
                    key = 'public'
                elif 'static' in flags:
                    key = 'static_package'
                else:
                    key = 'package_private'

                if c > 0:
                    meth_by_mod[key]['cov'] += 1
                else:
                    meth_by_mod[key]['unc'] += 1

                # por tipo de classe
                if current_class:
                    if current_class['interface']:
                        ck = 'interface'
                    elif current_class['inner'] == 'anon':
                        ck = 'anonymous'
                    elif current_class['inner'] == 'inner':
                        ck = 'inner'
                    elif 'enum' in current_class['flags']:
                        ck = 'enum'
                    elif 'abstract' in current_class['flags']:
                        ck = 'abstract'
                    else:
                        ck = 'concrete'
                    if c > 0:
                        meth_by_mod[f'class_{ck}']['cov'] += 1
                    else:
                        meth_by_mod[f'class_{ck}']['unc'] += 1

        elif t in branch_types:
            c = count_val(el)
            current_meth_branches += 1
            if c > 0:
                branch_cov[t] += 1
                current_meth_covered += 1
            else:
                branch_unc[t] += 1

            # cond true/false
            if t == 'cond':
                val = el.get('val', '')
                if val == 'true':
                    if c > 0: cond_true_cov  += 1
                    else:     cond_true_unc  += 1
                elif val == 'false':
                    if c > 0: cond_false_cov += 1
                    else:     cond_false_unc += 1

        elif t == 'exit':
            # exit não tem atributo count — apenas estrutural (indica tipo de retorno)
            opcode = el.get('opcode', 'unknown')
            exit_cov[opcode] += 1  # conta ocorrências totais por opcode

    # flush último método
    if current_meth is not None:
        method_complexity.append({
            'branches': current_meth_branches,
            'covered':  current_meth_covered,
        })

    # ── Agregar ────────────────────────────────────────────────────────────────
    def pct(cov, total):
        return round(cov * 100.0 / total, 2) if total > 0 else 0.0

    def branch_summary(btype):
        c = branch_cov[btype]
        u = branch_unc[btype]
        t = c + u
        return {'covered': c, 'uncovered': u, 'total': t, 'pct': pct(c, t)}

    # Métodos
    meth_cov   = sum(1 for m in methods_all if m.get('covered', False))
    meth_unc   = len(methods_all) - meth_cov
    meth_total = len(methods_all)

    # Branches totais
    b_cov_total = sum(branch_cov.values())
    b_unc_total = sum(branch_unc.values())
    b_total     = b_cov_total + b_unc_total

    # Complexidade ciclomática
    complexities = [m['branches'] for m in method_complexity if m['branches'] > 0]
    avg_complexity = round(sum(complexities) / len(complexities), 2) if complexities else 0
    max_complexity = max(complexities) if complexities else 0

    # Métodos altamente complexos não cobertos
    uncovered_complex = [
        m for m in method_complexity
        if m['branches'] >= 10 and m['covered'] == 0
    ]

    # Exit opcodes — estrutural, sem count de cobertura, indica tipos de retorno
    exit_opcodes = {op: cnt for op, cnt in sorted(exit_cov.items())}

    # Classes
    cls_total = len(classes_all)
    cls_interface = sum(1 for c in classes_all if c['interface'])
    cls_inner     = sum(1 for c in classes_all if c['inner'] == 'inner')
    cls_anon      = sum(1 for c in classes_all if c['inner'] == 'anon')
    cls_enum      = sum(1 for c in classes_all if 'enum' in c['flags'])
    cls_abstract  = sum(1 for c in classes_all if 'abstract' in c['flags'])

    # ── Resultado ──────────────────────────────────────────────────────────────
    result = {
        "variant": variant,
        "xml_file": xml_file,

        # Escopo
        "scope": {
            "packages": len(packages),
            "classes":  cls_total,
            "classes_interface": cls_interface,
            "classes_inner":     cls_inner,
            "classes_anonymous": cls_anon,
            "classes_enum":      cls_enum,
            "classes_abstract":  cls_abstract,
            "methods": meth_total,
        },

        # Cobertura de métodos
        "method_coverage": {
            "covered":   meth_cov,
            "uncovered": meth_unc,
            "total":     meth_total,
            "pct":       pct(meth_cov, meth_total),
        },

        # Cobertura de branches (total)
        "branch_coverage": {
            "covered":   b_cov_total,
            "uncovered": b_unc_total,
            "total":     b_total,
            "pct":       pct(b_cov_total, b_total),
        },

        # Cobertura por tipo de branch
        "branch_by_type": {
            "conditional":    branch_summary('cond'),
            "conditional_true":  {'covered': cond_true_cov,  'uncovered': cond_true_unc,  'total': cond_true_cov+cond_true_unc,   'pct': pct(cond_true_cov,  cond_true_cov+cond_true_unc)},
            "conditional_false": {'covered': cond_false_cov, 'uncovered': cond_false_unc, 'total': cond_false_cov+cond_false_unc, 'pct': pct(cond_false_cov, cond_false_cov+cond_false_unc)},
            "switch_case":    branch_summary('case'),
            "switch_default": branch_summary('default'),
            "fall_through":   branch_summary('fall'),
            "goto":           branch_summary('tg'),
            "exception_catch":branch_summary('catch'),
        },

        # Cobertura de métodos por modificador
        "method_by_modifier": {
            mod: {
                'covered':   v['cov'],
                'uncovered': v['unc'],
                'total':     v['cov'] + v['unc'],
                'pct':       pct(v['cov'], v['cov'] + v['unc']),
            }
            for mod, v in sorted(meth_by_mod.items())
        },

        # Exit/return coverage
        "exit_opcodes_count": exit_opcodes,
        "methods_with_throws": exit_opcodes.get('athrow', 0),

        # Complexidade ciclomática
        "cyclomatic_complexity": {
            "avg_branches_per_method": avg_complexity,
            "max_branches_per_method": max_complexity,
            "methods_with_high_complexity_uncovered": len(uncovered_complex),
        },
    }

    return result


def main():
    args = parse_args()
    result = analyze(args.xml_file, args.variant)

    output = json.dumps(result, indent=2, ensure_ascii=False)

    if args.output == '-':
        print(output)
    else:
        with open(args.output, 'w', encoding='utf-8') as f:
            f.write(output)
        print(f"Métricas salvas em: {args.output}", file=sys.stderr)


if __name__ == '__main__':
    main()
