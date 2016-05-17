package br.com.controleFinanceiroPessoal.jdbcinterface;

import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Objetivo;

public interface ObjetivoDAO {
	
	public void adicionarValorObjetivo(int idObjetivo, Float valorCorrente, int idUsuario);
	public List<Objetivo> buscarObjetivos(int idObjetivo);
	public boolean cadastrarObjetivo(Objetivo objetivo);
	public Objetivo recuperarValorObjetivo(int idObjetivo, int idUsuario);
	public boolean removerObjetivo(int id, int idUsuario);
	public Float buscarValorTotal(int idUsuario);
	public void removerObjetivoDespesa(int idUsuario, Objetivo objetivo);
	public Objetivo verificarObjetivo(int id);
	public void mudarStatusObjetivo(int idObjetivo, int idUsuario);
	

}
