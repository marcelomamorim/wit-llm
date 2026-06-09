.PHONY: install build test coverage fmt check-fmt vet quality clean

install:
	go mod tidy

build:
	go build -o bin/witup ./cmd/witup

test:
	go test ./cmd/... ./internal/...

coverage:
	go test ./cmd/... ./internal/... -coverprofile=coverage.out
	go tool cover -func=coverage.out

fmt:
	gofmt -w ./cmd ./internal

check-fmt:
	test -z "$$(gofmt -l ./cmd ./internal)"

vet:
	go vet ./cmd/... ./internal/...

quality: check-fmt vet test

clean:
	rm -rf bin coverage.out .gocache
