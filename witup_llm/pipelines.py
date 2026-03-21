from __future__ import annotations

from pathlib import Path

from witup_llm.artifacts import RunWorkspace
from witup_llm.artifacts import new_run_id
from witup_llm.artifacts import read_json
from witup_llm.artifacts import safe_relative_path
from witup_llm.artifacts import slugify
from witup_llm.artifacts import utc_timestamp
from witup_llm.artifacts import write_json
from witup_llm.artifacts import write_text
from witup_llm.config import load_config
from witup_llm.domain import BenchmarkScenario
from witup_llm.llm import HttpLLMClient
from witup_llm.metrics import MetricRunner
from witup_llm.metrics import MetricRuntimeContext
from witup_llm.metrics import aggregate_metric_score
from witup_llm.models import AnalysisReport
from witup_llm.models import AppConfig
from witup_llm.models import BenchmarkEntry
from witup_llm.models import BenchmarkReport
from witup_llm.models import EvaluationReport
from witup_llm.models import ExceptionPath
from witup_llm.models import GeneratedTestFile
from witup_llm.models import GenerationReport
from witup_llm.models import JudgeEvaluation
from witup_llm.models import MethodAnalysis
from witup_llm.models import MethodDescriptor
from witup_llm.prompts import build_analysis_system_prompt
from witup_llm.prompts import build_analysis_user_prompt
from witup_llm.prompts import build_benchmark_markdown
from witup_llm.prompts import build_generation_system_prompt
from witup_llm.prompts import build_generation_user_prompt
from witup_llm.prompts import build_judge_system_prompt
from witup_llm.prompts import build_judge_user_prompt
from witup_llm.prompts import group_analysis_by_container
from witup_llm.project_catalog import ProjectCataloger


