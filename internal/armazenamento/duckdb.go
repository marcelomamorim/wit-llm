package armazenamento

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"
	"time"

	_ "github.com/duckdb/duckdb-go/v2"

	"github.com/marceloamorim/witup-llm/internal/dominio"
	"github.com/marceloamorim/witup-llm/internal/witup"
)

// BancoDuckDB concentra a persistência analítica e o índice dos artefatos do projeto.
type BancoDuckDB struct {
	db      *sql.DB
	caminho string
}

// ResumoSincronizacao descreve o resultado de uma carga de baselines do WITUP.
type ResumoSincronizacao struct {
	ProjetosEncontrados int
	ProjetosImportados  int
	ProjetosAtualizados int
}

// ObjetoBanco representa um objeto visível na interface de consulta.
type ObjetoBanco struct {
	Esquema string `json:"schema"`
	Nome    string `json:"name"`
	Tipo    string `json:"type"`
}

// ResultadoConsulta devolve uma consulta SQL em formato tabular simples.
type ResultadoConsulta struct {
	Colunas          []string   `json:"columns"`
	Linhas           [][]string `json:"rows"`
	LimiteLinhas     int        `json:"row_limit"`
	LinhasTruncadas  bool       `json:"rows_truncated"`
	CaracteresMaximo int        `json:"cell_char_limit"`
}

// ResumoGraficos descreve o resultado da materialização gráfica do estudo.
type ResumoGraficos struct {
	Diretorio       string
	UsouTextplot    bool
	ArquivosGerados []string
}

const (
	limiteLinhasConsulta = 200
	limiteCaracteresCell = 4000
)

// AbrirBancoDuckDB abre ou cria um banco DuckDB persistente em arquivo.
func AbrirBancoDuckDB(caminho string) (*BancoDuckDB, error) {
	if strings.TrimSpace(caminho) == "" {
		return nil, fmt.Errorf("o caminho do DuckDB é obrigatório")
	}
	if err := os.MkdirAll(filepath.Dir(caminho), 0o755); err != nil {
		return nil, fmt.Errorf("ao criar o diretório do DuckDB: %w", err)
	}

	db, err := sql.Open("duckdb", caminho)
	if err != nil {
		return nil, fmt.Errorf("ao abrir o DuckDB %q: %w", caminho, err)
	}

	banco := &BancoDuckDB{db: db, caminho: caminho}
	if err := banco.garantirEsquema(); err != nil {
		_ = db.Close()
		return nil, err
	}
	return banco, nil
}

// Caminho devolve o arquivo físico do banco atual.
func (b *BancoDuckDB) Caminho() string {
	return b.caminho
}

// Fechar encerra a conexão com o DuckDB.
func (b *BancoDuckDB) Fechar() error {
	if b == nil || b.db == nil {
		return nil
	}
	return b.db.Close()
}

