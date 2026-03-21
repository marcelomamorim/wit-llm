from __future__ import annotations

import tempfile
import textwrap
import unittest
from pathlib import Path
import sys


ROOT = Path(__file__).resolve().parents[1]
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from witup_llm.config import load_config


class ConfigTest(unittest.TestCase):
    def test_load_config_resolves_paths_and_models(self) -> None:
        with tempfile.TemporaryDirectory() as tmp_dir:
            root = Path(tmp_dir)
            config_path = root / "witup.toml"
            config_path.write_text(
                textwrap.dedent(
                    """
                    [project]
                    root = "."
                    language = "java"
                    include = ["src/main/java"]
                    overview_file = "README.md"

                    [pipeline]
                    output_dir = "generated"
                    save_prompts = true
                    max_methods = 10
                    judge_model = "judge"

                    [models.analysis]
                    provider = "ollama"
                    model = "qwen2.5-coder:7b"
                    base_url = "http://localhost:11434"

                    [models.judge]
                    provider = "openai_compatible"
                    model = "judge-model"
                    base_url = "https://example.test/v1"
                    api_key_env = "API_KEY"

                    [[metrics]]
                    name = "coverage"
                    kind = "coverage"
                    command = "echo 81"
                    value_regex = "(\\\\d+)"
                    weight = 2.0
                    """
                ).strip()
                + "\n",
                encoding="utf-8",
            )
            (root / "README.md").write_text("overview", encoding="utf-8")

            config = load_config(config_path)

            self.assertEqual("java", config.project.language)
            self.assertEqual(root.resolve(), config.project.root)
            self.assertEqual((root / "generated").resolve(), config.pipeline.output_dir)
            self.assertEqual("judge", config.pipeline.judge_model)
            self.assertIn("analysis", config.models)
            self.assertEqual("openai_compatible", config.models["judge"].provider)
            self.assertEqual(1, len(config.metrics))

    def test_load_config_accepts_string_include_and_exclude(self) -> None:
        with tempfile.TemporaryDirectory() as tmp_dir:
            root = Path(tmp_dir)
            config_path = root / "witup.toml"
            config_path.write_text(
                textwrap.dedent(
                    """
                    [project]
                    root = "."
                    language = "python"
                    include = "witup_llm"
                    exclude = "generated"

                    [models.analysis]
                    provider = "ollama"
                    model = "qwen2.5-coder:7b"
                    base_url = "http://localhost:11434"
                    """
                ).strip()
                + "\n",
                encoding="utf-8",
            )
            config = load_config(config_path)

            self.assertEqual(["witup_llm"], config.project.include)
            self.assertEqual(["generated"], config.project.exclude)

    def test_load_config_rejects_invalid_include_type(self) -> None:
        with tempfile.TemporaryDirectory() as tmp_dir:
            root = Path(tmp_dir)
            config_path = root / "witup.toml"
            config_path.write_text(
                textwrap.dedent(
                    """
                    [project]
                    root = "."
                    language = "python"
                    include = 10

                    [models.analysis]
                    provider = "ollama"
                    model = "qwen2.5-coder:7b"
                    base_url = "http://localhost:11434"
                    """
                ).strip()
                + "\n",
                encoding="utf-8",
            )

            with self.assertRaises(ValueError):
                load_config(config_path)


if __name__ == "__main__":
    unittest.main()
