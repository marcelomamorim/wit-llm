from __future__ import annotations

import tomllib
from pathlib import Path

from witup_llm.models import AppConfig
from witup_llm.models import MetricConfig
from witup_llm.models import ModelConfig
from witup_llm.models import PipelineConfig
from witup_llm.models import ProjectConfig


SUPPORTED_LANGUAGES = {"java", "python"}
DEFAULT_EXCLUDE = [
    ".git",
    "target",
    "build",
    ".venv",
    "__pycache__",
    ".mypy_cache",
    ".pytest_cache",
    "generated",
    "tests",
]


def load_config(path: str | Path) -> AppConfig:
    config_path = Path(path).expanduser().resolve()
    with config_path.open("rb") as handle:
        payload = tomllib.load(handle)

    project_payload = payload.get("project", {})
    pipeline_payload = payload.get("pipeline", {})
    models_payload = payload.get("models", {})
    metrics_payload = payload.get("metrics", [])

    language = str(project_payload.get("language", "java")).strip().lower()
    if language not in SUPPORTED_LANGUAGES:
        raise ValueError(f"Unsupported project language `{language}`.")

    project_root_raw = project_payload.get("root", ".")
    project_root = resolve_path(config_path, project_root_raw)
    include = coerce_string_list(
        project_payload.get("include", default_include(language)),
        field_name="project.include",
    )
    exclude = coerce_string_list(
        project_payload.get("exclude", DEFAULT_EXCLUDE),
        field_name="project.exclude",
    )
    overview_file_raw = project_payload.get("overview_file")
    overview_file = None
    if overview_file_raw:
        overview_file = resolve_path(config_path, overview_file_raw)

    output_dir_raw = pipeline_payload.get("output_dir", "generated")
    output_dir = resolve_path(config_path, output_dir_raw)

    project = ProjectConfig(
        root=project_root,
        language=language,
        include=[str(item) for item in include],
        exclude=[str(item) for item in exclude],
        overview_file=overview_file,
        test_framework=str(project_payload.get("test_framework", "infer")),
    )
    pipeline = PipelineConfig(
        output_dir=output_dir,
        save_prompts=bool(pipeline_payload.get("save_prompts", True)),
        max_methods=int(pipeline_payload.get("max_methods", 0)),
        judge_model=pipeline_payload.get("judge_model"),
    )

    models: dict[str, ModelConfig] = {}
    for key, model_payload in models_payload.items():
        models[key] = ModelConfig(
            key=key,
            provider=str(model_payload["provider"]),
            model=str(model_payload["model"]),
            base_url=str(model_payload["base_url"]),
            api_key_env=model_payload.get("api_key_env"),
            temperature=float(model_payload.get("temperature", 0.1)),
            timeout_seconds=int(model_payload.get("timeout_seconds", 180)),
        )
    if not models:
        raise ValueError("Config must declare at least one model in [models].")

    metrics = [
        MetricConfig(
            name=str(item["name"]),
            kind=str(item.get("kind", item["name"])),
            command=str(item["command"]),
            weight=float(item.get("weight", 1.0)),
            value_regex=item.get("value_regex"),
            scale=float(item.get("scale", 100.0)),
            working_directory=item.get("working_directory"),
            description=str(item.get("description", "")),
        )
        for item in metrics_payload
    ]

    return AppConfig(
        config_path=config_path,
        project=project,
        pipeline=pipeline,
        models=models,
        metrics=metrics,
    )


def default_include(language: str) -> list[str]:
    if language == "python":
        return ["src", "."]
    return ["src/main/java", "."]


def resolve_path(config_path: Path, raw_path: str) -> Path:
    path = Path(raw_path).expanduser()
    if path.is_absolute():
        return path.resolve()
    return (config_path.parent / path).resolve()


def coerce_string_list(raw_value: object, field_name: str) -> list[str]:
    if isinstance(raw_value, str):
        value = raw_value.strip()
        return [value] if value else []
    if isinstance(raw_value, list):
        coerced: list[str] = []
        for item in raw_value:
            if isinstance(item, str):
                value = item.strip()
            else:
                value = str(item).strip()
            if value:
                coerced.append(value)
        return coerced
    raise ValueError(f"`{field_name}` must be a string or list of strings.")