// garantirEsquema assegura as tabelas base e views analíticas esperadas pela UI
// e pelas consultas do estudo.
func (b *BancoDuckDB) garantirEsquema() error {
	instrucoes := []string{
		`CREATE TABLE IF NOT EXISTS baselines_witup (
			chave_projeto VARCHAR NOT NULL,
			nome_arquivo VARCHAR NOT NULL,
			caminho_origem VARCHAR NOT NULL,
			hash_commit VARCHAR,
			importado_em TIMESTAMP NOT NULL,
			total_metodos BIGINT NOT NULL,
			total_caminhos BIGINT NOT NULL,
			payload_bruto_json VARCHAR NOT NULL,
			relatorio_json VARCHAR NOT NULL,
			PRIMARY KEY (chave_projeto, nome_arquivo)
		);`,
		`CREATE TABLE IF NOT EXISTS artefatos_execucao (
			id_execucao VARCHAR NOT NULL,
			tipo_artefato VARCHAR NOT NULL,
			chave_projeto VARCHAR,
			variante VARCHAR,
			caminho_arquivo VARCHAR NOT NULL,
			gerado_em TIMESTAMP NOT NULL,
			payload_json VARCHAR NOT NULL
		);`,
		`CREATE OR REPLACE VIEW vw_baselines_witup AS
		 SELECT
		   chave_projeto,
		   nome_arquivo,
		   caminho_origem,
		   hash_commit,
		   importado_em,
		   total_metodos,
		   total_caminhos
		 FROM baselines_witup
		 ORDER BY chave_projeto, nome_arquivo;`,
		`CREATE OR REPLACE VIEW vw_artefatos_execucao AS
		 SELECT
		   id_execucao,
		   tipo_artefato,
		   chave_projeto,
		   variante,
		   caminho_arquivo,
		   gerado_em
		 FROM artefatos_execucao
		 ORDER BY gerado_em DESC, id_execucao, tipo_artefato;`,
		`CREATE OR REPLACE VIEW vw_comparacao_fontes_resumo AS
		 SELECT
		   id_execucao,
		   chave_projeto,
		   gerado_em,
		   json_extract_string(payload_json, '$.witup_analysis_path') AS caminho_analise_witup,
		   json_extract_string(payload_json, '$.llm_analysis_path') AS caminho_analise_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.witup_method_count') AS BIGINT) AS quantidade_metodos_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.llm_method_count') AS BIGINT) AS quantidade_metodos_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.methods_in_both') AS BIGINT) AS metodos_em_ambos,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.methods_only_witup') AS BIGINT) AS metodos_apenas_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.methods_only_llm') AS BIGINT) AS metodos_apenas_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.witup_expath_count') AS BIGINT) AS quantidade_expaths_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.llm_expath_count') AS BIGINT) AS quantidade_expaths_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.shared_expath_count') AS BIGINT) AS quantidade_expaths_compartilhados,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.witup_only_expath_count') AS BIGINT) AS quantidade_expaths_apenas_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.summary.llm_only_expath_count') AS BIGINT) AS quantidade_expaths_apenas_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.metrics.llm_method_coverage_over_witup') AS DOUBLE) AS taxa_cobertura_metodos_llm_sobre_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.metrics.llm_expath_coverage_over_witup') AS DOUBLE) AS taxa_cobertura_expaths_llm_sobre_witup,
		   TRY_CAST(json_extract_string(payload_json, '$.metrics.llm_structural_precision') AS DOUBLE) AS taxa_precisao_estrutural_llm,
		   TRY_CAST(json_extract_string(payload_json, '$.metrics.expath_jaccard_index') AS DOUBLE) AS indice_jaccard_expaths,
		   TRY_CAST(json_extract_string(payload_json, '$.metrics.llm_novelty_rate') AS DOUBLE) AS taxa_novidade_llm
		 FROM artefatos_execucao
		 WHERE tipo_artefato = 'comparacao_fontes'
		 ORDER BY gerado_em DESC, id_execucao;`,
		`CREATE OR REPLACE VIEW vw_comparacao_fontes_metodo AS
		 SELECT
		   a.id_execucao,
		   a.chave_projeto,
		   a.gerado_em,
		   json_extract_string(m.value, '$.unit.class_name') AS nome_classe,
		   json_extract_string(m.value, '$.unit.method_signature') AS assinatura_metodo,
		   json_extract_string(m.value, '$.unit.exception_type') AS tipo_excecao,
		   TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT) AS quantidade_expaths_witup,
		   TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) AS quantidade_expaths_llm,
		   TRY_CAST(json_extract_string(m.value, '$.shared_expath_count') AS BIGINT) AS quantidade_expaths_compartilhados,
		   json_array_length(json_extract(m.value, '$.witup_only_expath_ids')) AS quantidade_expaths_apenas_witup,
		   json_array_length(json_extract(m.value, '$.llm_only_expath_ids')) AS quantidade_expaths_apenas_llm,
		   CASE
		     WHEN TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT) = 0
		      AND TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) > 0 THEN 'LLM_ONLY'
		     WHEN TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) = 0
		      AND TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT) > 0 THEN 'WITUP_ONLY'
		     WHEN TRY_CAST(json_extract_string(m.value, '$.shared_expath_count') AS BIGINT) = TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT)
		      AND TRY_CAST(json_extract_string(m.value, '$.shared_expath_count') AS BIGINT) = TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) THEN 'EQUIVALENTE_ESTRUTURAL'
		     WHEN TRY_CAST(json_extract_string(m.value, '$.shared_expath_count') AS BIGINT) = TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT)
		      AND TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) > TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT) THEN 'LLM_SUPERCONJUNTO'
		     WHEN TRY_CAST(json_extract_string(m.value, '$.shared_expath_count') AS BIGINT) = TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT)
		      AND TRY_CAST(json_extract_string(m.value, '$.witup_expath_count') AS BIGINT) > TRY_CAST(json_extract_string(m.value, '$.llm_expath_count') AS BIGINT) THEN 'WITUP_SUPERCONJUNTO'
		     ELSE 'DIVERGENTE'
		   END AS relacao_estrutural,
		   CASE
		     WHEN json_array_length(json_extract(m.value, '$.witup_only_expath_ids')) > 0
		       OR json_array_length(json_extract(m.value, '$.llm_only_expath_ids')) > 0 THEN TRUE
		     ELSE FALSE
		   END AS possui_divergencia
		 FROM artefatos_execucao a,
		      LATERAL json_each(json_extract(a.payload_json, '$.methods')) m
		 WHERE a.tipo_artefato = 'comparacao_fontes'
		 ORDER BY a.gerado_em DESC, a.id_execucao, assinatura_metodo;`,
		`CREATE OR REPLACE VIEW vw_witup_maybe AS
		 SELECT
		   a.id_execucao,
		   a.chave_projeto,
		   a.gerado_em,
		   json_extract_string(analise.value, '$.method.method_id') AS id_metodo,
		   json_extract_string(analise.value, '$.method.file_path') AS caminho_arquivo,
		   json_extract_string(analise.value, '$.method.container_name') AS nome_container,
		   json_extract_string(analise.value, '$.method.signature') AS assinatura_metodo,
		   json_extract_string(caminho.value, '$.path_id') AS id_caminho,
		   json_extract_string(caminho.value, '$.exception_type') AS tipo_excecao,
		   json_extract_string(caminho.value, '$.trigger') AS gatilho,
		   TRY_CAST(json_extract_string(caminho.value, '$.confidence') AS DOUBLE) AS confianca,
		   lower(coalesce(json_extract_string(caminho.value, '$.metadata.maybe'), 'false')) = 'true' AS maybe
		 FROM artefatos_execucao a,
		      LATERAL json_each(json_extract(a.payload_json, '$.analyses')) analise,
		      LATERAL json_each(json_extract(analise.value, '$.expaths')) caminho
		 WHERE a.tipo_artefato = 'analise_witup'
		   AND lower(coalesce(json_extract_string(caminho.value, '$.metadata.maybe'), 'false')) = 'true'
		 ORDER BY a.gerado_em DESC, a.id_execucao, assinatura_metodo, id_caminho;`,
		`CREATE OR REPLACE VIEW vw_h1_maybe_recuperacao AS
		 SELECT
		   w.id_execucao,
		   w.chave_projeto,
		   w.assinatura_metodo,
		   w.tipo_excecao,
		   w.id_caminho,
		   w.gatilho,
		   w.confianca,
		   c.quantidade_expaths_witup,
		   c.quantidade_expaths_llm,
		   c.quantidade_expaths_compartilhados,
		   c.quantidade_expaths_apenas_llm,
		   c.relacao_estrutural,
		   coalesce(c.quantidade_expaths_llm, 0) > 0 AS llm_recuperou_metodo,
		   coalesce(c.quantidade_expaths_apenas_llm, 0) > 0 AS llm_adicionou_expaths
		 FROM vw_witup_maybe w
		 LEFT JOIN vw_comparacao_fontes_metodo c
		   ON c.id_execucao = w.id_execucao
		  AND c.assinatura_metodo = w.assinatura_metodo
		  AND coalesce(c.tipo_excecao, '') = coalesce(w.tipo_excecao, '')
		 ORDER BY w.id_execucao DESC, w.assinatura_metodo, w.id_caminho;`,
		`CREATE OR REPLACE VIEW vw_h2_relacoes_estruturais AS
		 SELECT
		   id_execucao,
		   chave_projeto,
		   gerado_em,
		   assinatura_metodo,
		   tipo_excecao,
		   quantidade_expaths_witup,
		   quantidade_expaths_llm,
		   quantidade_expaths_compartilhados,
		   quantidade_expaths_apenas_witup,
		   quantidade_expaths_apenas_llm,
		   relacao_estrutural,
		   possui_divergencia
		 FROM vw_comparacao_fontes_metodo
		 ORDER BY gerado_em DESC, id_execucao, assinatura_metodo;`,
		`CREATE OR REPLACE VIEW vw_estudos_completos AS
		 SELECT
		   id_execucao,
		   chave_projeto,
		   gerado_em,
		   caminho_arquivo AS caminho_resumo,
		   json_extract_string(payload_json, '$.analysis_model_key') AS chave_modelo_analise,
		   json_extract_string(payload_json, '$.generation_model_key') AS chave_modelo_geracao,
		   json_extract_string(payload_json, '$.judge_model_key') AS chave_modelo_juiz,
		   json_extract_string(payload_json, '$.experiment_report_path') AS caminho_experimento,
		   json_extract_string(payload_json, '$.comparison_path') AS caminho_comparacao,
		   json_array_length(json_extract(payload_json, '$.variants')) AS quantidade_variantes
		 FROM artefatos_execucao
		 WHERE tipo_artefato = 'estudo_completo'
		 ORDER BY gerado_em DESC, id_execucao;`,
		`CREATE OR REPLACE VIEW vw_estudo_variantes AS
		 SELECT
		   a.id_execucao,
		   a.chave_projeto,
		   a.gerado_em,
		   json_extract_string(a.payload_json, '$.analysis_model_key') AS chave_modelo_analise,
		   json_extract_string(a.payload_json, '$.generation_model_key') AS chave_modelo_geracao,
		   json_extract_string(a.payload_json, '$.judge_model_key') AS chave_modelo_juiz,
		   json_extract_string(v.value, '$.variant') AS variante,
		   json_extract_string(v.value, '$.analysis_path') AS caminho_analise,
		   TRY_CAST(json_extract_string(v.value, '$.method_count') AS BIGINT) AS quantidade_metodos,
		   TRY_CAST(json_extract_string(v.value, '$.expath_count') AS BIGINT) AS quantidade_expaths,
		   json_extract_string(v.value, '$.generation_path') AS caminho_geracao,
		   TRY_CAST(json_extract_string(v.value, '$.test_file_count') AS BIGINT) AS quantidade_arquivos_teste,
		   json_extract_string(v.value, '$.evaluation_path') AS caminho_avaliacao,
		   TRY_CAST(json_extract_string(v.value, '$.judge_score') AS DOUBLE) AS nota_juiz,
		   json_extract_string(v.value, '$.judge_verdict') AS veredito_juiz,
		   TRY_CAST(json_extract_string(v.value, '$.metric_score') AS DOUBLE) AS nota_metricas,
		   TRY_CAST(json_extract_string(v.value, '$.combined_score') AS DOUBLE) AS nota_combinada,
		   TRY_CAST(json_extract_string(v.value, '$.derived_metrics.test_files_per_method') AS DOUBLE) AS taxa_arquivos_teste_por_metodo,
		   TRY_CAST(json_extract_string(v.value, '$.derived_metrics.test_files_per_expath') AS DOUBLE) AS taxa_arquivos_teste_por_expath,
		   TRY_CAST(json_extract_string(v.value, '$.derived_metrics.metric_success_rate') AS DOUBLE) AS taxa_sucesso_metricas
		 FROM artefatos_execucao a,
		      LATERAL json_each(json_extract(a.payload_json, '$.variants')) v
		 WHERE a.tipo_artefato = 'estudo_completo'
		 ORDER BY a.gerado_em DESC, a.id_execucao, variante;`,
		`CREATE OR REPLACE VIEW vw_h3_metricas_variantes AS
		 SELECT
		   a.id_execucao,
		   a.chave_projeto,
		   a.gerado_em,
		   json_extract_string(v.value, '$.variant') AS variante,
		   json_extract_string(v.value, '$.analysis_path') AS caminho_analise,
		   json_extract_string(v.value, '$.evaluation_path') AS caminho_avaliacao,
		   json_extract_string(m.value, '$.name') AS nome_metrica,
		   json_extract_string(m.value, '$.kind') AS tipo_metrica,
		   lower(coalesce(json_extract_string(m.value, '$.success'), 'false')) = 'true' AS sucesso,
		   TRY_CAST(json_extract_string(m.value, '$.numeric_value') AS DOUBLE) AS valor_numerico,
		   TRY_CAST(json_extract_string(m.value, '$.normalized_score') AS DOUBLE) AS nota_normalizada,
		   TRY_CAST(json_extract_string(m.value, '$.weight') AS DOUBLE) AS peso,
		   json_extract_string(m.value, '$.description') AS descricao
		 FROM artefatos_execucao a,
		      LATERAL json_each(json_extract(a.payload_json, '$.variants')) v,
		      LATERAL json_each(json_extract(v.value, '$.metric_results')) m
		 WHERE a.tipo_artefato = 'estudo_completo'
		 ORDER BY a.gerado_em DESC, a.id_execucao, variante, nome_metrica;`,
		`CREATE OR REPLACE VIEW vw_h3_comparacao_suites AS
		 WITH base AS (
		   SELECT
		     id_execucao,
		     chave_projeto,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN quantidade_metodos END) AS metodos_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN quantidade_metodos END) AS metodos_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN quantidade_metodos END) AS metodos_combinado,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN quantidade_expaths END) AS expaths_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN quantidade_expaths END) AS expaths_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN quantidade_expaths END) AS expaths_combinado,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN quantidade_arquivos_teste END) AS arquivos_teste_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN quantidade_arquivos_teste END) AS arquivos_teste_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN quantidade_arquivos_teste END) AS arquivos_teste_combinado,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN nota_metricas END) AS nota_metricas_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN nota_metricas END) AS nota_metricas_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN nota_metricas END) AS nota_metricas_combinado,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN nota_juiz END) AS nota_juiz_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN nota_juiz END) AS nota_juiz_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN nota_juiz END) AS nota_juiz_combinado,
		     MAX(CASE WHEN variante = 'WITUP_ONLY' THEN nota_combinada END) AS nota_combinada_witup,
		     MAX(CASE WHEN variante = 'LLM_ONLY' THEN nota_combinada END) AS nota_combinada_llm,
		     MAX(CASE WHEN variante = 'WITUP_PLUS_LLM' THEN nota_combinada END) AS nota_combinada_combinado
		   FROM vw_estudo_variantes
		   GROUP BY id_execucao, chave_projeto
		 )
		 SELECT
		   id_execucao,
		   chave_projeto,
		   metodos_witup,
		   metodos_llm,
		   metodos_combinado,
		   expaths_witup,
		   expaths_llm,
		   expaths_combinado,
		   arquivos_teste_witup,
		   arquivos_teste_llm,
		   arquivos_teste_combinado,
		   nota_metricas_witup,
		   nota_metricas_llm,
		   nota_metricas_combinado,
		   nota_juiz_witup,
		   nota_juiz_llm,
		   nota_juiz_combinado,
		   nota_combinada_witup,
		   nota_combinada_llm,
		   nota_combinada_combinado,
		   nota_metricas_llm - nota_metricas_witup AS delta_metricas_llm_vs_witup,
		   nota_metricas_combinado - nota_metricas_witup AS delta_metricas_combinado_vs_witup,
		   nota_metricas_combinado - nota_metricas_llm AS delta_metricas_combinado_vs_llm,
		   nota_combinada_llm - nota_combinada_witup AS delta_combinada_llm_vs_witup,
		   nota_combinada_combinado - nota_combinada_witup AS delta_combinada_combinado_vs_witup,
		   nota_combinada_combinado - nota_combinada_llm AS delta_combinada_combinado_vs_llm
		 FROM base
		 ORDER BY id_execucao DESC, chave_projeto;`,
		`CREATE OR REPLACE VIEW vw_h3_qualidade_variantes AS
		 SELECT
		   chave_projeto,
		   variante,
		   COUNT(*) AS total_execucoes,
		   AVG(quantidade_metodos) AS media_metodos,
		   AVG(quantidade_expaths) AS media_expaths,
		   AVG(quantidade_arquivos_teste) AS media_arquivos_teste,
		   AVG(taxa_arquivos_teste_por_metodo) AS media_arquivos_teste_por_metodo,
		   AVG(taxa_arquivos_teste_por_expath) AS media_arquivos_teste_por_expath,
		   AVG(taxa_sucesso_metricas) AS media_sucesso_metricas,
		   AVG(nota_metricas) AS media_nota_metricas,
		   AVG(nota_juiz) AS media_nota_juiz,
		   AVG(nota_combinada) AS media_nota_combinada
		 FROM vw_estudo_variantes
		 GROUP BY chave_projeto, variante
		 ORDER BY chave_projeto, variante;`,
		`CREATE OR REPLACE VIEW vw_h4_divergencias_base AS
		 SELECT
		   id_execucao,
		   chave_projeto,
		   assinatura_metodo,
		   tipo_excecao,
		   relacao_estrutural,
		   possui_divergencia,
		   quantidade_expaths_witup,
		   quantidade_expaths_llm,
		   quantidade_expaths_compartilhados,
		   quantidade_expaths_apenas_witup,
		   quantidade_expaths_apenas_llm
		 FROM vw_comparacao_fontes_metodo
		 ORDER BY id_execucao DESC, assinatura_metodo;`,
	}

	for _, instrucao := range instrucoes {
		if _, err := b.db.Exec(instrucao); err != nil {
			return fmt.Errorf("ao preparar o schema do DuckDB: %w", err)
		}
	}
	return nil
}

