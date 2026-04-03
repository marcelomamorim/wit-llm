package armazenamento

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

func TestLerConsultaSuportaGETEPOST(t *testing.T) {
	t.Run("get", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodGet, "/api/consulta?q=SELECT+1", nil)
		consulta, err := lerConsulta(req)
		if err != nil {
			t.Fatalf("ler GET: %v", err)
		}
		if consulta != "SELECT 1" {
			t.Fatalf("consulta GET inesperada: %q", consulta)
		}
	})

	t.Run("post", func(t *testing.T) {
		req := httptest.NewRequest(http.MethodPost, "/api/consulta", strings.NewReader(`{"query":"SELECT 2"}`))
		consulta, err := lerConsulta(req)
		if err != nil {
			t.Fatalf("ler POST: %v", err)
		}
		if consulta != "SELECT 2" {
			t.Fatalf("consulta POST inesperada: %q", consulta)
		}
	})
}

func TestLerConsultaValidaMetodoECorpo(t *testing.T) {
	req := httptest.NewRequest(http.MethodPut, "/api/consulta", nil)
	if _, err := lerConsulta(req); err == nil {
		t.Fatalf("esperava erro para método HTTP inválido")
	}

	req = httptest.NewRequest(http.MethodPost, "/api/consulta", strings.NewReader(`{"invalid":true}`))
	if _, err := lerConsulta(req); err == nil {
		t.Fatalf("esperava erro para payload sem campo query")
	}
}

func TestEscreverJSONRespostaSerializaPayloadEErros(t *testing.T) {
	t.Run("sucesso", func(t *testing.T) {
		recorder := httptest.NewRecorder()
		payload := ResultadoConsulta{Colunas: []string{"valor"}, Linhas: [][]string{{"1"}}}
		escreverJSONResposta(recorder, payload, nil)
		if recorder.Code != http.StatusOK {
			t.Fatalf("esperava 200, recebi %d", recorder.Code)
		}
		var recebido ResultadoConsulta
		if err := json.Unmarshal(recorder.Body.Bytes(), &recebido); err != nil {
			t.Fatalf("unmarshal resposta: %v", err)
		}
		if len(recebido.Linhas) != 1 || recebido.Linhas[0][0] != "1" {
			t.Fatalf("payload inesperado: %#v", recebido)
		}
	})

	t.Run("erro", func(t *testing.T) {
		gr := httptest.NewRecorder()
		escreverJSONResposta(gr, nil, http.ErrBodyNotAllowed)
		if gr.Code != http.StatusBadRequest {
			t.Fatalf("esperava 400, recebi %d", gr.Code)
		}
	})
}

func TestResumirConsultaTruncaConsultasLongas(t *testing.T) {
	curta := "SELECT * FROM vw_baselines_witup"
	if got := resumirConsulta(curta); got != curta {
		t.Fatalf("resumo curto inesperado: %q", got)
	}

	longa := "SELECT " + strings.Repeat("campo, ", 40) + " FROM tabela"
	if got := resumirConsulta(longa); len(got) <= 140 || !strings.HasSuffix(got, "...") {
		t.Fatalf("esperava resumo truncado, recebi %q", got)
	}
}
