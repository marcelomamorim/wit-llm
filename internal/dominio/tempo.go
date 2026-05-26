package dominio

import "time"

// HorarioUTC devolve timestamps RFC3339 em UTC para uso consistente nos artefatos.
func HorarioUTC() string {
	return time.Now().UTC().Format(time.RFC3339)
}