// SincronizarBaselines importa para o DuckDB todas as baselines encontradas na
// raiz do pacote de replicação.
func (b *BancoDuckDB) SincronizarBaselines(raizReplicacao, nomeArquivo string) (ResumoSincronizacao, error) {
	entradas, err := os.ReadDir(raizReplicacao)
	if err != nil {
		return ResumoSincronizacao{}, fmt.Errorf("ao listar a raiz de replicação %q: %w", raizReplicacao, err)
	}

	resumo := ResumoSincronizacao{}
	for _, entrada := range entradas {
		if !entrada.IsDir() {
			continue
		}
		resumo.ProjetosEncontrados++

		chaveProjeto := entrada.Name()
		caminhoBaseline := filepath.Join(raizReplicacao, chaveProjeto, nomeArquivo)
		info, err := os.Stat(caminhoBaseline)
		if err != nil || info.IsDir() {
			continue
		}

		importado, atualizado, err := b.ImportarBaselineProjeto(chaveProjeto, caminhoBaseline, nomeArquivo)
		if err != nil {
			return resumo, err
		}
		if importado {
			resumo.ProjetosImportados++
		}
		if atualizado {
			resumo.ProjetosAtualizados++
		}
	}
	return resumo, nil
}

// ImportarBaselineProjeto carrega uma baseline WITUP para o DuckDB e armazena
// tanto o payload bruto quanto o relatório canônico correspondente.
func (b *BancoDuckDB) ImportarBaselineProjeto(chaveProjeto, caminhoBaseline, nomeArquivo string) (bool, bool, error) {
	payloadBruto, err := os.ReadFile(caminhoBaseline)
	if err != nil {
		return false, false, fmt.Errorf("ao ler a baseline %q: %w", caminhoBaseline, err)
	}

	relatorio, err := witup.CarregarAnalise(caminhoBaseline)
	if err != nil {
		return false, false, err
	}

	hashCommit := extrairHashCommit(payloadBruto)
	relatorioJSON, err := json.Marshal(relatorio)
	if err != nil {
		return false, false, fmt.Errorf("ao serializar o relatório canônico do WITUP: %w", err)
	}

	var quantidadeAnterior int
	if err := b.db.QueryRow(
		`SELECT COUNT(*) FROM baselines_witup WHERE chave_projeto = ? AND nome_arquivo = ?`,
		chaveProjeto,
		nomeArquivo,
	).Scan(&quantidadeAnterior); err != nil {
		return false, false, fmt.Errorf("ao consultar a baseline existente de %q: %w", chaveProjeto, err)
	}

	if _, err := b.db.Exec(
		`INSERT OR REPLACE INTO baselines_witup (
			chave_projeto,
			nome_arquivo,
			caminho_origem,
			hash_commit,
			importado_em,
			total_metodos,
			total_caminhos,
			payload_bruto_json,
			relatorio_json
		) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`,
		chaveProjeto,
		nomeArquivo,
		caminhoBaseline,
		hashCommit,
		time.Now().UTC(),
		relatorio.TotalMetodos,
		contarCaminhos(relatorio),
		string(payloadBruto),
		string(relatorioJSON),
	); err != nil {
		return false, false, fmt.Errorf("ao persistir a baseline %q no DuckDB: %w", chaveProjeto, err)
	}

	return quantidadeAnterior == 0, quantidadeAnterior > 0, nil
}

