from __future__ import annotations

import unittest
from pathlib import Path
import sys


ROOT = Path(__file__).resolve().parents[1]
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from witup_llm.domain import build_benchmark_scenarios


class BenchmarkDomainTest(unittest.TestCase):
    def test_build_coupled_scenarios(self) -> None:
        scenarios = build_benchmark_scenarios(model_keys=["model-a", "model-a", "model-b"])
        self.assertEqual(2, len(scenarios))
        self.assertEqual("model-a", scenarios[0].analysis_model_key)
        self.assertEqual("model-a", scenarios[0].generation_model_key)
        self.assertEqual("model-b", scenarios[1].analysis_model_key)
        self.assertEqual("model-b", scenarios[1].generation_model_key)

    def test_build_matrix_scenarios(self) -> None:
        scenarios = build_benchmark_scenarios(
            analysis_model_keys=["analysis-a", "analysis-b"],
            generation_model_keys=["gen-a", "gen-b"],
        )
        pairs = [(item.analysis_model_key, item.generation_model_key) for item in scenarios]
        self.assertEqual(
            [
                ("analysis-a", "gen-a"),
                ("analysis-a", "gen-b"),
                ("analysis-b", "gen-a"),
                ("analysis-b", "gen-b"),
            ],
            pairs,
        )

    def test_rejects_mixed_modes(self) -> None:
        with self.assertRaises(ValueError):
            build_benchmark_scenarios(
                model_keys=["model-a"],
                analysis_model_keys=["analysis-a"],
                generation_model_keys=["gen-a"],
            )

    def test_requires_both_matrix_sides(self) -> None:
        with self.assertRaises(ValueError):
            build_benchmark_scenarios(analysis_model_keys=["analysis-a"])
        with self.assertRaises(ValueError):
            build_benchmark_scenarios(generation_model_keys=["gen-a"])


if __name__ == "__main__":
    unittest.main()
