package br.com.controleFinanceiroPessoal.jdbcinterface;

import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Categoria;

public interface CategoriaDAO {
	
	public boolean cadastrarCategoria(Categoria categoria);
	public List<Categoria> buscarCategoria(String categoriaParam, int idUser);
	public boolean removerCategoria(int id, int userId);
	public Categoria buscarCategoriaPorId(int id, int idUser);
	public boolean editarCategoria(Categoria categoria);

}