class PipelineService:
    def __init__(
        self,
        llm_client: HttpLLMClient | None = None,
        metric_runner: MetricRunner | None = None,
    ) -> None:
        self.llm_client = llm_client or HttpLLMClient()
        self.metric_runner = metric_runner or MetricRunner()

    def analyze(
        self,
        config: AppConfig,
        model_key: str,
        workspace: RunWorkspace | None = None,
    ) -> tuple[AnalysisReport, Path, RunWorkspace]:
        model = config.models[model_key]
        cataloger = ProjectCataloger(config.project)
        methods = cataloger.catalog()
        if config.pipeline.max_methods > 0:
            methods = methods[: config.pipeline.max_methods]
        overview = cataloger.load_overview()

        if workspace is None:
            workspace = RunWorkspace(
                config.pipeline.output_dir,
                new_run_id(f"analyze-{model_key}"),
            )
        write_json(workspace.root / "catalog.json", methods)

        analyses: list[MethodAnalysis] = []
        for index, method in enumerate(methods, start=1):
            system_prompt = build_analysis_system_prompt(config.project.language)
            user_prompt = build_analysis_user_prompt(overview, method)
            response = self.llm_client.complete_json(model, system_prompt, user_prompt)
            analysis = normalize_method_analysis(method, response.payload)
            analyses.append(analysis)
            if config.pipeline.save_prompts:
                stem = f"analysis-{index:04d}-{slugify(method.method_id)}"
                write_text(workspace.prompts / f"{stem}.txt", user_prompt)
                write_text(workspace.responses / f"{stem}.txt", response.raw_text)

        report = AnalysisReport(
            run_id=workspace.root.name,
            project_root=str(config.project.root),
            model_key=model_key,
            generated_at=utc_timestamp(),
            total_methods=len(methods),
            analyses=analyses,
        )
        analysis_path = workspace.root / "analysis.json"
        write_json(analysis_path, report)
        return report, analysis_path, workspace

    def generate(
        self,
        config: AppConfig,
        analysis_report: AnalysisReport,
        analysis_path: Path,
        model_key: str,
        workspace: RunWorkspace | None = None,
    ) -> tuple[GenerationReport, Path, RunWorkspace]:
        model = config.models[model_key]
        overview = ProjectCataloger(config.project).load_overview()

        if workspace is None:
            workspace = RunWorkspace(
                config.pipeline.output_dir,
                new_run_id(f"generate-{model_key}"),
            )

        grouped = group_analysis_by_container(analysis_report)
        strategy_parts: list[str] = []
        all_files: list[GeneratedTestFile] = []
        raw_responses: list[dict[str, object]] = []

        for index, (container_name, methods_payload) in enumerate(grouped.items(), start=1):
            system_prompt = build_generation_system_prompt(
                config.project.language,
                config.project.test_framework,
            )
            user_prompt = build_generation_user_prompt(
                overview,
                container_name,
                methods_payload,
                config.project.language,
            )
            response = self.llm_client.complete_json(model, system_prompt, user_prompt)
            strategy_summary, files = normalize_generation_response(
                response.payload,
                container_name,
                config.project.language,
            )
            strategy_parts.append(strategy_summary)
            all_files.extend(files)
            raw_responses.append(response.payload)

            if config.pipeline.save_prompts:
                stem = f"generation-{index:04d}-{slugify(container_name)}"
                write_text(workspace.prompts / f"{stem}.txt", user_prompt)
                write_text(workspace.responses / f"{stem}.txt", response.raw_text)

        for test_file in all_files:
            relative_path = safe_relative_path(test_file.relative_path)
            write_text(workspace.tests / relative_path, test_file.content)

        report = GenerationReport(
            run_id=workspace.root.name,
            source_analysis_path=str(analysis_path),
            model_key=model_key,
            generated_at=utc_timestamp(),
            strategy_summary="\n".join(part for part in strategy_parts if part).strip(),
            test_files=all_files,
            raw_responses=raw_responses,
        )
        generation_path = workspace.root / "generation.json"
        write_json(generation_path, report)
        return report, generation_path, workspace

    def evaluate(
        self,
        config: AppConfig,
        analysis_report: AnalysisReport,
        analysis_path: Path,
        generation_report: GenerationReport,
        generation_path: Path,
        judge_model_key: str | None = None,
        workspace: RunWorkspace | None = None,
    ) -> tuple[EvaluationReport, Path, RunWorkspace]:
        if workspace is None:
            workspace = RunWorkspace(
                config.pipeline.output_dir,
                new_run_id(f"evaluate-{generation_report.model_key}"),
            )

        metric_results = self.metric_runner.run_all(
            config.metrics,
            MetricRuntimeContext(
                project_root=config.project.root,
                run_dir=workspace.root,
                tests_dir=generation_path.parent / "generated-tests",
                analysis_path=analysis_path,
                generation_path=generation_path,
                model_key=generation_report.model_key,
            ),
        )
        metric_score = aggregate_metric_score(metric_results)

        judge_evaluation = None
        judge_score = None
        if judge_model_key:
            judge_model = config.models[judge_model_key]
            system_prompt = build_judge_system_prompt()
            user_prompt = build_judge_user_prompt(
                analysis_report,
                generation_report,
                metric_results,
            )
            response = self.llm_client.complete_json(judge_model, system_prompt, user_prompt)
            judge_evaluation = normalize_judge_response(response.payload)
            judge_score = judge_evaluation.score
            if config.pipeline.save_prompts:
                write_text(workspace.prompts / "judge.txt", user_prompt)
                write_text(workspace.responses / "judge.txt", response.raw_text)

        combined_score = combine_scores(metric_score, judge_score)
        report = EvaluationReport(
            run_id=workspace.root.name,
            model_key=generation_report.model_key,
            generated_at=utc_timestamp(),
            analysis_path=str(analysis_path),
            generation_path=str(generation_path),
            metric_results=metric_results,
            metric_score=metric_score,
            judge_model_key=judge_model_key,
            judge_evaluation=judge_evaluation,
            combined_score=combined_score,
        )
        evaluation_path = workspace.root / "evaluation.json"
        write_json(evaluation_path, report)
        return report, evaluation_path, workspace

    def run(
        self,
        config: AppConfig,
        analysis_model_key: str,
        generation_model_key: str,
        judge_model_key: str | None = None,
    ) -> dict[str, object]:
        workspace = RunWorkspace(
            config.pipeline.output_dir,
            new_run_id(f"run-{analysis_model_key}-{generation_model_key}"),
        )
        analysis_report, analysis_path, _ = self.analyze(
            config,
            analysis_model_key,
            workspace=workspace,
        )
        generation_report, generation_path, _ = self.generate(
            config,
            analysis_report,
            analysis_path,
            generation_model_key,
            workspace=workspace,
        )
        evaluation_report, evaluation_path, _ = self.evaluate(
            config,
            analysis_report,
            analysis_path,
            generation_report,
            generation_path,
            judge_model_key=judge_model_key,
            workspace=workspace,
        )
        return {
            "workspace": workspace.root,
            "analysis_path": analysis_path,
            "generation_path": generation_path,
            "evaluation_path": evaluation_path,
            "analysis_report": analysis_report,
            "generation_report": generation_report,
            "evaluation_report": evaluation_report,
        }

    def benchmark(
        self,
        config: AppConfig,
        scenarios: list[BenchmarkScenario],
        judge_model_key: str | None = None,
    ) -> tuple[BenchmarkReport, Path]:
        workspace = RunWorkspace(
            config.pipeline.output_dir,
            new_run_id("benchmark"),
        )
        entries: list[BenchmarkEntry] = []

        for scenario in scenarios:
            scenario_slug = slugify(
                f"{scenario.analysis_model_key}-to-{scenario.generation_model_key}"
            )
            model_workspace = RunWorkspace(workspace.root, scenario_slug)
            analysis_report, analysis_path, _ = self.analyze(
                config,
                scenario.analysis_model_key,
                workspace=model_workspace,
            )
            generation_report, generation_path, _ = self.generate(
                config,
                analysis_report,
                analysis_path,
                scenario.generation_model_key,
                workspace=model_workspace,
            )
            evaluation_report, evaluation_path, _ = self.evaluate(
                config,
                analysis_report,
                analysis_path,
                generation_report,
                generation_path,
                judge_model_key=judge_model_key,
                workspace=model_workspace,
            )
            judge_score = None
            if evaluation_report.judge_evaluation is not None:
                judge_score = evaluation_report.judge_evaluation.score
            entries.append(
                BenchmarkEntry(
                    analysis_model_key=scenario.analysis_model_key,
                    generation_model_key=scenario.generation_model_key,
                    evaluation_path=str(evaluation_path),
                    metric_score=evaluation_report.metric_score,
                    judge_score=judge_score,
                    combined_score=evaluation_report.combined_score,
                    rank=0,
                )
            )

        sorted_entries = sorted(
            entries,
            key=lambda item: score_sort_key(item.combined_score, item.metric_score, item.judge_score),
            reverse=True,
        )
        ranked_entries: list[BenchmarkEntry] = []
        markdown_rows: list[dict[str, object]] = []
        for rank, entry in enumerate(sorted_entries, start=1):
            ranked = BenchmarkEntry(
                analysis_model_key=entry.analysis_model_key,
                generation_model_key=entry.generation_model_key,
                evaluation_path=entry.evaluation_path,
                metric_score=entry.metric_score,
                judge_score=entry.judge_score,
                combined_score=entry.combined_score,
                rank=rank,
            )
            ranked_entries.append(ranked)
            markdown_rows.append(
                {
                    "rank": rank,
                    "model": (
                        f"{ranked.analysis_model_key}->{ranked.generation_model_key}"
                    ),
                    "metric_score": ranked.metric_score,
                    "judge_score": ranked.judge_score,
                    "combined_score": ranked.combined_score,
                }
            )

        report = BenchmarkReport(
            run_id=workspace.root.name,
            generated_at=utc_timestamp(),
            judge_model_key=judge_model_key,
            entries=ranked_entries,
        )
        benchmark_path = workspace.root / "benchmark.json"
        write_json(benchmark_path, report)
        write_text(workspace.root / "benchmark.md", build_benchmark_markdown(None, markdown_rows))
        return report, benchmark_path


