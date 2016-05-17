package br.com.controleFinanceiroPessoal.VO;

import java.sql.Date;

import br.com.controleFinanceiroPessoal.objetos.Categoria;
import br.com.controleFinanceiroPessoal.objetos.Conversor;
import br.com.controleFinanceiroPessoal.objetos.Movimentacao;
import br.com.controleFinanceiroPessoal.objetos.TipoMovimentacao;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class MovimentacaoVO {
	private int id;
	private TipoMovimentacao tipoMov;
	private String descricao;
	private String sDate;
	private Float fValor;
	private String sValor;
	private int idCategoria;
	private int idUsuario;
	private String nomeCategoria;
	private Date date;
	
	//Atribui as informaçõs do meu objeto para o VO
	public void convertToVO(Movimentacao movimentacao) {
		Conversor converter = new Conversor();
		this.id = movimentacao.getId();
		this.sValor = converter.convertFloatToString(movimentacao.getValor());
		this.sDate = converter.convertSqlDateToString(movimentacao.getDate());
		this.descricao = movimentacao.getDescricao();
		this.nomeCategoria = movimentacao.getCategoria().getDescricao();
		this.setDate(movimentacao.getDate());
		this.setTipoMov(movimentacao.getTipoMov());
	}

	//Atribui as informações para meu objeto
	public Movimentacao getMovimentacao() {
		Movimentacao movimentacao = new Movimentacao();
		
		movimentacao.setId(this.id);
		movimentacao.setValor(this.fValor);
		movimentacao.setDescricao(this.descricao);
		movimentacao.setTipoMov(tipoMov);
		
		Conversor converter = new Conversor();
		movimentacao.setDate(converter.convertStringToSqlDate(this.sDate));
		
		movimentacao.setUsuario( new Usuario());
		movimentacao.getUsuario().setId(this.idUsuario);
		
		movimentacao.setCategoria( new Categoria());
		movimentacao.getCategoria().setId(this.idCategoria);
		movimentacao.getCategoria().setDescricao(this.nomeCategoria);
		
		return movimentacao;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}*/

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public Float getfValor() {
		return fValor;
	}

	public void setfValor(Float fValor) {
		this.fValor = fValor;
	}

	public String getsValor() {
		return sValor;
	}

	public void setsValor(String sValor) {
		this.sValor = sValor;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TipoMovimentacao getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(TipoMovimentacao tipoMov) {
		this.tipoMov = tipoMov;
	}
	
	
	
	
}