// CarregarRelatorioBaseline devolve a baseline WITUP já normalizada a partir do DuckDB.
func (b *BancoDuckDB) CarregarRelatorioBaseline(chaveProjeto, nomeArquivo string) (dominio.RelatorioAnalise, string, error) {
	var caminhoOrigem string
	var relatorioJSON string
	err := b.db.QueryRow(
		`SELECT caminho_origem, relatorio_json
		   FROM baselines_witup
		  WHERE chave_projeto = ? AND nome_arquivo = ?`,
		chaveProjeto,
		nomeArquivo,
	).Scan(&caminhoOrigem, &relatorioJSON)
	if err == sql.ErrNoRows {
		return dominio.RelatorioAnalise{}, "", fmt.Errorf("a baseline %q/%q ainda não foi carregada no DuckDB", chaveProjeto, nomeArquivo)
	}
	if err != nil {
		return dominio.RelatorioAnalise{}, "", fmt.Errorf("ao ler a baseline %q do DuckDB: %w", chaveProjeto, err)
	}

	relatorio := dominio.RelatorioAnalise{}
	if err := json.Unmarshal([]byte(relatorioJSON), &relatorio); err != nil {
		return dominio.RelatorioAnalise{}, "", fmt.Errorf("ao desserializar a baseline canônica de %q: %w", chaveProjeto, err)
	}
	return relatorio, caminhoOrigem, nil
}

