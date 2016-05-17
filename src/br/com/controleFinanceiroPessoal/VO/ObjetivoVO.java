package br.com.controleFinanceiroPessoal.VO;

import br.com.controleFinanceiroPessoal.objetos.Conversor;
import br.com.controleFinanceiroPessoal.objetos.Objetivo;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class ObjetivoVO {
	private float fValorPercent;
	private float fValorCorrente;
	private String sValorCorrente;
	private String descricao;
	private float fValor;
	private String sValor;
	private int idUsuario;
	private int status;
	private int id;
	
	//Atribui as informaçõs do meu objeto para o VO
		public void convertToVO(Objetivo objetivo) {
			Conversor converter = new Conversor();
			this.sValorCorrente = converter.convertFloatToString(objetivo.getValorCorrente());
			this.descricao = objetivo.getDescricao();
			this.sValor = converter.convertFloatToString(objetivo.getValor());
			this.status = objetivo.getStatus();
			this.id = objetivo.getId();
		}

		//Atribui as informações para meu objeto
		public Objetivo getObjetivo() {
			Objetivo objetivo = new Objetivo();
			objetivo.setValor(this.fValor);
			objetivo.setDescricao(this.descricao);
			objetivo.setValorCorrente(this.fValorCorrente);
			objetivo.setUsuario(new Usuario());
			objetivo.getUsuario().setId(this.idUsuario);
			objetivo.setId(this.id);
			return objetivo;
		}

		public float getfValorCorrente() {
			return fValorCorrente;
		}

		public void setfValorCorrente(float fValorCorrente) {
			this.fValorCorrente = fValorCorrente;
		}

		public String getsValorCorrente() {
			return sValorCorrente;
		}

		public void setsValorCorrente(String sValorCorrente) {
			this.sValorCorrente = sValorCorrente;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public float getfValor() {
			return fValor;
		}

		public void setfValor(float fValor) {
			this.fValor = fValor;
		}

		public String getsValor() {
			return sValor;
		}

		public void setsValor(String sValor) {
			this.sValor = sValor;
		}

		public int getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(int idUsuario) {
			this.idUsuario = idUsuario;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public float getfValorPercent() {
			return fValorPercent;
		}

		public void setfValorPercent(float fValorPercent) {
			this.fValorPercent = fValorPercent;
		}

		@Override
		public String toString() {
			return "ObjetivoVO [fValorPercent=" + fValorPercent + ", fValorCorrente=" + fValorCorrente
					+ ", sValorCorrente=" + sValorCorrente + ", descricao=" + descricao + ", fValor=" + fValor
					+ ", sValor=" + sValor + ", idUsuario=" + idUsuario + ", status=" + status + ", id=" + id + "]";
		}
	
}


