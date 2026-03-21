from __future__ import annotations

from dataclasses import asdict
from dataclasses import dataclass
from dataclasses import field
from pathlib import Path
from typing import Any


def to_dict(value: Any) -> Any:
    if hasattr(value, "__dataclass_fields__"):
        return {key: to_dict(item) for key, item in asdict(value).items()}
    if isinstance(value, Path):
        return str(value)
    if isinstance(value, dict):
        return {key: to_dict(item) for key, item in value.items()}
    if isinstance(value, list):
        return [to_dict(item) for item in value]
    return value


@dataclass(slots=True)
class ProjectConfig:
    root: Path
    language: str
    include: list[str]
    exclude: list[str]
    overview_file: Path | None = None
    test_framework: str = "infer"


@dataclass(slots=True)
class PipelineConfig:
    output_dir: Path
    save_prompts: bool = True
    max_methods: int = 0
    judge_model: str | None = None


@dataclass(slots=True)
class ModelConfig:
    key: str
    provider: str
    model: str
    base_url: str
    api_key_env: str | None = None
    temperature: float = 0.1
    timeout_seconds: int = 180


@dataclass(slots=True)
class MetricConfig:
    name: str
    kind: str
    command: str
    weight: float = 1.0
    value_regex: str | None = None
    scale: float = 100.0
    working_directory: str | None = None
    description: str | None = None


@dataclass(slots=True)
class AppConfig:
    config_path: Path
    project: ProjectConfig
    pipeline: PipelineConfig
    models: dict[str, ModelConfig]
    metrics: list[MetricConfig]


@dataclass(slots=True)
class MethodDescriptor:
    method_id: str
    file_path: str
    language: str
    container_name: str
    method_name: str
    signature: str
    start_line: int
    end_line: int
    source: str


@dataclass(slots=True)
class ExceptionPath:
    path_id: str
    exception_type: str
    trigger: str
    guard_conditions: list[str]
    confidence: float
    evidence: list[str]

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "ExceptionPath":
        return cls(
            path_id=str(payload.get("path_id", "")),
            exception_type=str(payload.get("exception_type", "UNKNOWN")),
            trigger=str(payload.get("trigger", "")),
            guard_conditions=[str(item) for item in payload.get("guard_conditions", [])],
            confidence=float(payload.get("confidence", 0.0)),
            evidence=[str(item) for item in payload.get("evidence", [])],
        )


@dataclass(slots=True)
class MethodAnalysis:
    method: MethodDescriptor
    method_summary: str
    expaths: list[ExceptionPath]
    raw_response: dict[str, Any]

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "MethodAnalysis":
        method_payload = payload["method"]
        method = MethodDescriptor(
            method_id=str(method_payload["method_id"]),
            file_path=str(method_payload["file_path"]),
            language=str(method_payload["language"]),
            container_name=str(method_payload["container_name"]),
            method_name=str(method_payload["method_name"]),
            signature=str(method_payload["signature"]),
            start_line=int(method_payload["start_line"]),
            end_line=int(method_payload["end_line"]),
            source=str(method_payload["source"]),
        )
        expaths = [ExceptionPath.from_dict(item) for item in payload.get("expaths", [])]
        return cls(
            method=method,
            method_summary=str(payload.get("method_summary", "")),
            expaths=expaths,
            raw_response=dict(payload.get("raw_response", {})),
        )


@dataclass(slots=True)
class AnalysisReport:
    run_id: str
    project_root: str
    model_key: str
    generated_at: str
    total_methods: int
    analyses: list[MethodAnalysis]

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "AnalysisReport":
        return cls(
            run_id=str(payload["run_id"]),
            project_root=str(payload["project_root"]),
            model_key=str(payload["model_key"]),
            generated_at=str(payload["generated_at"]),
            total_methods=int(payload["total_methods"]),
            analyses=[MethodAnalysis.from_dict(item) for item in payload.get("analyses", [])],
        )


@dataclass(slots=True)
class GeneratedTestFile:
    relative_path: str
    content: str
    covered_method_ids: list[str]
    notes: str

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "GeneratedTestFile":
        return cls(
            relative_path=str(payload["relative_path"]),
            content=str(payload["content"]),
            covered_method_ids=[str(item) for item in payload.get("covered_method_ids", [])],
            notes=str(payload.get("notes", "")),
        )


