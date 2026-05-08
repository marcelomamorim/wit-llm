package aplicacao

import (
	"strconv"
	"strings"

	"github.com/marceloamorim/witup-llm/internal/dominio"
)

func chaveCacheCatalogoSegundaFase(projeto dominio.ConfigProjeto, maximoMetodos int) string {
	partes := []string{
		projeto.Raiz,
		strings.Join(projeto.Include, "\x00"),
		strings.Join(projeto.Exclude, "\x00"),
		projeto.TestFramework,
		projeto.OverviewFile,
	}
	return strings.Join(partes, "\x1f") + "\x1f" + strconv.Itoa(maximoMetodos)
}

func (s *Servico) carregarCatalogoSegundaFaseComCache(cfgProjeto *dominio.ConfigAplicacao, cache map[string][]dominio.DescritorMetodo) ([]dominio.DescritorMetodo, error) {
	chave := chaveCacheCatalogoSegundaFase(cfgProjeto.Projeto, cfgProjeto.Fluxo.MaximoMetodos)
	if metodos, ok := cache[chave]; ok {
		return append([]dominio.DescritorMetodo{}, metodos...), nil
	}
	catalogo := s.catalogFactory.NovoCatalogo(cfgProjeto.Projeto)
	metodos, _, err := carregarCatalogoProjeto(catalogo, cfgProjeto.Fluxo.MaximoMetodos)
	if err != nil {
		return nil, err
	}
	cache[chave] = append([]dominio.DescritorMetodo{}, metodos...)
	return metodos, nil
}