// ListarObjetos devolve tabelas e views disponíveis para a interface gráfica.
func (b *BancoDuckDB) ListarObjetos() ([]ObjetoBanco, error) {
	linhas, err := b.db.Query(`
		SELECT table_schema, table_name, table_type
		  FROM information_schema.tables
		 WHERE table_schema NOT IN ('information_schema', 'pg_catalog')
		 ORDER BY table_schema, table_name`)
	if err != nil {
		return nil, fmt.Errorf("ao listar os objetos do DuckDB: %w", err)
	}
	defer linhas.Close()

	objetos := make([]ObjetoBanco, 0)
	for linhas.Next() {
		var objeto ObjetoBanco
		if err := linhas.Scan(&objeto.Esquema, &objeto.Nome, &objeto.Tipo); err != nil {
			return nil, fmt.Errorf("ao ler a lista de objetos do DuckDB: %w", err)
		}
		objetos = append(objetos, objeto)
	}
	return objetos, linhas.Err()
}

// ExecutarConsultaSomenteLeitura executa uma consulta SQL de leitura e devolve
// os resultados em um formato simples para a UI e a CLI.
func (b *BancoDuckDB) ExecutarConsultaSomenteLeitura(consulta string) (ResultadoConsulta, error) {
	consulta = strings.TrimSpace(consulta)
	if !consultaSomenteLeitura(consulta) {
		return ResultadoConsulta{}, fmt.Errorf("a interface aceita apenas consultas de leitura")
	}

	linhas, err := b.db.Query(consulta)
	if err != nil {
		return ResultadoConsulta{}, fmt.Errorf("ao executar a consulta no DuckDB: %w", err)
	}
	defer linhas.Close()

	colunas, err := linhas.Columns()
	if err != nil {
		return ResultadoConsulta{}, fmt.Errorf("ao listar as colunas da consulta: %w", err)
	}

	resultado := ResultadoConsulta{
		Colunas:          colunas,
		Linhas:           make([][]string, 0, minInt(limiteLinhasConsulta, 16)),
		LimiteLinhas:     limiteLinhasConsulta,
		CaracteresMaximo: limiteCaracteresCell,
	}

	for linhas.Next() {
		if len(resultado.Linhas) >= limiteLinhasConsulta {
			resultado.LinhasTruncadas = true
			break
		}
		valores := make([]interface{}, len(colunas))
		destinos := make([]interface{}, len(colunas))
		for i := range valores {
			destinos[i] = &valores[i]
		}
		if err := linhas.Scan(destinos...); err != nil {
			return ResultadoConsulta{}, fmt.Errorf("ao ler linha da consulta: %w", err)
		}
		resultado.Linhas = append(resultado.Linhas, formatarLinha(valores))
	}
	return resultado, linhas.Err()
}