@dataclass(slots=True)
class GenerationReport:
    run_id: str
    source_analysis_path: str
    model_key: str
    generated_at: str
    strategy_summary: str
    test_files: list[GeneratedTestFile]
    raw_responses: list[dict[str, Any]]

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "GenerationReport":
        return cls(
            run_id=str(payload["run_id"]),
            source_analysis_path=str(payload["source_analysis_path"]),
            model_key=str(payload["model_key"]),
            generated_at=str(payload["generated_at"]),
            strategy_summary=str(payload.get("strategy_summary", "")),
            test_files=[GeneratedTestFile.from_dict(item) for item in payload.get("test_files", [])],
            raw_responses=[dict(item) for item in payload.get("raw_responses", [])],
        )


@dataclass(slots=True)
class MetricResult:
    name: str
    kind: str
    command: str
    success: bool
    exit_code: int
    stdout: str
    stderr: str
    numeric_value: float | None
    normalized_score: float | None
    weight: float
    description: str

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "MetricResult":
        numeric_value = payload.get("numeric_value")
        normalized_score = payload.get("normalized_score")
        return cls(
            name=str(payload["name"]),
            kind=str(payload["kind"]),
            command=str(payload["command"]),
            success=bool(payload["success"]),
            exit_code=int(payload["exit_code"]),
            stdout=str(payload.get("stdout", "")),
            stderr=str(payload.get("stderr", "")),
            numeric_value=None if numeric_value is None else float(numeric_value),
            normalized_score=None if normalized_score is None else float(normalized_score),
            weight=float(payload.get("weight", 1.0)),
            description=str(payload.get("description", "")),
        )


@dataclass(slots=True)
class JudgeEvaluation:
    score: float
    verdict: str
    strengths: list[str]
    weaknesses: list[str]
    risks: list[str]
    recommended_next_actions: list[str]
    raw_response: dict[str, Any]

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "JudgeEvaluation":
        return cls(
            score=float(payload.get("score", 0.0)),
            verdict=str(payload.get("verdict", "")),
            strengths=[str(item) for item in payload.get("strengths", [])],
            weaknesses=[str(item) for item in payload.get("weaknesses", [])],
            risks=[str(item) for item in payload.get("risks", [])],
            recommended_next_actions=[
                str(item) for item in payload.get("recommended_next_actions", [])
            ],
            raw_response=dict(payload.get("raw_response", {})),
        )


@dataclass(slots=True)
class EvaluationReport:
    run_id: str
    model_key: str
    generated_at: str
    analysis_path: str
    generation_path: str
    metric_results: list[MetricResult]
    metric_score: float | None
    judge_model_key: str | None
    judge_evaluation: JudgeEvaluation | None
    combined_score: float | None

    @classmethod
    def from_dict(cls, payload: dict[str, Any]) -> "EvaluationReport":
        judge_payload = payload.get("judge_evaluation")
        return cls(
            run_id=str(payload["run_id"]),
            model_key=str(payload["model_key"]),
            generated_at=str(payload["generated_at"]),
            analysis_path=str(payload["analysis_path"]),
            generation_path=str(payload["generation_path"]),
            metric_results=[MetricResult.from_dict(item) for item in payload.get("metric_results", [])],
            metric_score=None if payload.get("metric_score") is None else float(payload["metric_score"]),
            judge_model_key=payload.get("judge_model_key"),
            judge_evaluation=None
            if judge_payload is None
            else JudgeEvaluation.from_dict(dict(judge_payload)),
            combined_score=None
            if payload.get("combined_score") is None
            else float(payload["combined_score"]),
        )


@dataclass(slots=True)
class BenchmarkEntry:
    analysis_model_key: str
    generation_model_key: str
    evaluation_path: str
    metric_score: float | None
    judge_score: float | None
    combined_score: float | None
    rank: int


@dataclass(slots=True)
class BenchmarkReport:
    run_id: str
    generated_at: str
    judge_model_key: str | None
    entries: list[BenchmarkEntry] = field(default_factory=list)

