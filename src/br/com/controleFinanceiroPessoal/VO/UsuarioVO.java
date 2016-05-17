package br.com.controleFinanceiroPessoal.VO;

import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class UsuarioVO {
	
	private int id;
	private String email;
	private String nome;
	private String senha;
	
	//Atribui as informaçõs do meu objeto para o VO
	public void convertToVO(Usuario usuario) {
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}

	//Atribui as informações para meu objeto
	public Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(this.id);
		usuario.setEmail(this.email);
		usuario.setNome(this.nome);
		usuario.setSenha(this.senha);

		return usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
