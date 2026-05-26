# Resources

This directory is reserved for local research inputs, especially the WIT
replication package used by the phase-2 pilots and baseline generation scripts.

The contents are intentionally ignored by Git because the package is large and
environment-specific. The expected local layout is:

```text
resources/
└── wit-replication-package/
    ├── data/
    │   └── output/
    └── implementation/
        ├── run-wit.py
        └── wit.jar
```

Common scripts that read this directory:

- `scripts/gerar-baselines-wit.sh`
- `scripts/executar-piloto-commons-io-filtrado.sh`
- `scripts/executar-estudo-commons-io-filtrado.sh`

Do not commit extracted replication data, generated WIT outputs, Java project
checkouts, or local tool caches from this directory.