// VisualizarObjeto devolve as primeiras linhas de uma tabela ou view.
func (b *BancoDuckDB) VisualizarObjeto(esquema, nome string, limite int) (ResultadoConsulta, error) {
	if limite <= 0 {
		limite = 100
	}
	if strings.TrimSpace(nome) == "" {
		return ResultadoConsulta{}, fmt.Errorf("o nome do objeto é obrigatório")
	}
	objeto := citarIdentificador(nome)
	if strings.TrimSpace(esquema) != "" {
		objeto = citarIdentificador(esquema) + "." + objeto
	}
	consulta := fmt.Sprintf("SELECT * FROM %s LIMIT %d", objeto, limite)
	return b.ExecutarConsultaSomenteLeitura(consulta)
}

// RegistrarArtefatoExecucao indexa um artefato gerado para facilitar consultas posteriores.
func (b *BancoDuckDB) RegistrarArtefatoExecucao(
	idExecucao string,
	tipoArtefato string,
	chaveProjeto string,
	variante string,
	caminhoArquivo string,
	geradoEm string,
	payload interface{},
) error {
	payloadJSON, err := json.Marshal(payload)
	if err != nil {
		return fmt.Errorf("ao serializar o artefato %q para o DuckDB: %w", tipoArtefato, err)
	}

	momento, err := time.Parse(time.RFC3339, geradoEm)
	if err != nil {
		momento = time.Now().UTC()
	}

	if _, err := b.db.Exec(
		`INSERT INTO artefatos_execucao (
			id_execucao,
			tipo_artefato,
			chave_projeto,
			variante,
			caminho_arquivo,
			gerado_em,
			payload_json
		) VALUES (?, ?, ?, ?, ?, ?, ?)`,
		idExecucao,
		tipoArtefato,
		nullIfBlank(chaveProjeto),
		nullIfBlank(variante),
		caminhoArquivo,
		momento,
		string(payloadJSON),
	); err != nil {
		return fmt.Errorf("ao registrar o artefato %q no DuckDB: %w", tipoArtefato, err)
	}
	return nil
}

// GerarGraficosExecucao materializa resumos gráficos do estudo a partir do DuckDB.
func (b *BancoDuckDB) GerarGraficosExecucao(idExecucao, diretorioSaida string) (ResumoGraficos, error) {
	if strings.TrimSpace(idExecucao) == "" {
		return ResumoGraficos{}, fmt.Errorf("o id da execução é obrigatório para gerar gráficos")
	}
	if err := os.MkdirAll(diretorioSaida, 0o755); err != nil {
		return ResumoGraficos{}, fmt.Errorf("ao criar diretório de gráficos %q: %w", diretorioSaida, err)
	}

	usouTextplot := b.habilitarTextplot()
	especificacoes := []struct {
		nomeArquivo   string
		titulo        string
		consultaPlot  string
		consultaTexto string
	}{
		{
			nomeArquivo:   "parte-1-expaths.txt",
			titulo:        "Parte 1 :: Comparação de Expaths",
			consultaPlot:  consultaParte1Plot(idExecucao),
			consultaTexto: consultaParte1Texto(idExecucao),
		},
		{
			nomeArquivo:   "parte-2-suites.txt",
			titulo:        "Parte 2 :: Comparação das Suítes",
			consultaPlot:  consultaParte2SuitesPlot(idExecucao),
			consultaTexto: consultaParte2SuitesTexto(idExecucao),
		},
		{
			nomeArquivo:   "parte-2-metricas.txt",
			titulo:        "Parte 2 :: Métricas por Variante",
			consultaPlot:  consultaParte2MetricasPlot(idExecucao),
			consultaTexto: consultaParte2MetricasTexto(idExecucao),
		},
	}

	arquivos := make([]string, 0, len(especificacoes))
	for _, especificacao := range especificacoes {
		consulta := especificacao.consultaTexto
		if usouTextplot {
			consulta = especificacao.consultaPlot
		}
		resultado, err := b.ExecutarConsultaSomenteLeitura(consulta)
		if err != nil && usouTextplot {
			resultado, err = b.ExecutarConsultaSomenteLeitura(especificacao.consultaTexto)
			usouTextplot = false
		}
		if err != nil {
			return ResumoGraficos{}, err
		}

		conteudo := formatarConsultaComoTabela(especificacao.titulo, resultado)
		caminhoArquivo := filepath.Join(diretorioSaida, especificacao.nomeArquivo)
		if err := os.WriteFile(caminhoArquivo, []byte(conteudo), 0o644); err != nil {
			return ResumoGraficos{}, fmt.Errorf("ao gravar gráfico %q: %w", caminhoArquivo, err)
		}
		arquivos = append(arquivos, caminhoArquivo)
	}

	return ResumoGraficos{
		Diretorio:       diretorioSaida,
		UsouTextplot:    usouTextplot,
		ArquivosGerados: arquivos,
	}, nil
}

