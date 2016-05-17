package br.com.controleFinanceiroPessoal.objetos;

public class Objetivo extends Categoria {
	
	private Float valor;
	private Float valorCorrente;
	private int status;
	
	public Float getValorCorrente() {
		return valorCorrente;
	}
	public void setValorCorrente(Float valorCorrente) {
		this.valorCorrente = valorCorrente;
	}

	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Objetivo [valor=" + valor + ", valorCorrente=" + valorCorrente + ", status=" + status
				+ ", getDescricao()=" + getDescricao() + ", getId()=" + getId() + "]";
	}
	
	
	
	

}