def normalize_method_analysis(
    method: MethodDescriptor,
    payload: dict[str, object],
) -> MethodAnalysis:
    method_summary = str(payload.get("method_summary", "")).strip()
    raw_paths = payload.get("expaths", [])
    if not isinstance(raw_paths, list):
        raw_paths = []
    expaths: list[ExceptionPath] = []
    for index, item in enumerate(raw_paths, start=1):
        if not isinstance(item, dict):
            continue
        exception_type = str(item.get("exception_type", "UNKNOWN")).strip() or "UNKNOWN"
        confidence = float(item.get("confidence", 0.0))
        expaths.append(
            ExceptionPath(
                path_id=str(item.get("path_id", f"{method.method_id}:path-{index}")),
                exception_type=exception_type,
                trigger=str(item.get("trigger", "")).strip(),
                guard_conditions=[str(value) for value in item.get("guard_conditions", [])],
                confidence=max(0.0, min(1.0, confidence)),
                evidence=[str(value) for value in item.get("evidence", [])],
            )
        )
    return MethodAnalysis(
        method=method,
        method_summary=method_summary,
        expaths=expaths,
        raw_response=dict(payload),
    )


def normalize_generation_response(
    payload: dict[str, object],
    container_name: str,
    language: str,
) -> tuple[str, list[GeneratedTestFile]]:
    extension = ".java" if language == "java" else ".py"
    raw_files = payload.get("test_files", [])
    if not isinstance(raw_files, list):
        raw_files = []
    files: list[GeneratedTestFile] = []
    for index, item in enumerate(raw_files, start=1):
        if not isinstance(item, dict):
            continue
        relative_path = str(item.get("relative_path", "")).strip()
        if not relative_path:
            relative_path = f"{slugify(container_name)}-tests-{index}{extension}"
        files.append(
            GeneratedTestFile(
                relative_path=relative_path,
                content=str(item.get("content", "")),
                covered_method_ids=[str(value) for value in item.get("covered_method_ids", [])],
                notes=str(item.get("notes", "")),
            )
        )
    strategy_summary = str(payload.get("strategy_summary", "")).strip()
    return strategy_summary, files


