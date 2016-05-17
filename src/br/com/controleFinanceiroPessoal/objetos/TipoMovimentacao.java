package br.com.controleFinanceiroPessoal.objetos;

public enum TipoMovimentacao {
	RECEITA(0), DESPESA(1), OBJETIVO(2);
	
	public int tipo;
	
	private TipoMovimentacao(int valor){
		tipo = valor;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	public static TipoMovimentacao getPorTipo( int tipo ) {
		for (TipoMovimentacao tipoMovimentacao : TipoMovimentacao.values() ) {
			if ( tipoMovimentacao.getTipo() == tipo ) {
				return tipoMovimentacao;
			}
		}
		return null;
	}
}