// habilitarTextplot tenta carregar a extensão opcional usada para gráficos ASCII.
func (b *BancoDuckDB) habilitarTextplot() bool {
	if _, err := b.db.Exec(`INSTALL textplot FROM community`); err != nil {
		if _, loadErr := b.db.Exec(`LOAD textplot`); loadErr != nil {
			return false
		}
		return true
	}
	if _, err := b.db.Exec(`LOAD textplot`); err != nil {
		return false
	}
	return true
}

// consultaParte1Plot devolve a consulta gráfica da comparação de expaths.
func consultaParte1Plot(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
WITH metricas AS (
  SELECT 'Cobertura métodos LLM/WITUP' AS metrica, taxa_cobertura_metodos_llm_sobre_witup AS valor
  FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'
  UNION ALL
  SELECT 'Cobertura expaths LLM/WITUP', taxa_cobertura_expaths_llm_sobre_witup
  FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'
  UNION ALL
  SELECT 'Precisão estrutural LLM', taxa_precisao_estrutural_llm
  FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'
  UNION ALL
  SELECT 'Jaccard expaths', indice_jaccard_expaths
  FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'
  UNION ALL
  SELECT 'Novidade LLM', taxa_novidade_llm
  FROM vw_comparacao_fontes_resumo WHERE id_execucao = '%s'
)
SELECT metrica, printf('%%.2f', valor) AS valor, tp_bar(valor, min := 0, max := 100, width := 30) AS grafico
FROM metricas
WHERE valor IS NOT NULL`, id, id, id, id, id)
}

// consultaParte1Texto devolve a consulta tabular de fallback para a Parte 1.
func consultaParte1Texto(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
SELECT
  taxa_cobertura_metodos_llm_sobre_witup,
  taxa_cobertura_expaths_llm_sobre_witup,
  taxa_precisao_estrutural_llm,
  indice_jaccard_expaths,
  taxa_novidade_llm
FROM vw_comparacao_fontes_resumo
WHERE id_execucao = '%s'`, id)
}

// consultaParte2SuitesPlot devolve a consulta gráfica de comparação das suítes.
func consultaParte2SuitesPlot(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
SELECT
  variante,
  printf('%%.2f', nota_metricas) AS nota_metricas,
  tp_bar(nota_metricas, min := 0, max := 100, width := 24) AS grafico_metricas,
  printf('%%.2f', nota_combinada) AS nota_combinada,
  tp_bar(nota_combinada, min := 0, max := 100, width := 24) AS grafico_combinada
FROM vw_estudo_variantes
WHERE id_execucao = '%s'
ORDER BY variante`, id)
}

// consultaParte2SuitesTexto devolve a versão tabular da comparação de suítes.
func consultaParte2SuitesTexto(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
SELECT
  variante,
  quantidade_expaths,
  quantidade_arquivos_teste,
  nota_metricas,
  nota_juiz,
  nota_combinada
FROM vw_estudo_variantes
WHERE id_execucao = '%s'
ORDER BY variante`, id)
}

