package br.com.controleFinanceiroPessoal.VO;

import br.com.controleFinanceiroPessoal.objetos.Categoria;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class CategoriaVO {

	private int id;
	private String descricao;
	private int idUsuario;
	
	//Atribui as informaçõs do meu objeto para o VO
	public void convertToVO(Categoria categoria) {
		this.id = categoria.getId();
		this.descricao = categoria.getDescricao();
	}

	//Atribui as informações para meu objeto
	public Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setId(this.id);
		categoria.setDescricao(this.descricao);
		categoria.setUsuario(new Usuario());
		categoria.getUsuario().setId(this.idUsuario);

		return categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "CategoriaVO [id=" + id + ", descricao=" + descricao + ", idUsuario=" + idUsuario + "]";
	}
	
	
}
