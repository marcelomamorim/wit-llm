# Catálogo de testes gerados

Este catálogo reúne os arquivos Java dos **3.232 métodos da coorte comum** aos três modelos, nos cenários com e sem WIT: 19.392 arquivos. O conteúdo dos arquivos foi conferido por SHA-256 contra os manifestos de origem; o nome no repositório é um identificador, e o caminho original consta no índice.

!!! warning "Escopo e interpretação"
    Esta é a coorte comum usada na análise exploratória, não o conjunto completo de todas as rodadas. Os estados mostrados são das avaliações **históricas**, em protocolos que diferem entre modelos; eles não representam a reexecução atualmente em andamento. Um teste aprovado não garante um oráculo correto ou que o alvo tenha sido exercitado. No nano há uma resposta com estrutura de arquivos malformada, identificada no catálogo; o Java exportado é o primeiro arquivo daquela resposta.

Use os filtros para localizar um método, escolher um modelo e comparar cenários. O botão **Ver código** abre o arquivo preservado no site. O índice completo, incluindo hashes, caminhos originais e estados históricos, também está disponível em [JSON](../testes-gerados/catalogo.json).

<div id="catalogo-testes" aria-live="polite">
  <p>Carregando catálogo…</p>
</div>

<script src="../../testes-gerados/catalogo.js" defer></script>
