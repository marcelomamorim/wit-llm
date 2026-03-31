# witup-llm

[![CI](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/ci.yml)
[![Release CLI](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml/badge.svg)](https://github.com/marcelomamorim/witup-llm/actions/workflows/release.yml)
[![Go Report Card](https://goreportcard.com/badge/github.com/marcelomamorim/witup-llm)](https://goreportcard.com/report/github.com/marcelomamorim/witup-llm)
![Go Version](https://img.shields.io/badge/Go-1.22+-00ADD8?logo=go&logoColor=white)
![Coverage](https://img.shields.io/badge/Coverage-go%20test%20-cover-success)
![Target](https://img.shields.io/badge/Target-Java%20projects-orange?logo=openjdk&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

`witup-llm` is an open source Go CLI for research on exception-path extraction in Java projects.

It prepares and compares three experiment branches:
- `WITUP_ONLY`
- `LLM_ONLY`
- `WITUP_PLUS_LLM`

The goal is to compare exception paths extracted from the WIT/WITUP replication package with exception paths inferred through a role-based LLM workflow, then reuse those artifacts for downstream tasks such as test generation and evaluation.

## Why this project exists

This repository supports a research workflow that asks:
- how well a static baseline (`WITUP`) extracts exception paths,
- how well an LLM-only pipeline does the same,
- whether combining both sources produces stronger derived artifacts.

The current implementation is intentionally `Java-only`.

## Core experiment branches

### `WITUP_ONLY`

Imports `wit.json` or `wit_filtered.json` from the local replication package under `resources/wit-replication-package`.

### `LLM_ONLY`

Runs a deterministic multi-agent workflow implemented by the repository:
1. `Archaeologist`
2. `Dependency Mesh`
3. `Expath Extractor`
4. `Skeptic Reviewer`

The workflow is code-orchestrated and provider-agnostic at the application layer. Providers only serve JSON completions.

### `WITUP_PLUS_LLM`

Merges the two branches only after they have run independently, preserving research validity.

## Tech stack

- Go 1.22+
- Java source projects as targets
- OpenAI-compatible and Ollama model adapters
- GitHub Actions for CI and release packaging

## Install

Clone the repository and build the CLI:

```bash
git clone git@github.com:marcelomamorim/witup-llm.git
cd witup-llm
make build
```

The binary will be available at `bin/witup`.

## Quick start

1. Copy the example configuration:

```bash
cp witup.toml.example witup.toml
```

2. Point `project.root` to the checked out Java project you want to analyze.

3. Configure at least one model under `[models]`.

4. Probe connectivity:

```bash
./bin/witup probe --config witup.toml --model openai_main
```

5. Run the three-branch experiment:

```bash
./bin/witup run-experiment \
  --config witup.toml \
  --model openai_main \
  --project-key commons-io
```

6. Generate tests from one prepared variant:

```bash
./bin/witup generate \
  --config witup.toml \
  --analysis generated/<run-id>/variants/witup_plus_llm.analysis.json \
  --model openai_main
```

## CLI banner

The CLI prints a banner on human-facing runs.

For scripted environments, disable it with:

```bash
WITUP_NO_BANNER=1 ./bin/witup help
```

JSON-oriented commands such as `probe --json` suppress the banner automatically.

## Commands

```text
models            List configured models
probe             Probe model connectivity and auth
ingest-witup      Import a WITUP baseline into canonical analysis JSON
analyze           Analyze methods with a direct LLM prompt
analyze-agentic   Analyze methods with the multi-agent LLM workflow
compare-sources   Compare canonical WITUP and LLM analysis artifacts
generate          Generate tests from an analysis artifact
evaluate          Run metrics and optional judge evaluation
run               Execute analyze -> generate -> evaluate
run-experiment    Prepare WITUP_ONLY, LLM_ONLY, and WITUP_PLUS_LLM
benchmark         Run coupled or matrix benchmark scenarios
```

## Reproducing the test suite

The project keeps local reproduction simple.

Run the full test suite:

```bash
make test
```

Run formatting, vetting, and tests together:

```bash
make quality
```

Generate a local coverage report:

```bash
make coverage
```

The explicit commands used underneath are:

```bash
go test ./...
go test ./... -coverprofile=coverage.out
go tool cover -func=coverage.out
```

If your environment restricts Go's global build cache, you can force a local cache:

```bash
mkdir -p .gocache
GOCACHE=$(pwd)/.gocache go test ./...
```

## GitHub Actions

This repository ships with:
- `.github/workflows/ci.yml`
  - formatting check
  - `go vet`
  - `go test`
  - coverage artifact upload
- `.github/workflows/release.yml`
  - cross-platform CLI builds for tagged releases
  - GitHub Release asset upload

## Artifacts and reproducibility

Each run writes a deterministic workspace under `pipeline.output_dir`.

Important folders:
- `sources/`
- `comparisons/`
- `variants/`
- `traces/`
- `generated-tests/`
- `prompts/`
- `responses/`

These artifacts are treated as first-class research outputs.

## Repository layout

```text
cmd/witup/main.go
.github/workflows/
docs/
internal/
  agentic/
  artifacts/
  catalog/
  config/
  domain/
  experiment/
  llm/
  metrics/
  pipeline/
  witup/
resources/
witup.toml.example
Makefile
go.mod
```

## Documentation

- [Architecture](docs/architecture.md)
- [Research context](docs/research_context.md)
- [Project review](docs/project_review.md)
- [Review guide](docs/review_guide.md)
- [Thesis foundation](docs/thesis_foundation.md)

If you want to understand the codebase quickly, start with [docs/review_guide.md](docs/review_guide.md).

## Roadmap

The core source experiment is implemented. The next major steps are:
- formal comparison between `WITUP` and `LLM`,
- dynamic validation over concrete counterexamples,
- mutation-focused evaluation,
- statistical aggregation for H1-H4.

## Contributing

1. Fork the repository.
2. Create a focused branch.
3. Keep changes small and tested.
4. Run `make quality`.
5. Open a pull request with reproducible steps.

## Security and responsible use

- Never commit API keys.
- Use environment variables for secrets.
- Persist raw model outputs in research runs.
- Do not treat LLM judges as formal oracles.
- Do not leak WITUP answers into the `LLM_ONLY` branch of the main experiment.

## License

MIT. See [LICENSE](LICENSE).
