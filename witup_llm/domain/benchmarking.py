from __future__ import annotations

from dataclasses import dataclass


@dataclass(frozen=True, slots=True)
class BenchmarkScenario:
    analysis_model_key: str
    generation_model_key: str


def build_benchmark_scenarios(
    model_keys: list[str] | None = None,
    analysis_model_keys: list[str] | None = None,
    generation_model_keys: list[str] | None = None,
) -> list[BenchmarkScenario]:
    coupled_keys = unique_non_empty(model_keys or [])
    analysis_keys = unique_non_empty(analysis_model_keys or [])
    generation_keys = unique_non_empty(generation_model_keys or [])

    if coupled_keys and (analysis_keys or generation_keys):
        raise ValueError(
            "Use either --model or (--analysis-model with --generation-model), not both."
        )

    if coupled_keys:
        return [BenchmarkScenario(key, key) for key in coupled_keys]

    if not analysis_keys and not generation_keys:
        raise ValueError(
            "Benchmark requires at least one --model or both --analysis-model and --generation-model."
        )
    if not analysis_keys:
        raise ValueError("Missing --analysis-model for benchmark matrix.")
    if not generation_keys:
        raise ValueError("Missing --generation-model for benchmark matrix.")

    scenarios: list[BenchmarkScenario] = []
    for analysis_key in analysis_keys:
        for generation_key in generation_keys:
            scenarios.append(
                BenchmarkScenario(
                    analysis_model_key=analysis_key,
                    generation_model_key=generation_key,
                )
            )
    return scenarios


def unique_non_empty(values: list[str]) -> list[str]:
    normalized: list[str] = []
    seen: set[str] = set()
    for raw in values:
        value = str(raw).strip()
        if not value or value in seen:
            continue
        normalized.append(value)
        seen.add(value)
    return normalized
