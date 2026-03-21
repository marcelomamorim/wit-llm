from __future__ import annotations

import json
from datetime import datetime
from datetime import timezone
from pathlib import Path
from typing import Any

from witup_llm.models import to_dict


def utc_timestamp() -> str:
    return datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")


def new_run_id(label: str) -> str:
    slug = slugify(label)
    return f"{datetime.now(timezone.utc).strftime('%Y%m%dT%H%M%SZ')}-{slug}"


def slugify(value: str) -> str:
    lowered = value.lower()
    cleaned = []
    for char in lowered:
        if char.isalnum():
            cleaned.append(char)
        else:
            cleaned.append("-")
    slug = "".join(cleaned).strip("-")
    while "--" in slug:
        slug = slug.replace("--", "-")
    return slug or "run"


def ensure_dir(path: Path) -> Path:
    path.mkdir(parents=True, exist_ok=True)
    return path


def write_json(path: Path, payload: Any) -> None:
    ensure_dir(path.parent)
    with path.open("w", encoding="utf-8") as handle:
        json.dump(to_dict(payload), handle, indent=2, ensure_ascii=False)
        handle.write("\n")


def read_json(path: Path) -> dict[str, Any]:
    with path.open("r", encoding="utf-8") as handle:
        return json.load(handle)


def write_text(path: Path, content: str) -> None:
    ensure_dir(path.parent)
    path.write_text(content, encoding="utf-8")


def safe_relative_path(raw_path: str) -> Path:
    path = Path(raw_path)
    if path.is_absolute():
        raise ValueError(f"Generated file path must be relative, got `{raw_path}`.")
    if ".." in path.parts:
        raise ValueError(f"Generated file path cannot escape the output dir: `{raw_path}`.")
    return path


class RunWorkspace:
    def __init__(self, output_root: Path, run_id: str) -> None:
        self.root = ensure_dir(output_root / run_id)
        self.prompts = ensure_dir(self.root / "prompts")
        self.responses = ensure_dir(self.root / "responses")
        self.tests = ensure_dir(self.root / "generated-tests")
