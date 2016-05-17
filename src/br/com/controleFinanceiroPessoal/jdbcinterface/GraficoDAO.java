package br.com.controleFinanceiroPessoal.jdbcinterface;

import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Grafico;

public interface GraficoDAO {
	
	public List gerarGraficoDespesa(List list, Grafico grafico);
	public List gerarGraficoReceita(List list, Grafico grafico);
	public List gerarGraficoTotal(List list, Grafico grafico);

}
