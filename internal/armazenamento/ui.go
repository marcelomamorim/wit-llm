package armazenamento

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strconv"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/registro"
)

type requisicaoConsulta struct {
	Consulta string `json:"query"`
}

// IniciarInterfaceHTTP expõe uma interface web simples para navegar nas tabelas
// do DuckDB e executar consultas SQL somente de leitura.
func IniciarInterfaceHTTP(caminhoBanco, endereco string) error {
	banco, err := AbrirBancoDuckDB(caminhoBanco)
	if err != nil {
		return err
	}
	defer banco.Fechar()

	mux := http.NewServeMux()
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		registro.Debug("duckdb-ui", "servindo interface HTML para %s", r.RemoteAddr)
		w.Header().Set("Content-Type", "text/html; charset=utf-8")
		_, _ = w.Write([]byte(paginaInterface))
	})
	mux.HandleFunc("/api/objetos", func(w http.ResponseWriter, r *http.Request) {
		registro.Debug("duckdb-ui", "listando objetos do banco")
		objetos, err := banco.ListarObjetos()
		escreverJSONResposta(w, objetos, err)
	})
	mux.HandleFunc("/api/objeto", func(w http.ResponseWriter, r *http.Request) {
		esquema := r.URL.Query().Get("schema")
		nome := r.URL.Query().Get("name")
		limite, _ := strconv.Atoi(r.URL.Query().Get("limit"))
		registro.Info("duckdb-ui", "visualizando objeto %s.%s limite=%d", esquema, nome, limite)
		resultado, err := banco.VisualizarObjeto(esquema, nome, limite)
		escreverJSONResposta(w, resultado, err)
	})
	mux.HandleFunc("/api/consulta", func(w http.ResponseWriter, r *http.Request) {
		consulta, err := lerConsulta(r)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}
		registro.Info("duckdb-ui", "executando consulta ad-hoc: %s", resumirConsulta(consulta))
		resultado, err := banco.ExecutarConsultaSomenteLeitura(consulta)
		escreverJSONResposta(w, resultado, err)
	})

	registro.Info("duckdb-ui", "interface DuckDB disponível em http://%s", endereco)
	registro.Info("duckdb-ui", "banco persistido em %s", caminhoBanco)
	return http.ListenAndServe(endereco, mux)
}

// lerConsulta extrai a query SQL do método HTTP aceito pela interface.
func lerConsulta(r *http.Request) (string, error) {
	switch r.Method {
	case http.MethodGet:
		return strings.TrimSpace(r.URL.Query().Get("q")), nil
	case http.MethodPost:
		corpo, err := io.ReadAll(r.Body)
		if err != nil {
			return "", fmt.Errorf("ao ler a consulta enviada: %w", err)
		}
		requisicao := requisicaoConsulta{}
		if err := json.Unmarshal(corpo, &requisicao); err != nil {
			return "", fmt.Errorf("o corpo da consulta deve ser um JSON com o campo \"query\": %w", err)
		}
		consulta := strings.TrimSpace(requisicao.Consulta)
		if consulta == "" {
			return "", fmt.Errorf("a consulta enviada não pode ser vazia")
		}
		return consulta, nil
	default:
		return "", fmt.Errorf("método HTTP não suportado: %s", r.Method)
	}
}

// escreverJSONResposta devolve payloads JSON ou traduz erros da camada HTTP em
// respostas 400 para a interface web.
func escreverJSONResposta(w http.ResponseWriter, payload interface{}, err error) {
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	w.Header().Set("Content-Type", "application/json; charset=utf-8")
	_ = json.NewEncoder(w).Encode(payload)
}

// resumirConsulta compacta a query em uma linha curta para logging da UI.
func resumirConsulta(consulta string) string {
	consulta = strings.Join(strings.Fields(strings.TrimSpace(consulta)), " ")
	if len(consulta) <= 140 {
		return consulta
	}
	return consulta[:140] + "..."
}

