package llm

import (
	"context"
	"crypto/sha256"
	"encoding/hex"
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

// OpcoesLote controla um lote de chamadas individuais (não é Batch com desconto).
type OpcoesLote struct {
	Intervalo time.Duration
	Limite    int
	DryRun    bool
	Progresso func(string)
}
type ResumoLote struct {
	Total        int `json:"total"`
	Reutilizados int `json:"reused"`
	Sucessos     int `json:"succeeded"`
	Falhas       int `json:"failed"`
}
type registroLote struct {
	LinhaResultadoBatch
	Fingerprint string                 `json:"fingerprint"`
	Provider    string                 `json:"provider"`
	Model       string                 `json:"model"`
	Config      dominio.ConfigModelo   `json:"model_config"`
	CreatedAt   string                 `json:"created_at"`
	ElapsedMS   int64                  `json:"elapsed_ms"`
	Raw         map[string]interface{} `json:"raw_provider_response,omitempty"`
}

// ExecutarLoteLocal aceita o JSONL de preparação existente e grava resultados
// consumíveis por avaliar-estudo-jdk-global. Só sucessos validados são reutilizados.
func (c *Cliente) ExecutarLoteLocal(ctx context.Context, m dominio.ConfigModelo, entrada, saida string, opts OpcoesLote) (ResumoLote, error) {
	var summary ResumoLote
	if opts.Intervalo < 0 || opts.Limite < 0 {
		return summary, fmt.Errorf("intervalo e limite devem ser >= 0")
	}
	if err := ValidarModeloDireto(m); err != nil {
		return summary, err
	}
	inAbs, _ := filepath.Abs(entrada)
	outAbs, _ := filepath.Abs(saida)
	if inAbs == outAbs {
		return summary, fmt.Errorf("entrada e saída devem ser diferentes")
	}
	if a, e := os.Stat(entrada); e == nil {
		if b, e := os.Stat(saida); e == nil && os.SameFile(a, b) {
			return summary, fmt.Errorf("entrada e saída apontam para o mesmo arquivo")
		}
	}
	data, err := os.ReadFile(entrada)
	if err != nil {
		return summary, err
	}
	var linhas []LinhaRequisicaoBatch
	prompts := map[string][2]string{}
	seen := map[string]bool{}
	for i, line := range strings.Split(string(data), "\n") {
		if strings.TrimSpace(line) == "" {
			continue
		}
		var r LinhaRequisicaoBatch
		if err = json.Unmarshal([]byte(line), &r); err != nil {
			return summary, fmt.Errorf("linha %d: %w", i+1, err)
		}
		if r.CustomID == "" || seen[r.CustomID] {
			return summary, fmt.Errorf("custom_id vazio ou repetido na linha %d", i+1)
		}
		seen[r.CustomID] = true
		if r.Method != "POST" || r.Body["model"] != m.Modelo {
			return summary, fmt.Errorf("linha %d: método/modelo não corresponde à configuração; prepare novamente", i+1)
		}
		sistema, usuario, e := promptsDaLinha(r)
		if e != nil {
			return summary, fmt.Errorf("linha %d: %w", i+1, e)
		}
		prompts[r.CustomID] = [2]string{sistema, usuario}
		linhas = append(linhas, r)
	}
	summary.Total = len(linhas)
	if len(linhas) == 0 {
		return summary, fmt.Errorf("lote vazio")
	}
	// O hash vincula conteúdo, ordem e configuração: não mistura modelos/rodadas.
	cfg, _ := json.Marshal(m)
	sum := sha256.Sum256(append(append([]byte{}, data...), cfg...))
	fingerprint := hex.EncodeToString(sum[:])

	if !opts.DryRun {
		if strings.TrimSpace(os.Getenv(m.VariavelAmbienteChaveAPI)) == "" {
			return summary, fmt.Errorf("variável %s obrigatória", m.VariavelAmbienteChaveAPI)
		}
		if err = os.MkdirAll(filepath.Dir(saida), 0755); err != nil {
			return summary, err
		}
		lock := saida + ".lock"
		if err = os.Mkdir(lock, 0700); err != nil {
			return summary, fmt.Errorf("lote bloqueado (%s); verifique se há outra execução: %w", lock, err)
		}
		defer os.Remove(lock)
	}
	completed := map[string]bool{}
	if f, e := os.Open(saida); e == nil {
		dec := json.NewDecoder(f)
		for {
			var r registroLote
			e = dec.Decode(&r)
			if e == io.EOF {
				break
			}
			if e != nil {
				f.Close()
				return summary, fmt.Errorf("saída incompleta ou inválida; recupere o último registro antes de retomar: %w", e)
			}
			if r.Fingerprint != fingerprint || !seen[r.CustomID] {
				f.Close()
				return summary, fmt.Errorf("saída pertence a outra configuração/entrada")
			}
			if r.Error == nil && r.Response != nil {
				if _, e := ExtrairRespostaBatch(m, r.LinhaResultadoBatch); e == nil {
					completed[r.CustomID] = true
				}
			}
		}
		f.Close()
	} else if !os.IsNotExist(e) {
		return summary, e
	}
	for _, r := range linhas {
		if completed[r.CustomID] {
			summary.Reutilizados++
		}
	}
	if opts.DryRun {
		return summary, nil
	}
	f, err := os.OpenFile(saida, os.O_CREATE|os.O_APPEND|os.O_WRONLY, 0600)
	if err != nil {
		return summary, err
	}
	defer f.Close()
	enc := json.NewEncoder(f)
	attempted := 0
	interval := opts.Intervalo
	if minimum := time.Duration(m.IntervaloRequisicoesMS) * time.Millisecond; minimum > interval {
		interval = minimum
	}
	for _, r := range linhas {
		if completed[r.CustomID] {
			continue
		}
		if opts.Limite > 0 && attempted >= opts.Limite {
			break
		}
		if attempted > 0 && interval > 0 {
			timer := time.NewTimer(interval)
			select {
			case <-ctx.Done():
				timer.Stop()
				return summary, ctx.Err()
			case <-timer.C:
			}
		}
		if err = ctx.Err(); err != nil {
			return summary, err
		}
		attempted++
		p := prompts[r.CustomID]
		start := time.Now()
		record := registroLote{LinhaResultadoBatch: LinhaResultadoBatch{CustomID: r.CustomID}, Fingerprint: fingerprint, Provider: m.Provedor, Model: m.Modelo, Config: m, CreatedAt: time.Now().UTC().Format(time.RFC3339Nano)}
		var raw map[string]interface{}
		if m.Provedor == "openai" || m.Provedor == "openai_compatible" {
			h, e := openAIHeaders(m)
			if e != nil {
				return summary, e
			}
			raw, err = c.requestJSONContext(ctx, "POST", strings.TrimRight(m.URLBase, "/")+"/responses", construirCorpoResponses(m, p[0], p[1], dominio.OpcoesRequisicaoLLM{}), m, h)
		} else {
			raw, err = c.requisitarProvedorContext(ctx, m, p[0], p[1], dominio.OpcoesRequisicaoLLM{})
		}
		record.Raw = raw
		record.ElapsedMS = time.Since(start).Milliseconds()
		if err == nil {
			var normalized map[string]interface{}
			normalized, err = normalizarRespostaProvedor(m, raw)
			if err == nil {
				record.Response = &struct {
					StatusCode int                    `json:"status_code"`
					RequestID  string                 `json:"request_id"`
					Body       map[string]interface{} `json:"body"`
				}{StatusCode: 200, Body: normalized}
				_, err = ExtrairRespostaBatch(m, record.LinhaResultadoBatch)
			}
		}
		if err != nil {
			record.Response = nil
			record.Error = map[string]string{"message": err.Error()}
			summary.Falhas++
		} else {
			summary.Sucessos++
		}
		if e := enc.Encode(record); e != nil {
			return summary, e
		}
		if e := f.Sync(); e != nil {
			return summary, e
		}
		var httpErr *ErroHTTP
		if errors.As(err, &httpErr) {
			switch httpErr.StatusCode {
			case 401, 402, 403, 404, 429:
				return summary, fmt.Errorf("lote interrompido após HTTP %d; corrija credenciais, modelo ou quota antes de retomar: %w", httpErr.StatusCode, err)
			}
		}
		if opts.Progresso != nil {
			opts.Progresso(fmt.Sprintf("%s: sucessos=%d falhas=%d reutilizados=%d", r.CustomID, summary.Sucessos, summary.Falhas, summary.Reutilizados))
		}
	}
	if summary.Falhas > 0 {
		return summary, fmt.Errorf("%d requisições falharam; respostas originais preservadas, repita o comando para tentar novamente", summary.Falhas)
	}
	return summary, nil
}

func promptsDaLinha(r LinhaRequisicaoBatch) (string, string, error) {
	if r.Body["previous_response_id"] != nil {
		return "", "", fmt.Errorf("lote exige chamadas independentes")
	}
	inputs, ok := r.Body["input"].([]interface{})
	if !ok || len(inputs) != 2 {
		return "", "", fmt.Errorf("input deve conter exatamente system e user")
	}
	var texts [2]string
	for i, x := range inputs {
		msg, ok := x.(map[string]interface{})
		if !ok {
			return "", "", fmt.Errorf("mensagem inválida")
		}
		role := "system"
		if i == 1 {
			role = "user"
		}
		if msg["role"] != role {
			return "", "", fmt.Errorf("role esperado: %s", role)
		}
		parts, ok := msg["content"].([]interface{})
		if !ok || len(parts) != 1 {
			return "", "", fmt.Errorf("esperado um input_text")
		}
		part, ok := parts[0].(map[string]interface{})
		if !ok || part["type"] != "input_text" {
			return "", "", fmt.Errorf("conteúdo não textual")
		}
		texts[i], ok = part["text"].(string)
		if !ok || texts[i] == "" {
			return "", "", fmt.Errorf("prompt vazio")
		}
	}
	return texts[0], texts[1], nil
}

// ValidarModeloDireto também é usado no dry-run, sem exigir credenciais.
func ValidarModeloDireto(m dominio.ConfigModelo) error {
	switch m.Provedor {
	case "openai", "openai_compatible", "gemini", "anthropic":
	default:
		return fmt.Errorf("provedor não suportado pelo lote: %s", m.Provedor)
	}
	if m.Modelo == "" || m.URLBase == "" || m.VariavelAmbienteChaveAPI == "" {
		return fmt.Errorf("model, base_url e api_key_env são obrigatórios")
	}
	if m.SegundosTimeout <= 0 || m.MaximoTentativas < 0 || m.MaximoTokensSaida <= 0 || m.IntervaloRequisicoesMS < 0 || m.Temperature < 0 {
		return fmt.Errorf("timeout/max_output_tokens devem ser positivos; retries/intervalo/temperature não negativos")
	}
	effort := m.EsforcoRaciocinio
	allowed := map[string]bool{"": true, "low": true, "medium": true, "high": true}
	switch m.Provedor {
	case "gemini":
		allowed["minimal"] = true
	case "anthropic":
		allowed["none"] = true
		allowed["xhigh"] = true
		allowed["max"] = true
	default:
		allowed["none"] = true
		allowed["xhigh"] = true
		allowed["max"] = true
	}
	if !allowed[effort] {
		return fmt.Errorf("reasoning_effort=%q inválido para %s", effort, m.Provedor)
	}
	if strings.HasPrefix(m.Modelo, "gpt-4.1") && effort != "" {
		return fmt.Errorf("GPT-4.1 não aceita reasoning_effort; omita o campo")
	}
	if strings.HasPrefix(m.Modelo, "gpt-6.1-sol") && (effort == "none" || effort == "") {
		return fmt.Errorf("GPT-6.1 Sol exige reasoning_effort explícito: low, medium, high, xhigh ou max")
	}
	return nil
}
