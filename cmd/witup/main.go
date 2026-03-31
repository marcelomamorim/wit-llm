package main

import (
	"os"

	"github.com/marceloamorim/witup-llm/internal/pipeline"
)

func main() {
	os.Exit(pipeline.Main(os.Args[1:]))
}
