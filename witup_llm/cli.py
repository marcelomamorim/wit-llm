from __future__ import annotations

import argparse
import sys
from pathlib import Path

from witup_llm.config import load_config
from witup_llm.domain import build_benchmark_scenarios
from witup_llm.pipelines import PipelineService
from witup_llm.pipelines import load_analysis_report
from witup_llm.pipelines import load_generation_report


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        prog="witup",
        description=(
            "AI pipeline CLI for exception-path analysis, unit-test generation, "
            "evaluation, and model benchmarking."
        ),
    )
    subparsers = parser.add_subparsers(dest="command", required=True)

    models = subparsers.add_parser("models", help="List configured models.")
    models.add_argument("--config", required=True, help="Path to the TOML config file.")

    analyze = subparsers.add_parser("analyze", help="Find exception paths for all methods.")
    analyze.add_argument("--config", required=True, help="Path to the TOML config file.")
    analyze.add_argument("--model", required=True, help="Configured model key to use.")

    generate = subparsers.add_parser("generate", help="Generate unit tests from an analysis report.")
    generate.add_argument("--config", required=True, help="Path to the TOML config file.")
    generate.add_argument("--analysis", required=True, help="Path to an analysis.json file.")
    generate.add_argument("--model", required=True, help="Configured model key to use.")

    evaluate = subparsers.add_parser("evaluate", help="Run metrics and optional judge evaluation.")
    evaluate.add_argument("--config", required=True, help="Path to the TOML config file.")
    evaluate.add_argument("--analysis", required=True, help="Path to an analysis.json file.")
    evaluate.add_argument("--generation", required=True, help="Path to a generation.json file.")
    evaluate.add_argument(
        "--judge-model",
        help="Configured model key used as the AI judge. Defaults to [pipeline].judge_model.",
    )

    run = subparsers.add_parser("run", help="Run analysis, generation, and evaluation together.")
    run.add_argument("--config", required=True, help="Path to the TOML config file.")
    run.add_argument("--analysis-model", required=True, help="Model key for exception-path analysis.")
    run.add_argument("--generation-model", required=True, help="Model key for test generation.")
    run.add_argument(
        "--judge-model",
        help="Configured model key used as the AI judge. Defaults to [pipeline].judge_model.",
    )

    benchmark = subparsers.add_parser(
        "benchmark",
        help=(
            "Run benchmarks as coupled models (--model) or as an analysis/generation matrix "
            "(--analysis-model with --generation-model)."
        ),
    )
    benchmark.add_argument("--config", required=True, help="Path to the TOML config file.")
    benchmark.add_argument(
        "--model",
        action="append",
        dest="models",
        help="Configured model key to benchmark. Repeat this flag for multiple models.",
    )
    benchmark.add_argument(
        "--analysis-model",
        action="append",
        dest="analysis_models",
        help="Analysis model key for benchmark matrix mode. Repeat for multiple models.",
    )
    benchmark.add_argument(
        "--generation-model",
        action="append",
        dest="generation_models",
        help="Generation model key for benchmark matrix mode. Repeat for multiple models.",
    )
    benchmark.add_argument(
        "--judge-model",
        help="Configured model key used as the AI judge. Defaults to [pipeline].judge_model.",
    )

    return parser


def main(argv: list[str] | None = None) -> int:
    parser = build_parser()
    args = parser.parse_args(argv)
    service = PipelineService()

    if args.command == "models":
        config = load_config(args.config)
        for key, model in sorted(config.models.items()):
            print(f"{key}: provider={model.provider} model={model.model} base_url={model.base_url}")
        return 0

    if args.command == "analyze":
        config = load_config(args.config)
        analysis_report, analysis_path, workspace = service.analyze(config, args.model)
        print(f"Run dir      : {workspace.root}")
        print(f"Analysis path: {analysis_path}")
        print(f"Methods      : {analysis_report.total_methods}")
        print(f"Model        : {analysis_report.model_key}")
        return 0

    if args.command == "generate":
        config = load_config(args.config)
        analysis_path = Path(args.analysis).resolve()
        analysis_report = load_analysis_report(analysis_path)
        generation_report, generation_path, workspace = service.generate(
            config,
            analysis_report,
            analysis_path,
            args.model,
        )
        print(f"Run dir        : {workspace.root}")
        print(f"Generation path: {generation_path}")
        print(f"Generated files: {len(generation_report.test_files)}")
        print(f"Tests dir      : {workspace.tests}")
        return 0

    if args.command == "evaluate":
        config = load_config(args.config)
        analysis_path = Path(args.analysis).resolve()
        generation_path = Path(args.generation).resolve()
        analysis_report = load_analysis_report(analysis_path)
        generation_report = load_generation_report(generation_path)
        judge_model = args.judge_model or config.pipeline.judge_model
        evaluation_report, evaluation_path, workspace = service.evaluate(
            config,
            analysis_report,
            analysis_path,
            generation_report,
            generation_path,
            judge_model_key=judge_model,
        )
        print(f"Run dir         : {workspace.root}")
        print(f"Evaluation path : {evaluation_path}")
        print(f"Metric score    : {format_score(evaluation_report.metric_score)}")
        print(f"Combined score  : {format_score(evaluation_report.combined_score)}")
        if evaluation_report.judge_evaluation is not None:
            print(f"Judge verdict   : {evaluation_report.judge_evaluation.verdict}")
        return 0

    if args.command == "run":
        config = load_config(args.config)
        judge_model = args.judge_model or config.pipeline.judge_model
        result = service.run(
            config,
            analysis_model_key=args.analysis_model,
            generation_model_key=args.generation_model,
            judge_model_key=judge_model,
        )
        evaluation_report = result["evaluation_report"]
        print(f"Run dir         : {result['workspace']}")
        print(f"Analysis path   : {result['analysis_path']}")
        print(f"Generation path : {result['generation_path']}")
        print(f"Evaluation path : {result['evaluation_path']}")
        print(f"Combined score  : {format_score(evaluation_report.combined_score)}")
        return 0

    if args.command == "benchmark":
        config = load_config(args.config)
        judge_model = args.judge_model or config.pipeline.judge_model
        try:
            scenarios = build_benchmark_scenarios(
                model_keys=args.models,
                analysis_model_keys=args.analysis_models,
                generation_model_keys=args.generation_models,
            )
        except ValueError as exc:
            parser.error(str(exc))
            return 2
        report, benchmark_path = service.benchmark(
            config,
            scenarios=scenarios,
            judge_model_key=judge_model,
        )
        print(f"Benchmark path: {benchmark_path}")
        for entry in report.entries:
            print(
                f"#{entry.rank} {entry.analysis_model_key}->{entry.generation_model_key} "
                f"combined={format_score(entry.combined_score)} "
                f"metric={format_score(entry.metric_score)} "
                f"judge={format_score(entry.judge_score)}"
            )
        return 0

    parser.error(f"Unknown command `{args.command}`.")
    return 2


def format_score(value: float | None) -> str:
    if value is None:
        return "-"
    return f"{value:.2f}"


if __name__ == "__main__":
    sys.exit(main())
