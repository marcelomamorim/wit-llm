from __future__ import annotations

import tempfile
import textwrap
import unittest
from pathlib import Path
import sys


ROOT = Path(__file__).resolve().parents[1]
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from witup_llm.models import ProjectConfig
from witup_llm.project_catalog import ProjectCataloger


JAVA_SOURCE = """
package com.example.demo;

public class Calculator {
  public int divide(int left, int right) {
    if (right == 0) {
      throw new IllegalArgumentException("zero");
    }
    return left / right;
  }

  public int abs(int value) {
    if (value < 0) {
      return -value;
    }
    return value;
  }
}
"""

PYTHON_SOURCE = """
def top_level(value: int) -> int:
    def helper(delta: int) -> int:
        return value + delta
    return helper(1)


class AccountService:
    def transfer(self, amount: int) -> None:
        def validate() -> None:
            if amount <= 0:
                raise ValueError("amount")
        validate()
"""


class ProjectCatalogTest(unittest.TestCase):
    def test_java_catalog_extracts_methods(self) -> None:
        with tempfile.TemporaryDirectory() as tmp_dir:
            root = Path(tmp_dir)
            source_path = root / "src" / "main" / "java" / "com" / "example" / "demo" / "Calculator.java"
            source_path.parent.mkdir(parents=True, exist_ok=True)
            source_path.write_text(textwrap.dedent(JAVA_SOURCE).strip() + "\n", encoding="utf-8")

            cataloger = ProjectCataloger(
                ProjectConfig(
                    root=root,
                    language="java",
                    include=["src/main/java"],
                    exclude=["target"],
                    overview_file=None,
                )
            )

            methods = cataloger.catalog()

            self.assertEqual(2, len(methods))
            self.assertEqual("divide", methods[0].method_name)
            self.assertEqual("com.example.demo.Calculator", methods[0].container_name)
            self.assertIn("throw new IllegalArgumentException", methods[0].source)
            self.assertEqual("abs", methods[1].method_name)

    def test_python_catalog_ignores_nested_local_functions(self) -> None:
        with tempfile.TemporaryDirectory() as tmp_dir:
            root = Path(tmp_dir)
            source_path = root / "service.py"
            source_path.write_text(textwrap.dedent(PYTHON_SOURCE).strip() + "\n", encoding="utf-8")

            cataloger = ProjectCataloger(
                ProjectConfig(
                    root=root,
                    language="python",
                    include=["service.py"],
                    exclude=[],
                    overview_file=None,
                )
            )

            methods = cataloger.catalog()

            names = [item.method_name for item in methods]
            self.assertEqual(["top_level", "transfer"], names)


if __name__ == "__main__":
    unittest.main()
