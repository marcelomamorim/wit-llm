# Análise Estatística — WIT-context vs Direct-tests

Data: 2026-06-02  |  Pipeline: pit-eval t18  |  Projetos: 7

## Teste de Wilcoxon signed-rank unilateral (H₁: WIT > DIR)

| Métrica | n | Δ̄ (W−D) | p | r | Sig. |
|---|---|---|---|---|---|
| PIT Mutation Score (%) | 6 | +0.665 | 0.3438 | 0.381 | n.s. |
| Pass Rate Surefire (%) | 6 | +3.878 | 0.2188 | 0.286 | n.s. |
| Número de Testes | 6 | +19.000 | 0.3438 | 0.381 | n.s. |
| JaCoCo Line Coverage (%) | 5 | -1.024 | 0.7812 | 0.667 | n.s. |
| JaCoCo Branch Coverage (%) | 5 | -1.340 | 0.9062 | 0.800 | n.s. |
| Exception Assertion Rate (%) | 7 | +4.493 | 0.1484 | 0.250 | n.s. |
| Target Method Coverage (%) | 7 | -1.586 | 0.9279 | 0.964 | n.s. |
| Assertive Tests Rate (%) | 7 | -0.091 | 0.6999 | 0.714 | n.s. |
| Target Invocation Rate (%) | 7 | -1.641 | 0.9279 | 0.964 | n.s. |
| Test Method Presence Rate (%) | 7 | +4.394 | 0.0899 | 0.893 | ~ p<0.10 |
| Reflection Usage Rate (%) | 7 | +0.114 | 0.3274 | 0.929 | n.s. |

## Conclusão

Nenhuma métrica atinge p<0.05 a favor do WIT. A única tendência marginal é
**Test Method Presence Rate** (p=0.090, r=0.893), indicando que testes gerados
com contexto WIT tendem a conter mais métodos @Test.

Para PIT Mutation Score, o WIT tem média +0.67 pp acima do Direct e vence em
4/6 projetos, mas reversões em commons-lang, jackson e log4j2 eliminam a
significância estatística com n=6.

## Resultado por projeto — métricas-chave

### PIT %
| Projeto | WIT | DIR | Δ |
|---|---|---|---|
| commons-io | 12.27 | 9.66 | +2.61 |
| commons-lang | 4.14 | 4.71 | -0.57 |
| h2database | 1.48 | 0.93 | +0.55 |
| jackson-databind | 0.55 | 0.67 | -0.12 |
| joda-time | 9.96 | 7.17 | +2.79 |
| logging-log4j2 | 3.09 | 4.36 | -1.27 |

### Pass %
| Projeto | WIT | DIR | Δ |
|---|---|---|---|
| commons-io | 96.88 | 93.56 | +3.32 |
| commons-lang | 96.40 | 92.84 | +3.56 |
| h2database | 82.33 | 83.51 | -1.18 |
| jackson-databind | 89.47 | 95.24 | -5.77 |
| joda-time | 96.73 | 91.56 | +5.17 |
| logging-log4j2 | 94.23 | 76.06 | +18.17 |

### ExcpAss %
| Projeto | WIT | DIR | Δ |
|---|---|---|---|
| commons-io | 19.04 | 22.25 | -3.21 |
| commons-lang | 48.06 | 30.91 | +17.15 |
| h2database | 40.12 | 32.11 | +8.01 |
| httpcomponents-client | 22.56 | 22.35 | +0.21 |
| jackson-databind | 17.95 | 15.15 | +2.80 |
| joda-time | 19.33 | 22.94 | -3.61 |
| logging-log4j2 | 36.01 | 25.91 | +10.10 |

