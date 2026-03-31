package metrics

import (
	"testing"

	"github.com/marceloamorim/witup-llm/internal/domain"
)

func fptr(v float64) *float64 { return &v }

func TestAggregateScore(t *testing.T) {
	results := []domain.MetricResult{
		{NormalizedScore: fptr(80), Weight: 1.0},
		{NormalizedScore: fptr(100), Weight: 3.0},
	}
	out := AggregateScore(results)
	if out == nil {
		t.Fatalf("expected aggregate score")
	}
	if *out != 95 {
		t.Fatalf("expected 95, got %f", *out)
	}
}

func TestNormalizeScore(t *testing.T) {
	value := 50.0
	n := normalizeScore(&value, 100)
	if n == nil || *n != 50 {
		t.Fatalf("expected 50, got %v", n)
	}
}

func TestCombineScores(t *testing.T) {
	metric := 80.0
	judge := 60.0
	combined := CombineScores(&metric, &judge)
	if combined == nil {
		t.Fatalf("expected combined score")
	}
	if *combined != 74 {
		t.Fatalf("expected 74, got %f", *combined)
	}
}
