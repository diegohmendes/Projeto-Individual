package br.com.controleFinanceiroPessoal.objetos;

import java.sql.Date;

public class Movimentacao {

	private int id;
	private Float valor;
	private Date date;
	private String descricao;
	//private int tipo;
	private TipoMovimentacao tipoMov;
	private Categoria categoria;
	private Usuario usuario;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valorMovimentacao) {
		this.valor = valorMovimentacao;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/*public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}*/
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public TipoMovimentacao getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(TipoMovimentacao tipoMov) {
		this.tipoMov = tipoMov;
	}
	
}
