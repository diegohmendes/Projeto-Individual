package br.com.controleFinanceiroPessoal.jdbcinterface;

import br.com.controleFinanceiroPessoal.objetos.Usuario;

public interface UsuarioDAO {
	
	public boolean cadastrarUsuario(Usuario usuario);
	public boolean validarUsuario(Usuario usuario);
	public Usuario recuperarDadosUsuario(Usuario usuario);
	public boolean validarEmailUsuario(String email);
}
