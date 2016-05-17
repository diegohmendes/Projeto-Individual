package br.com.controleFinanceiroPessoal.jdbcinterface;

import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Movimentacao;

public interface MovimentacaoDAO {
	
	public boolean cadastrarMovimentacao(Movimentacao movimentacao);
	public List<Movimentacao> buscarMovimentacao(int tipo, int idUser);
	public boolean editarMovimentacao(Movimentacao movimentacao, int idUsuario);
	public boolean removerMovimentacao(int id, int idUsuario);
	public List<Movimentacao> buscarCincoUltimasMov(int tipo, int idUser);
	public Float buscarSaldoTotal(int idUser);
	public Movimentacao buscarMovimentacaoPorId(int id, int idUser);
	
}
