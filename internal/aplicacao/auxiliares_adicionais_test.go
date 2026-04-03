package aplicacao

import (
	"os"
	"path/filepath"
	"strings"
	"testing"

	"github.com/marceloamorim/witup-llm/internal/artefatos"
	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func ponteiroFloatAux(v float64) *float64 { return &v }

func TestSelecionarMetodosRefinoCombinaMaybeDivergenciaEInterprocedural(t *testing.T) {
	analiseMaybe := dominio.AnaliseMetodo{
		Metodo:          dominio.DescritorMetodo{IDMetodo: "m1", CaminhoArquivo: "src/A.java", NomeMetodo: "a", LinhaInicial: 10},
		CaminhosExcecao: []dominio.CaminhoExcecao{{Metadados: map[string]interface{}{"maybe": true}}},
	}
	analiseInter := dominio.AnaliseMetodo{
		Metodo:          dominio.DescritorMetodo{IDMetodo: "m2", CaminhoArquivo: "src/B.java", NomeMetodo: "b", LinhaInicial: 20},
		CaminhosExcecao: []dominio.CaminhoExcecao{{Metadados: map[string]interface{}{"call_sequence": []interface{}{"callee"}}}},
	}
	witup := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{analiseMaybe, analiseInter}}
	direto := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{analiseMaybe, analiseInter}}
	comparacao := dominio.RelatorioComparacaoFontes{Metodos: []dominio.ComparacaoMetodo{{
		Unidade:                dominio.UnidadeComparacao{CaminhoArquivo: "src/B.java", NomeMetodo: "b", LinhaInicial: 20},
		QuantidadeExpathsWITUP: 1,
		QuantidadeExpathsLLM:   0,
		IDsExpathsApenasWITUP:  []string{"wit-1"},
	}}}

	refino := selecionarMetodosRefino(witup, direto, comparacao, 1)
	if len(refino) != 2 {
		t.Fatalf("esperava dois métodos para refino, recebi %d", len(refino))
	}
	if got := strings.Join(refino[0].Motivos, ","); !strings.Contains(got, "witup_maybe") {
		t.Fatalf("esperava motivo maybe no primeiro método: %v", refino[0].Motivos)
	}
	if got := strings.Join(refino[1].Motivos, ","); !strings.Contains(got, "divergence") || !strings.Contains(got, "interprocedural") {
		t.Fatalf("esperava motivos de divergência e interprocedural: %v", refino[1].Motivos)
	}
}

func TestMesclarAnalisesDiretoEMultiagenteSubstituiAnalisesRefinadas(t *testing.T) {
	base := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo:       dominio.DescritorMetodo{CaminhoArquivo: "src/A.java", NomeMetodo: "run", LinhaInicial: 10},
		ResumoMetodo: "direto",
	}}}
	refinado := dominio.RelatorioAnalise{Analises: []dominio.AnaliseMetodo{{
		Metodo:       dominio.DescritorMetodo{CaminhoArquivo: "src/A.java", NomeMetodo: "run", LinhaInicial: 10},
		ResumoMetodo: "refinado",
	}}}

	mesclado := mesclarAnalisesDiretoEMultiagente(base, refinado)
	if mesclado.Analises[0].ResumoMetodo != "refinado" {
		t.Fatalf("esperava análise refinada sobrescrevendo a direta, recebi %q", mesclado.Analises[0].ResumoMetodo)
	}
}