def normalize_judge_response(payload: dict[str, object]) -> JudgeEvaluation:
    score = max(0.0, min(100.0, float(payload.get("score", 0.0))))
    return JudgeEvaluation(
        score=score,
        verdict=str(payload.get("verdict", "")),
        strengths=[str(value) for value in payload.get("strengths", [])],
        weaknesses=[str(value) for value in payload.get("weaknesses", [])],
        risks=[str(value) for value in payload.get("risks", [])],
        recommended_next_actions=[
            str(value) for value in payload.get("recommended_next_actions", [])
        ],
        raw_response=dict(payload),
    )


def combine_scores(metric_score: float | None, judge_score: float | None) -> float | None:
    if metric_score is None and judge_score is None:
        return None
    if metric_score is None:
        return judge_score
    if judge_score is None:
        return metric_score
    return (metric_score + judge_score) / 2.0


def score_sort_key(
    combined_score: float | None,
    metric_score: float | None,
    judge_score: float | None,
) -> tuple[float, float, float]:
    return (
        combined_score if combined_score is not None else -1.0,
        metric_score if metric_score is not None else -1.0,
        judge_score if judge_score is not None else -1.0,
    )


def load_analysis_report(path: str | Path) -> AnalysisReport:
    return AnalysisReport.from_dict(read_json(Path(path)))


def load_generation_report(path: str | Path) -> GenerationReport:
    return GenerationReport.from_dict(read_json(Path(path)))


def load_evaluation_report(path: str | Path) -> EvaluationReport:
    return EvaluationReport.from_dict(read_json(Path(path)))


def load_app_config(path: str | Path) -> AppConfig:
    return load_config(path)
