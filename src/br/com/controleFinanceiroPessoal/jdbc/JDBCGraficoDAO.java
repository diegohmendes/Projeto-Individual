package br.com.controleFinanceiroPessoal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.controleFinanceiroPessoal.jdbcinterface.GraficoDAO;
import br.com.controleFinanceiroPessoal.objetos.Grafico;

public class JDBCGraficoDAO implements GraficoDAO {
	
	private Connection conexao;

	public JDBCGraficoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	
	public List gerarGraficoTotal (List totalMov, Grafico grafico) {
		
		String comando = "select sum(valor_movimentacao) a from movimentacao "
						+ "where tipo_movimentacao = 0 "
						+ "and data_movimentacao between ? and ? "
						+ "and usuario_id_usuario = ? "
						+ "union all "
						+ "select sum(valor_movimentacao) a from movimentacao "
						+ "where tipo_movimentacao = 1 "
						+ "and data_movimentacao between ? and ? "
						+ "and usuario_id_usuario = ?"; 
		
		PreparedStatement p;
		
		try{
			p = this.conexao.prepareStatement(comando);
			
			p.setDate(1, grafico.getDataInicial());
			p.setDate(2, grafico.getDataFinal());
			p.setInt(3, grafico.getUsuario().getId());
			
			p.setDate(4, grafico.getDataInicial());
			p.setDate(5, grafico.getDataFinal());
			p.setInt(6, grafico.getUsuario().getId());
			
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				Integer movimentacao = rs.getInt("a");
				totalMov.add(movimentacao);
			}
		}	
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalMov;

	}


	public List gerarGraficoReceita(List catReceita, Grafico grafico) {
		
		
		String comando = "select categoria.descricao_categoria as nomeCategoria, sum(movimentacao.valor_movimentacao) as valorTotal "
					+ "from movimentacao "
					+ "inner join categoria on categoria.id_categoria = movimentacao.categoria_id_categoria "
					+ "where movimentacao.data_movimentacao between ? and ? "
					+ "and movimentacao.usuario_id_usuario = ? "
					+ "and movimentacao.tipo_movimentacao = ? "
					+ "group by categoria.descricao_categoria";
				
		PreparedStatement p;
		
		
		try{
			p = this.conexao.prepareStatement(comando);
			p.setDate(1, grafico.getDataInicial());
			p.setDate(2, grafico.getDataFinal());
			p.setInt(3, grafico.getUsuario().getId());
			p.setInt(4, 0);
			
			ResultSet rs = p.executeQuery();
			
			List<Object> lista = new ArrayList<Object>();
			lista.add("Titulo");
			lista.add("valor");
			
			catReceita.add(lista);

			
			while(rs.next()) {
				String nomeCategoria = rs.getString("nomeCategoria");
				Float valorTotal = rs.getFloat("valorTotal");
				lista = new ArrayList<Object>();
				lista.add(nomeCategoria);
				lista.add(valorTotal);
				
				catReceita.add(lista);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return catReceita;
	}
	
	
	public List gerarGraficoDespesa(List catReceita, Grafico grafico) {
		
		
		String comando = "select categoria.descricao_categoria as nomeCategoria, sum(movimentacao.valor_movimentacao) as valorTotal "
					+ "from movimentacao "
					+ "inner join categoria on categoria.id_categoria = movimentacao.categoria_id_categoria "
					+ "where movimentacao.data_movimentacao between ? and ? "
					+ "and movimentacao.usuario_id_usuario = ? "
					+ "and movimentacao.tipo_movimentacao = ? "
					+ "group by categoria.descricao_categoria";
				
		PreparedStatement p;
		
		
		try{
			p = this.conexao.prepareStatement(comando);
			p.setDate(1, grafico.getDataInicial());
			p.setDate(2, grafico.getDataFinal());
			p.setInt(3, grafico.getUsuario().getId());
			p.setInt(4, 1);
			
			ResultSet rs = p.executeQuery();
			
			List<Object> lista = new ArrayList<Object>();
			lista.add("Titulo");
			lista.add("valor");
			
			catReceita.add(lista);

			
			while(rs.next()) {
				String nomeCategoria = rs.getString("nomeCategoria");
				Float valorTotal = rs.getFloat("valorTotal");
				lista = new ArrayList<Object>();
				lista.add(nomeCategoria);
				lista.add(valorTotal);
				
				catReceita.add(lista);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return catReceita;
	}
}