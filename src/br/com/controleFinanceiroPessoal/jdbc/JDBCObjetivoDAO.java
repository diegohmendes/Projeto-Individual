package br.com.controleFinanceiroPessoal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Categoria;
import br.com.controleFinanceiroPessoal.objetos.Movimentacao;
import br.com.controleFinanceiroPessoal.objetos.Objetivo;
import br.com.controleFinanceiroPessoal.objetos.TipoMovimentacao;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class JDBCObjetivoDAO {

	private Connection conexao;
	
	public JDBCObjetivoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Objetivo> buscarObjetivos(Objetivo objetivo) {
		
		
		List<Objetivo> listaDeObjetivos = new ArrayList<Objetivo>();
		
		String comando = "select * from objetivo where objetivo.usuario_id_usuario = ?";
		
		try {
			PreparedStatement p;
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, objetivo.getUsuario().getId());
					
			ResultSet rs = p.executeQuery();
				
			while (rs.next()) {
				
				String descricao = rs.getString("descricao_objetivo");
				Float valor = rs.getFloat("valor_objetivo");
				Float valorCorrente = rs.getFloat("valorCorrente_objetivo");
				int idObjetivo = rs.getInt("id_objetivo");
				int status = rs.getInt("status_objetivo");
						
				objetivo.setValor(valor);
				objetivo.setDescricao(descricao);
				objetivo.setValor(valor);
				objetivo.setValorCorrente(valorCorrente);
				objetivo.setId(idObjetivo);
				objetivo.setStatus(status);
				
				listaDeObjetivos.add(objetivo);
				
				objetivo = new Objetivo();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaDeObjetivos;
			
	}

	public boolean cadastrarObjetivo(Objetivo objetivo) {
		String comando = "insert into objetivo (objetivo.usuario_id_usuario, descricao_objetivo, valor_objetivo, id_objetivo) "
				+ "values (?,?,?,?)";
		
		PreparedStatement p;
				
		try {
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, objetivo.getUsuario().getId());
			p.setString(2, objetivo.getDescricao());
			p.setFloat(3, objetivo.getValor());
			p.setInt(4, objetivo.getId());

			p.execute();
			
			ResultSet rs = p.getGeneratedKeys();
			
			while(rs.next()) {
				objetivo.setId(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public void adicionarValorObjetivo(Objetivo objetivo) {
		//String comando = "update objetivo set valorCorrente_objetivo = (?) where id_objetivo = ?";
		/*System.out.println("Recebi o objeto JDBC: "+objetivo);
		recuperarValorObjetivo(objetivo);
		System.out.println("Recebi o recuperar: "+objetivo);*/
		
		String descricao = recuperarDescricao(objetivo);
		Movimentacao despesa = new Movimentacao();
		
		JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(this.conexao);
		
		despesa.setTipoMov(TipoMovimentacao.OBJETIVO);
		despesa.setDate( new Date(new java.util.Date().getTime() ));
		despesa.setDescricao(descricao);
		despesa.setValor(objetivo.getValorCorrente());
		despesa.setCategoria( new Categoria() );
		despesa.getCategoria().setId(objetivo.getId());
		despesa.setUsuario( new Usuario() );
		despesa.getUsuario().setId(objetivo.getUsuario().getId());
		
		jdbcMovimentacao.cadastrarMovimentacao(despesa);
		
		String comando = "update objetivo set "
				+ "valorCorrente_objetivo = ? + valorCorrente_objetivo "
				+ "where id_objetivo = ?";
		
		PreparedStatement p;
		
		try {
			
			p = this.conexao.prepareStatement(comando);
			p.setFloat(1, objetivo.getValorCorrente());
			p.setInt(2, objetivo.getId());
			p.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private String recuperarDescricao(Objetivo objetivo) {
		String comando = "select descricao_objetivo from objetivo where id_objetivo = ? and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		String descricao = null;

		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, objetivo.getId());
			p.setInt(2, objetivo.getUsuario().getId());
			
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				descricao = rs.getString("descricao_objetivo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return descricao;
	}

	public Objetivo recuperarValorObjetivo(Objetivo objetivo) {
		String comando = "select * from objetivo where id_objetivo = ? and usuario_id_usuario = ?";

		PreparedStatement p;
		
		try {	
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, objetivo.getId());
			p.setInt(2, objetivo.getUsuario().getId());
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_objetivo");
				String descricao = rs.getString("descricao_objetivo");
				Float valor = rs.getFloat("valor_objetivo");
				Float valorCorrente = rs.getFloat("valorCorrente_objetivo");
				int status = rs.getInt("status_objetivo");
				
				objetivo.setDescricao(descricao);
				objetivo.setValor(valor);
				objetivo.setValorCorrente(valorCorrente);
				objetivo.setId(id);	
				objetivo.setStatus(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objetivo;
	}

	public boolean removerObjetivo(Objetivo objetivo) {
		String comando = "delete from objetivo where id_objetivo = ? and usuario_id_usuario = ?";
		System.out.println("removerObjetivo: "+objetivo);
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, objetivo.getId());
			p.setInt(2, objetivo.getUsuario().getId());
			p.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Float buscarValorTotal(int idUsuario) {
		
		String comando = "select sum(valorCorrente_objetivo) as valorTotal from objetivo where usuario_id_usuario = ?";
		
		PreparedStatement p;
		Float valorTotal = 0f;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idUsuario);
			
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				valorTotal = rs.getFloat("valorTotal");	
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valorTotal;
	}
	
	public void removerObjetivoDespesa(Objetivo objetivo) {
		System.out.println("RemoverObjetivoDespesa: "+objetivo);
		String comando = "delete from movimentacao where descricao_movimentacao = ? and tipo_movimentacao = 2 and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, objetivo.getDescricao());
			p.setInt(2, objetivo.getUsuario().getId());
			
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Objetivo verificarObjetivo(int id) {
		
		String comando = "select * from objetivo where id_objetivo = ?";
		
		PreparedStatement p;
		Objetivo objetivo = new Objetivo();
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			
			float valor = 0;
			String descricao;
			
			ResultSet rs = p.executeQuery();
				while(rs.next()) {
					valor = rs.getFloat("valorCorrente_objetivo");
					descricao = rs.getString("descricao_objetivo");
					
					objetivo.setDescricao(descricao);
					objetivo.setValorCorrente(valor);
				}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objetivo;
	}
	
	public void mudarStatusObjetivo(Objetivo objetivo) {
		String comando = "update objetivo set status_objetivo = 1 where id_objetivo = ? and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, objetivo.getId());
			p.setInt(2, objetivo.getUsuario().getId());
			
			p.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}