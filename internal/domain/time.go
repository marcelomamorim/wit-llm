package domain

import "time"

// TimestampUTC returns deterministic RFC3339 timestamps used by artifacts.
func TimestampUTC() string {
	return time.Now().UTC().Format(time.RFC3339)
}
