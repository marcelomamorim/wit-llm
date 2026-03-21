from __future__ import annotations

import re
import subprocess
from dataclasses import dataclass
from pathlib import Path

from witup_llm.models import MetricConfig
from witup_llm.models import MetricResult


@dataclass(slots=True)
class CommandResult:
    exit_code: int
    stdout: str
    stderr: str


@dataclass(slots=True)
class MetricRuntimeContext:
    project_root: Path
    run_dir: Path
    tests_dir: Path
    analysis_path: Path
    generation_path: Path
    model_key: str


class ShellCommandRunner:
    def run(self, command: str, cwd: Path) -> CommandResult:
        completed = subprocess.run(
            command,
            cwd=cwd,
            shell=True,
            capture_output=True,
            text=True,
            check=False,
        )
        return CommandResult(
            exit_code=completed.returncode,
            stdout=completed.stdout,
            stderr=completed.stderr,
        )


class MetricRunner:
    def __init__(self, command_runner: ShellCommandRunner | None = None) -> None:
        self.command_runner = command_runner or ShellCommandRunner()

    def run_all(
        self,
        metrics: list[MetricConfig],
        context: MetricRuntimeContext,
    ) -> list[MetricResult]:
        return [self.run_metric(metric, context) for metric in metrics]

    def run_metric(
        self,
        metric: MetricConfig,
        context: MetricRuntimeContext,
    ) -> MetricResult:
        command = metric.command.format(
            project_root=str(context.project_root),
            run_dir=str(context.run_dir),
            tests_dir=str(context.tests_dir),
            analysis_path=str(context.analysis_path),
            generation_path=str(context.generation_path),
            model_key=context.model_key,
        )
        cwd = context.project_root
        if metric.working_directory:
            cwd = (context.project_root / metric.working_directory).resolve()

        result = self.command_runner.run(command, cwd)
        numeric_value = parse_numeric_value(metric.value_regex, result.stdout, result.stderr)
        normalized_score = normalize_score(numeric_value, metric.scale)
        return MetricResult(
            name=metric.name,
            kind=metric.kind,
            command=command,
            success=result.exit_code == 0,
            exit_code=result.exit_code,
            stdout=result.stdout,
            stderr=result.stderr,
            numeric_value=numeric_value,
            normalized_score=normalized_score,
            weight=metric.weight,
            description=metric.description or "",
        )


def parse_numeric_value(value_regex: str | None, stdout: str, stderr: str) -> float | None:
    if not value_regex:
        return None
    combined = stdout + "\n" + stderr
    match = re.search(value_regex, combined, flags=re.MULTILINE)
    if match is None:
        return None
    raw_value = match.group(1).strip().rstrip("%")
    try:
        return float(raw_value)
    except ValueError:
        return None


def normalize_score(value: float | None, scale: float) -> float | None:
    if value is None or scale <= 0:
        return None
    normalized = max(0.0, min(100.0, (value / scale) * 100.0))
    return normalized


def aggregate_metric_score(results: list[MetricResult]) -> float | None:
    weighted_total = 0.0
    weight_sum = 0.0
    for result in results:
        if result.normalized_score is None:
            continue
        weighted_total += result.normalized_score * result.weight
        weight_sum += result.weight
    if weight_sum == 0:
        return None
    return weighted_total / weight_sum

