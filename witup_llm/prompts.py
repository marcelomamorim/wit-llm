from __future__ import annotations

import json
from collections import defaultdict

from witup_llm.models import AnalysisReport
from witup_llm.models import EvaluationReport
from witup_llm.models import GenerationReport
from witup_llm.models import MethodDescriptor
from witup_llm.models import MetricResult


def build_analysis_system_prompt(language: str) -> str:
    return (
        "You are a senior software testing and program analysis assistant. "
        f"Your job is to inspect a {language} method and identify exception paths "
        "that should be exercised by generated tests. Return valid JSON only."
    )


def build_analysis_user_prompt(project_overview: str, method: MethodDescriptor) -> str:
    schema = {
        "method_summary": "short explanation of the method behavior",
        "expaths": [
            {
                "path_id": "stable id for this path",
                "exception_type": "exception class or category",
                "trigger": "what causes the exception",
                "guard_conditions": ["condition 1", "condition 2"],
                "confidence": 0.0,
                "evidence": ["relevant line or code fact"],
            }
        ],
    }
    return (
        "Analyse the method below and identify exception paths that are test-worthy.\n\n"
        f"Project overview:\n{project_overview or '(none provided)'}\n\n"
        f"Method signature: {method.signature}\n"
        f"Method location: {method.file_path}:{method.start_line}-{method.end_line}\n"
        f"Method source:\n```{method.language}\n{method.source}\n```\n\n"
        "Output JSON schema:\n"
        f"{json.dumps(schema, indent=2)}\n\n"
        "Rules:\n"
        "1. Do not invent exception paths without evidence in the method source.\n"
        "2. Use confidence values between 0.0 and 1.0.\n"
        "3. Keep evidence concrete.\n"
        "4. If no exception path is visible, return an empty `expaths` list.\n"
    )


def build_generation_system_prompt(language: str, framework: str) -> str:
    return (
        "You are a senior test engineer. Generate executable unit tests based only on "
        "the supplied exception-path analysis and project context. Return valid JSON only. "
        f"Target language: {language}. Preferred test framework: {framework}."
    )


def build_generation_user_prompt(
    project_overview: str,
    container_name: str,
    methods_payload: list[dict[str, object]],
    language: str,
) -> str:
    schema = {
        "strategy_summary": "high-level summary of the testing strategy",
        "test_files": [
            {
                "relative_path": "relative file path inside the generated tests directory",
                "content": "full source code",
                "covered_method_ids": ["id-1", "id-2"],
                "notes": "brief notes about assumptions or setup",
            }
        ],
    }
    return (
        f"Generate unit tests for container `{container_name}`.\n\n"
        f"Project overview:\n{project_overview or '(none provided)'}\n\n"
        f"Target language: {language}\n"
        f"Analysed methods and exception paths:\n{json.dumps(methods_payload, indent=2)}\n\n"
        "Output JSON schema:\n"
        f"{json.dumps(schema, indent=2)}\n\n"
        "Rules:\n"
        "1. Return file paths relative to the generated tests directory.\n"
        "2. Provide complete files, not snippets.\n"
        "3. Cover positive and negative paths when evidence exists.\n"
        "4. Do not invent APIs or production behavior unsupported by the analysis.\n"
        "5. Mention assumptions in `notes` when context is missing.\n"
    )


def build_judge_system_prompt() -> str:
    return (
        "You are an impartial test-quality judge. You receive generated tests, runtime metrics, "
        "and exception-path analysis. Evaluate effectiveness and return valid JSON only."
    )


def build_judge_user_prompt(
    analysis_report: AnalysisReport,
    generation_report: GenerationReport,
    metric_results: list[MetricResult],
) -> str:
    schema = {
        "score": 0.0,
        "verdict": "short verdict",
        "strengths": ["item"],
        "weaknesses": ["item"],
        "risks": ["item"],
        "recommended_next_actions": ["item"],
    }
    metric_summary = [
        {
            "name": metric.name,
            "kind": metric.kind,
            "success": metric.success,
            "exit_code": metric.exit_code,
            "numeric_value": metric.numeric_value,
            "normalized_score": metric.normalized_score,
            "weight": metric.weight,
            "stdout_excerpt": metric.stdout[:800],
            "stderr_excerpt": metric.stderr[:800],
        }
        for metric in metric_results
    ]
    generation_summary = [
        {
            "relative_path": file.relative_path,
            "covered_method_ids": file.covered_method_ids,
            "notes": file.notes,
        }
        for file in generation_report.test_files
    ]
    analysis_summary = [
        {
            "method_id": item.method.method_id,
            "signature": item.method.signature,
            "expaths": [
                {
                    "path_id": path.path_id,
                    "exception_type": path.exception_type,
                    "trigger": path.trigger,
                    "guard_conditions": path.guard_conditions,
                    "confidence": path.confidence,
                }
                for path in item.expaths
            ],
        }
        for item in analysis_report.analyses
    ]
    return (
        "Evaluate how effective the generated tests are.\n\n"
        f"Analysis summary:\n{json.dumps(analysis_summary, indent=2)}\n\n"
        f"Generated tests summary:\n{json.dumps(generation_summary, indent=2)}\n\n"
        f"Runtime metrics:\n{json.dumps(metric_summary, indent=2)}\n\n"
        "Consider both objective metrics and qualitative adequacy. "
        "Return JSON with score between 0 and 100.\n"
        f"Output JSON schema:\n{json.dumps(schema, indent=2)}"
    )


def build_benchmark_markdown(report: EvaluationReport | None, entries: list[dict[str, object]]) -> str:
    lines = ["# Benchmark Summary", "", "| Rank | Model | Metric Score | Judge Score | Combined |", "| --- | --- | --- | --- | --- |"]
    for entry in entries:
        lines.append(
            "| {rank} | {model} | {metric} | {judge} | {combined} |".format(
                rank=entry["rank"],
                model=entry["model"],
                metric=format_score(entry.get("metric_score")),
                judge=format_score(entry.get("judge_score")),
                combined=format_score(entry.get("combined_score")),
            )
        )
    if report is not None and report.judge_evaluation is not None:
        lines.extend(["", "## Judge Notes", "", report.judge_evaluation.verdict])
    return "\n".join(lines) + "\n"


def group_analysis_by_container(analysis_report: AnalysisReport) -> dict[str, list[dict[str, object]]]:
    grouped: dict[str, list[dict[str, object]]] = defaultdict(list)
    for item in analysis_report.analyses:
        grouped[item.method.container_name].append(
            {
                "method_id": item.method.method_id,
                "signature": item.method.signature,
                "source": item.method.source,
                "summary": item.method_summary,
                "expaths": [
                    {
                        "path_id": path.path_id,
                        "exception_type": path.exception_type,
                        "trigger": path.trigger,
                        "guard_conditions": path.guard_conditions,
                        "confidence": path.confidence,
                        "evidence": path.evidence,
                    }
                    for path in item.expaths
                ],
            }
        )
    return grouped


def format_score(value: float | None) -> str:
    if value is None:
        return "-"
    return f"{value:.2f}"