const paginaInterface = `<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>witup-llm :: DuckDB</title>
  <style>
    :root {
      color-scheme: light;
      --fundo: #f5f3ed;
      --painel: #fffdf7;
      --texto: #1e2a2f;
      --destaque: #0d5c63;
      --borda: #d7d1c7;
      --suave: #6b7280;
      --erro: #9f1239;
    }
    * { box-sizing: border-box; }
    body { margin: 0; font-family: Georgia, "Times New Roman", serif; background: var(--fundo); color: var(--texto); }
    header { padding: 24px 28px; border-bottom: 1px solid var(--borda); background: linear-gradient(135deg, #fffdf7, #e8f1ef); }
    h1 { margin: 0 0 8px; font-size: 28px; }
    p { margin: 0; color: var(--suave); }
    main { display: grid; grid-template-columns: 320px 1fr; min-height: calc(100vh - 98px); }
    aside, section { padding: 20px 24px; }
    aside { border-right: 1px solid var(--borda); background: #faf7f1; }
    .painel { background: var(--painel); border: 1px solid var(--borda); border-radius: 14px; padding: 16px; margin-bottom: 16px; }
    .objeto { display: block; width: 100%; text-align: left; margin: 6px 0; border: 1px solid var(--borda); background: white; border-radius: 10px; padding: 10px 12px; cursor: pointer; }
    .objeto small { color: var(--suave); display: block; }
    textarea { width: 100%; min-height: 160px; border-radius: 12px; border: 1px solid var(--borda); padding: 12px; font-family: "SFMono-Regular", ui-monospace, monospace; }
    button { background: var(--destaque); color: white; border: none; border-radius: 999px; padding: 10px 18px; cursor: pointer; }
    .acoes { display: flex; gap: 12px; margin-top: 12px; align-items: center; flex-wrap: wrap; }
    .status { color: var(--suave); font-size: 14px; }
    .erro { color: var(--erro); white-space: pre-wrap; font-family: "SFMono-Regular", ui-monospace, monospace; }
    .resultado-info { margin-bottom: 12px; color: var(--suave); }
    .grade { width: 100%; overflow: auto; }
    table { width: 100%; border-collapse: collapse; background: white; border-radius: 12px; overflow: hidden; }
    th, td { border: 1px solid var(--borda); padding: 10px 12px; text-align: left; vertical-align: top; font-size: 14px; }
    th { background: #eef6f5; position: sticky; top: 0; }
    td { white-space: pre-wrap; word-break: break-word; }
    @media (max-width: 960px) {
      main { grid-template-columns: 1fr; }
      aside { border-right: none; border-bottom: 1px solid var(--borda); }
    }
  </style>
</head>
<body>
  <header>
    <h1>witup-llm :: DuckDB</h1>
    <p>Use a barra lateral para visualizar tabelas e rode consultas SQL somente de leitura no banco analítico do experimento.</p>
  </header>
  <main>
    <aside>
      <div class="painel">
        <strong>Objetos do banco</strong>
        <div id="objetos">Carregando...</div>
      </div>
    </aside>
    <section>
      <div class="painel">
        <strong>Consulta SQL</strong>
        <p>Permitido: SELECT, WITH, SHOW, DESCRIBE, SUMMARIZE e PRAGMA.</p>
        <textarea id="consulta">SELECT * FROM vw_baselines_witup LIMIT 20;</textarea>
        <div class="acoes">
          <button id="botaoExecutar" onclick="executarConsulta()">Executar consulta</button>
          <span id="status" class="status">Pronto.</span>
        </div>
      </div>
      <div class="painel">
        <strong>Resultado</strong>
        <div id="resultado">Nenhuma consulta executada ainda.</div>
      </div>
    </section>
  </main>
  <script>
    async function carregarObjetos() {
      const resposta = await fetch('/api/objetos');
      const objetos = await resposta.json();
      const alvo = document.getElementById('objetos');
      alvo.textContent = '';
      objetos.forEach(objeto => {
        const botao = document.createElement('button');
        botao.className = 'objeto';
        const titulo = document.createElement('strong');
        titulo.textContent = objeto.name;
        const legenda = document.createElement('small');
        legenda.textContent = objeto.schema + ' :: ' + objeto.type;
        botao.appendChild(titulo);
        botao.appendChild(legenda);
        botao.onclick = () => visualizarObjeto(objeto.schema, objeto.name);
        alvo.appendChild(botao);
      });
    }

    async function visualizarObjeto(schema, name) {
      atualizarStatus('Carregando ' + schema + '.' + name + '...');
      const resposta = await fetch('/api/objeto?schema=' + encodeURIComponent(schema) + '&name=' + encodeURIComponent(name) + '&limit=100');
      await renderizarResposta(resposta);
    }

    async function executarConsulta() {
      const consulta = document.getElementById('consulta').value;
      atualizarStatus('Executando consulta...');
      const resposta = await fetch('/api/consulta', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({query: consulta})
      });
      await renderizarResposta(resposta);
    }

    function atualizarStatus(texto) {
      document.getElementById('status').textContent = texto;
    }

    async function renderizarResposta(resposta) {
      const alvo = document.getElementById('resultado');
      alvo.textContent = '';
      if (!resposta.ok) {
        const erro = document.createElement('div');
        erro.className = 'erro';
        erro.textContent = await resposta.text();
        alvo.appendChild(erro);
        atualizarStatus('Erro na consulta.');
        return;
      }

      const payload = await resposta.json();
      if (!payload.rows || payload.rows.length === 0) {
        const vazio = document.createElement('em');
        vazio.textContent = 'Consulta sem linhas de resultado.';
        alvo.appendChild(vazio);
        atualizarStatus('Consulta concluída sem linhas.');
        return;
      }

      const info = document.createElement('div');
      info.className = 'resultado-info';
      info.textContent = 'Linhas retornadas: ' + payload.rows.length +
        (payload.rows_truncated ? ' (resultado truncado no limite de ' + payload.row_limit + ' linhas).' : '.');
      alvo.appendChild(info);

      const grade = document.createElement('div');
      grade.className = 'grade';
      const tabela = document.createElement('table');
      const thead = document.createElement('thead');
      const headRow = document.createElement('tr');
      payload.columns.forEach(coluna => {
        const th = document.createElement('th');
        th.textContent = coluna;
        headRow.appendChild(th);
      });
      thead.appendChild(headRow);
      tabela.appendChild(thead);

      const tbody = document.createElement('tbody');
      payload.rows.forEach(linha => {
        const tr = document.createElement('tr');
        linha.forEach(valor => {
          const td = document.createElement('td');
          td.textContent = valor || '';
          tr.appendChild(td);
        });
        tbody.appendChild(tr);
      });
      tabela.appendChild(tbody);
      grade.appendChild(tabela);
      alvo.appendChild(grade);
      atualizarStatus('Consulta concluída.');
    }

    function aplicarConsultaInicialDaURL() {
      const params = new URLSearchParams(window.location.search);
      const consulta = params.get('consulta');
      if (consulta) {
        document.getElementById('consulta').value = consulta;
      }
    }

    aplicarConsultaInicialDaURL();
    carregarObjetos();
    executarConsulta();
  </script>
</body>
</html>`
