package armazenamento

import (
	"strings"
	"testing"
)

func TestFormatadoresESanitizadoresDuckDB(t *testing.T) {
	if got := normalizarConsultaSomenteLeitura("```sql\nSELECT 1;\n```"); got != "SELECT 1;" {
		t.Fatalf("consulta normalizada inesperada: %q", got)
	}
	if got := removerFenceMarkdown("```sql\nSELECT 1\n```"); got != "SELECT 1" {
		t.Fatalf("fence markdown não removida: %q", got)
	}
	if got := truncarTexto(strings.Repeat("a", 20), 10); !strings.HasSuffix(got, "...") {
		t.Fatalf("texto truncado inesperado: %q", got)
	}
	if got := minInt(2, 5); got != 2 {
		t.Fatalf("minInt inesperado: %d", got)
	}
	if got := citarIdentificador(`coluna"x`); got != `"coluna""x"` {
		t.Fatalf("identificador citado inesperado: %q", got)
	}
}

func TestFormatarConsultaComoTabelaEHelpers(t *testing.T) {
	resultado := ResultadoConsulta{
		Colunas: []string{"metodo", "valor"},
		Linhas:  [][]string{{"run", "1"}, {"execute", "2"}},
	}
	texto := formatarConsultaComoTabela("titulo", resultado)
	if !strings.Contains(texto, "titulo") || !strings.Contains(texto, "metodo") {
		t.Fatalf("tabela formatada inesperada: %q", texto)
	}
	if got := formatarLinha([]interface{}{nil, 12, true}); len(got) != 3 || got[0] != "" || got[1] != "12" {
		t.Fatalf("linha formatada inesperada: %#v", got)
	}
	if got := nullIfBlank("   "); got != nil {
		t.Fatalf("nullIfBlank deveria devolver nil para branco")
	}
}

func TestConsultasDePlotContemExecucaoInformada(t *testing.T) {
	for _, consulta := range []string{
		consultaParte1Plot("run-1"),
		consultaParte1Texto("run-1"),
		consultaParte2SuitesPlot("run-1"),
		consultaParte2SuitesTexto("run-1"),
		consultaParte2MetricasPlot("run-1"),
		consultaParte2MetricasTexto("run-1"),
	} {
		if !strings.Contains(consulta, "run-1") {
			t.Fatalf("consulta deveria referenciar a execução: %q", consulta)
		}
	}
}