// consultaParte2MetricasPlot devolve a consulta gráfica das métricas por variante.
func consultaParte2MetricasPlot(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
SELECT
  variante || ' :: ' || nome_metrica AS item,
  printf('%%.2f', nota_normalizada) AS nota,
  tp_bar(nota_normalizada, min := 0, max := 100, width := 24) AS grafico
FROM vw_h3_metricas_variantes
WHERE id_execucao = '%s'
ORDER BY variante, nome_metrica`, id)
}

// consultaParte2MetricasTexto devolve a versão tabular das métricas por variante.
func consultaParte2MetricasTexto(idExecucao string) string {
	id := escaparLiteralSQL(idExecucao)
	return fmt.Sprintf(`
SELECT
  variante,
  nome_metrica,
  sucesso,
  valor_numerico,
  nota_normalizada
FROM vw_h3_metricas_variantes
WHERE id_execucao = '%s'
ORDER BY variante, nome_metrica`, id)
}

// escaparLiteralSQL protege literais simples usados nas queries montadas localmente.
func escaparLiteralSQL(valor string) string {
	return strings.ReplaceAll(valor, "'", "''")
}

// formatarConsultaComoTabela renderiza o resultado em uma tabela ASCII simples.
func formatarConsultaComoTabela(titulo string, resultado ResultadoConsulta) string {
	linhas := []string{titulo, strings.Repeat("=", len(titulo)), ""}
	if len(resultado.Colunas) == 0 {
		return strings.Join(append(linhas, "(sem dados)", ""), "\n")
	}

	larguras := make([]int, len(resultado.Colunas))
	for i, coluna := range resultado.Colunas {
		larguras[i] = len(coluna)
	}
	for _, linha := range resultado.Linhas {
		for i, valor := range linha {
			if len(valor) > larguras[i] {
				larguras[i] = len(valor)
			}
		}
	}

	linhas = append(linhas, formatarLinhaTabela(resultado.Colunas, larguras))
	separador := make([]string, len(larguras))
	for i, largura := range larguras {
		separador[i] = strings.Repeat("-", largura)
	}
	linhas = append(linhas, formatarLinhaTabela(separador, larguras))
	for _, linha := range resultado.Linhas {
		linhas = append(linhas, formatarLinhaTabela(linha, larguras))
	}
	if resultado.LinhasTruncadas {
		linhas = append(linhas, "", fmt.Sprintf("(resultado truncado em %d linhas)", resultado.LimiteLinhas))
	}
	linhas = append(linhas, "")
	return strings.Join(linhas, "\n")
}

// formatarLinhaTabela alinha cada coluna com a largura máxima calculada.
func formatarLinhaTabela(valores []string, larguras []int) string {
	partes := make([]string, len(valores))
	for i, valor := range valores {
		partes[i] = valor + strings.Repeat(" ", larguras[i]-len(valor))
	}
	return strings.Join(partes, " | ")
}

// consultaSomenteLeitura valida o primeiro comando SQL permitido na interface.
func consultaSomenteLeitura(consulta string) bool {
	consulta = strings.ToUpper(normalizarConsultaSomenteLeitura(consulta))
	if consulta == "" {
		return false
	}
	palavras := strings.Fields(consulta)
	if len(palavras) == 0 {
		return false
	}
	primeiraPalavra := palavras[0]
	comandosPermitidos := map[string]struct{}{
		"SELECT":    {},
		"WITH":      {},
		"SHOW":      {},
		"DESCRIBE":  {},
		"SUMMARIZE": {},
		"PRAGMA":    {},
		"EXPLAIN":   {},
	}
	if _, permitido := comandosPermitidos[primeiraPalavra]; permitido {
		return true
	}
	return false
}

// normalizarConsultaSomenteLeitura remove ruídos comuns de cópia e cola na UI,
// como fences Markdown, comentários SQL iniciais e ponto-e-vírgula sobrando no
// começo da consulta.
func normalizarConsultaSomenteLeitura(consulta string) string {
	consulta = strings.TrimSpace(consulta)
	if strings.HasPrefix(consulta, "```") {
		consulta = removerFenceMarkdown(consulta)
	}

	for {
		consulta = strings.TrimSpace(strings.TrimLeft(consulta, ";"))
		switch {
		case strings.HasPrefix(consulta, "--"):
			if indiceQuebra := strings.IndexByte(consulta, '\n'); indiceQuebra >= 0 {
				consulta = consulta[indiceQuebra+1:]
				continue
			}
			return ""
		case strings.HasPrefix(consulta, "/*"):
			if indiceFim := strings.Index(consulta, "*/"); indiceFim >= 0 {
				consulta = consulta[indiceFim+2:]
				continue
			}
			return ""
		default:
			return strings.TrimSpace(consulta)
		}
	}
}

// removerFenceMarkdown descarta cercas ```sql ... ``` antes da validação SQL.
func removerFenceMarkdown(texto string) string {
	linhas := strings.Split(strings.TrimSpace(texto), "\n")
	if len(linhas) >= 2 && strings.HasPrefix(strings.TrimSpace(linhas[0]), "```") &&
		strings.HasPrefix(strings.TrimSpace(linhas[len(linhas)-1]), "```") {
		return strings.TrimSpace(strings.Join(linhas[1:len(linhas)-1], "\n"))
	}
	return texto
}

// formatarLinha converte tipos arbitrários do driver em strings próprias para a UI.
func formatarLinha(valores []interface{}) []string {
	linha := make([]string, 0, len(valores))
	for _, valor := range valores {
		switch v := valor.(type) {
		case nil:
			linha = append(linha, "")
		case []byte:
			linha = append(linha, truncarTexto(string(v), limiteCaracteresCell))
		default:
			linha = append(linha, truncarTexto(fmt.Sprint(v), limiteCaracteresCell))
		}
	}
	return linha
}

// truncarTexto limita células muito longas para evitar travamento visual na UI.
func truncarTexto(texto string, limite int) string {
	if limite <= 0 || len(texto) <= limite {
		return texto
	}
	return texto[:limite] + "..."
}

// minInt devolve o menor valor entre dois inteiros.
func minInt(a, b int) int {
	if a < b {
		return a
	}
	return b
}

// nullIfBlank converte strings vazias em NULL ao persistir no banco.
func nullIfBlank(valor string) interface{} {
	if strings.TrimSpace(valor) == "" {
		return nil
	}
	return valor
}

// contarCaminhos soma os expaths presentes em um relatório canônico.
func contarCaminhos(relatorio dominio.RelatorioAnalise) int {
	total := 0
	for _, analise := range relatorio.Analises {
		total += len(analise.CaminhosExcecao)
	}
	return total
}

// extrairHashCommit lê o commit hash bruto da baseline original do artigo.
func extrairHashCommit(payload []byte) string {
	var bruto struct {
		HashCommit string `json:"commitHash"`
	}
	if err := json.Unmarshal(payload, &bruto); err != nil {
		return ""
	}
	return strings.TrimSpace(bruto.HashCommit)
}

// citarIdentificador envolve nomes de schema e tabela com aspas duplas seguras.
func citarIdentificador(valor string) string {
	valor = strings.ReplaceAll(valor, `"`, `""`)
	return `"` + valor + `"`
}

// ListarProjetosImportados devolve os projetos disponíveis em ordem determinística.
func (b *BancoDuckDB) ListarProjetosImportados() ([]string, error) {
	linhas, err := b.db.Query(`SELECT DISTINCT chave_projeto FROM baselines_witup ORDER BY chave_projeto`)
	if err != nil {
		return nil, fmt.Errorf("ao listar projetos importados: %w", err)
	}
	defer linhas.Close()

	projetos := make([]string, 0)
	for linhas.Next() {
		var chave string
		if err := linhas.Scan(&chave); err != nil {
			return nil, fmt.Errorf("ao ler chave de projeto do DuckDB: %w", err)
		}
		projetos = append(projetos, chave)
	}
	sort.Strings(projetos)
	return projetos, linhas.Err()
}