func TestAuxiliaresDeFluxoLLMEGeracao(t *testing.T) {
	cfg := &dominio.ConfigAplicacao{Projeto: dominio.ConfigProjeto{Raiz: "/tmp/projeto"}}
	if identificarProjeto(cfg) != "projeto" {
		t.Fatalf("identificação de projeto inesperada")
	}
	if modoFluxoLLM(cfg) != dominio.ModoLLMMultiagente {
		t.Fatalf("modo padrão deveria ser multiagente")
	}
	cfg.Fluxo.ModoLLM = string(dominio.ModoLLMDireto)
	if modoFluxoLLM(cfg) != dominio.ModoLLMDireto {
		t.Fatalf("modo configurado deveria ser direto")
	}
	if chave1, chave2 := construirPromptCacheKey("a", "b"), construirPromptCacheKey("a", "b"); chave1 != chave2 || !strings.HasPrefix(chave1, "witup-llm:") {
		t.Fatalf("prompt cache key deveria ser determinística: %q %q", chave1, chave2)
	}

	analises := []dominio.AnaliseMetodo{{CaminhosExcecao: make([]dominio.CaminhoExcecao, 10)}, {CaminhosExcecao: make([]dominio.CaminhoExcecao, 10)}}
	lotes := dividirAnalisesParaGeracao(analises)
	if len(lotes) != 2 {
		t.Fatalf("esperava divisão em dois lotes pelo limite de caminhos, recebi %d", len(lotes))
	}
	if got := reduzirVisaoGeralParaGeracao(strings.Repeat("a", limiteCaracteresVisaoGeralGeracao+10)); !strings.Contains(got, "[truncado]") {
		t.Fatalf("visão geral deveria ser truncada")
	}
}

func TestAuxiliaresDeArquivosEFormatacao(t *testing.T) {
	arquivos := []dominio.ArquivoTesteGerado{{CaminhoRelativo: "A.java", Conteudo: "1"}, {CaminhoRelativo: "A.java", Conteudo: "2"}, {CaminhoRelativo: "B.java", Conteudo: "3"}}
	consolidados := consolidarArquivosGerados(arquivos)
	if len(consolidados) != 2 || consolidados[0].CaminhoRelativo != "A.java" || consolidados[0].Conteudo != "2" {
		t.Fatalf("consolidação inesperada: %#v", consolidados)
	}
	if got := paraListaStrings([]interface{}{"a", "", nil, "b"}); len(got) != 2 {
		t.Fatalf("lista de strings inesperada: %#v", got)
	}
	if got := converterParaFloat("12.5", 0); got != 12.5 {
		t.Fatalf("float convertido inesperado: %.2f", got)
	}
	if got := fallbackIDCaminho("", "m1", 2); got != "m1:2" {
		t.Fatalf("fallback de id inesperado: %q", got)
	}
	if got := chaveOrdenacaoNota(nil, nil, nil); got != -1 {
		t.Fatalf("chave de ordenação vazia inesperada: %.2f", got)
	}
	markdown := construirMarkdownBenchmark([]dominio.EntradaBenchmark{{Posicao: 1, ChaveModeloAnalise: "a", ChaveModeloGeracao: "g", NotaCombinada: ponteiroFloatAux(90)}})
	if !strings.Contains(markdown, "| 1 | a->g |") {
		t.Fatalf("markdown de benchmark inesperado: %q", markdown)
	}
}

func TestGarantirCaminhosExistemEPrepararEspaco(t *testing.T) {
	arquivo := filepath.Join(t.TempDir(), "analysis.json")
	if err := os.WriteFile(arquivo, []byte("{}"), 0o644); err != nil {
		t.Fatalf("write file: %v", err)
	}
	if err := GarantirCaminhosExistem(arquivo); err != nil {
		t.Fatalf("caminho válido deveria passar: %v", err)
	}
	if err := GarantirCaminhosExistem(filepath.Dir(arquivo)); err == nil {
		t.Fatalf("diretório não deveria ser aceito como arquivo obrigatório")
	}
	espaco, err := prepararEspacoTrabalho(nil, filepath.Join(t.TempDir(), "generated"), "run")
	if err != nil {
		t.Fatalf("preparar espaco: %v", err)
	}
	if err := persistirCatalogo(espaco, []dominio.DescritorMetodo{{IDMetodo: "m1"}}); err != nil {
		t.Fatalf("persistir catálogo: %v", err)
	}
	if err := persistirPromptEResposta(espaco, "step-1", "prompt", "resposta"); err != nil {
		t.Fatalf("persistir prompt/resposta: %v", err)
	}
	var catalogoPersistido []dominio.DescritorMetodo
	if err := artefatos.LerJSON(filepath.Join(espaco.Raiz, "catalogo.json"), &catalogoPersistido); err != nil {
		t.Fatalf("catálogo persistido deveria ser legível: %v", err)
	}
}
