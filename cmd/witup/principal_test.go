package main

import "testing"

func TestExecutarDelegatesToCLI(t *testing.T) {
	if codigo := executar([]string{"help"}); codigo != 0 {
		t.Fatalf("esperava help com código zero, recebi %d", codigo)
	}
}
