(() => {
  const mount = document.getElementById('catalogo-testes');
  if (!mount) return;
  const root = new URL('../../testes-gerados/', window.location.href);
  const node = (tag, text) => { const n = document.createElement(tag); if (text !== undefined) n.textContent = text; return n; };
  const selector = (label, choices) => {
    const wrap = node('label'); wrap.textContent = label + ' ';
    const select = node('select');
    choices.forEach(([value, title]) => { const o = node('option', title); o.value = value; select.append(o); });
    wrap.append(select); return [wrap, select];
  };
  fetch(new URL('catalogo.json', root)).then(r => { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); }).then(data => {
    mount.replaceChildren();
    const controls = node('div'); controls.className = 'catalogo-controles';
    const searchLabel = node('label', 'Buscar método ou classe '); const search = node('input'); search.type = 'search'; search.placeholder = 'Ex.: requireNonNull'; searchLabel.append(search);
    const [modelLabel, model] = selector('Modelo', [['','Todos'],['nano','GPT-4.1 nano'],['mini','GPT-4.1 mini'],['haiku','Claude Haiku 5.5']]);
    const [armLabel, arm] = selector('Condição', [['','Todas'],['direct-tests','Sem WIT'],['wit-context','Com WIT']]);
    const [stateLabel, state] = selector('Resultado histórico', [['','Todos'],['aprovado','Aprovado'],['compilacao','Erro de compilação'],['outro','Outro desfecho']]);
    controls.append(searchLabel, modelLabel, armLabel, stateLabel); mount.append(controls);
    const summary = node('p'); const list = node('div'); const navigation = node('div'); navigation.className = 'catalogo-paginacao';
    const previous = node('button', 'Anterior'); const pageText = node('span'); const next = node('button', 'Próxima'); previous.type = next.type = 'button'; navigation.append(previous, pageText, next); mount.append(summary, list, navigation);
    let page = 0; const perPage = 25;
    const render = () => {
      const query = search.value.trim().toLocaleLowerCase('pt-BR');
      const filtered = data.filter(x => (!model.value || x.modelo === model.value) && (!arm.value || x.cenario === arm.value) && (!query || x.alvo.toLocaleLowerCase('pt-BR').includes(query)) && (!state.value || (state.value === 'aprovado' ? x.aprovado_historico : state.value === 'compilacao' ? x.erro_compilacao_historico : !x.aprovado_historico && !x.erro_compilacao_historico)));
      const pages = Math.max(1, Math.ceil(filtered.length / perPage)); page = Math.min(page, pages - 1);
      summary.textContent = `${filtered.length.toLocaleString('pt-BR')} registros encontrados; ${data.length.toLocaleString('pt-BR')} no catálogo.`;
      list.replaceChildren();
      for (const item of filtered.slice(page * perPage, (page + 1) * perPage)) {
        const card = node('article'); card.className = 'catalogo-item';
        card.append(node('strong', item.alvo));
        const condition = item.cenario === 'wit-context' ? 'Com WIT' : 'Sem WIT';
        const names = {nano:'GPT-4.1 nano', mini:'GPT-4.1 mini', haiku:'Claude Haiku 5.5'};
        card.append(node('p', `${names[item.modelo]} · ${condition} · resultado histórico: ${item.estado_historico}${item.resposta_malformada ? ' · resposta com estrutura de arquivos malformada' : ''}`));
        const link = node('a', 'Ver código Java'); link.href = new URL(item.arquivo, root).href; link.target = '_blank'; link.rel = 'noopener'; card.append(link);
        const details = node('details'); details.append(node('summary','Proveniência')); details.append(node('p',`Arquivo original: ${item.caminho_original}`), node('p',`SHA-256: ${item.sha256}`)); card.append(details); list.append(card);
      }
      previous.disabled = page === 0; next.disabled = page >= pages - 1; pageText.textContent = ` Página ${page + 1} de ${pages} `;
    };
    [search, model, arm, state].forEach(el => el.addEventListener('input', () => { page = 0; render(); }));
    previous.addEventListener('click', () => { page--; render(); }); next.addEventListener('click', () => { page++; render(); }); render();
  }).catch(() => { mount.textContent = 'Não foi possível carregar o catálogo. Abra o site por um servidor HTTP ou consulte o arquivo JSON no repositório.'; });
})();
