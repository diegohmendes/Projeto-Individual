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
import br.com.controleFinanceiroPessoal.objetos.TipoMovimentacao;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class JDBCMovimentacaoDAO {
	
	private Connection conexao;
	
	public JDBCMovimentacaoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean cadastrarMovimentacao(Movimentacao movimentacao) {
		
		String comando = "insert into movimentacao (usuario_id_usuario, categoria_id_categoria, valor_movimentacao, data_movimentacao, "
				+ "descricao_movimentacao, tipo_movimentacao) values(?,?,?,?,?,?)";
		
		PreparedStatement p;		

		try{
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			
			p.setInt(1, movimentacao.getUsuario().getId());
			p.setInt(2, movimentacao.getCategoria().getId());
			p.setFloat(3, movimentacao.getValor());	
			p.setDate(4, movimentacao.getDate());			
			p.setString(5, movimentacao.getDescricao());
			p.setInt(6, movimentacao.getTipoMov().getTipo());	
			
			p.execute();
			
			Categoria categoria = new JDBCCategoriaDAO(conexao).buscarCategoriaPorId(movimentacao.getCategoria().getId(), movimentacao.getUsuario().getId());
			
			movimentacao.setCategoria( categoria );
			
			ResultSet rs = p.getGeneratedKeys();
			
			while(rs.next()) {
				movimentacao.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public List<Movimentacao> buscarMovimentacao(int tipo, int idUser) {
		
		List<Movimentacao> listaDeMov = new ArrayList<Movimentacao>();
		Movimentacao movimentacao = null;

		String comando = "select * from movimentacao where usuario_id_usuario = ? and tipo_movimentacao = ? ";
		
		if(tipo == 1) {
			comando += " or tipo_movimentacao = 2";
		}
		
		PreparedStatement p;

		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idUser);
			p.setInt(2, tipo);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				
				movimentacao = new Movimentacao();
				int idReceita = rs.getInt("id_movimentacao");
				int idUsuario = rs.getInt("usuario_id_usuario");
				int idCategoria = rs.getInt("categoria_id_categoria");
				float valorMovimentacao = rs.getFloat("valor_movimentacao");
				Date dataMovimentacao = rs.getDate("data_movimentacao");
				String descricaoMovimentacao = rs.getString("descricao_movimentacao");
				int idMovimentacao = rs.getInt("id_movimentacao");
				int tipoMov = rs.getInt("tipo_movimentacao");
				
				Categoria categoria = new JDBCCategoriaDAO(conexao).buscarCategoriaPorId(idCategoria, idUsuario);
				
				movimentacao.setUsuario( new Usuario() );
				movimentacao.setCategoria( categoria );
				movimentacao.setId(idReceita);
				movimentacao.getUsuario().setId(idUsuario);
				movimentacao.setTipoMov(TipoMovimentacao.getPorTipo(tipoMov));
				movimentacao.setDate(dataMovimentacao);
				movimentacao.setDescricao(descricaoMovimentacao);
				movimentacao.setId(idMovimentacao);
				movimentacao.setValor(valorMovimentacao);
				listaDeMov.add(movimentacao);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDeMov;
	}

	public boolean editarMovimentacao(Movimentacao movimentacao) {
		String comando = "update movimentacao set usuario_id_usuario = ?, categoria_id_categoria = ?, valor_movimentacao = ?, "
				+ "data_movimentacao = ?, descricao_movimentacao = ?, tipo_movimentacao = ? where id_movimentacao = ?";
		
		new Categoria();
		new Usuario();
		
		PreparedStatement p;
		try {
			
			p = this.conexao.prepareStatement(comando);
			
			p.setInt(1,movimentacao.getUsuario().getId());
			p.setInt(2, movimentacao.getCategoria().getId());
			p.setFloat(3, movimentacao.getValor());
			p.setDate(4, movimentacao.getDate());
			p.setString(5, movimentacao.getDescricao());
			p.setInt(6, movimentacao.getTipoMov().getTipo());
			p.setInt(7, movimentacao.getId());
			
			p.execute();
			
			int idCategoria = movimentacao.getCategoria().getId();
			Categoria categoria = new JDBCCategoriaDAO(conexao).buscarCategoriaPorId(idCategoria, movimentacao.getUsuario().getId());
			
			movimentacao.setCategoria(categoria);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removerMovimentacao(int id, int idUsuario) {
	
			String comando = "delete from movimentacao where id_movimentacao = ? and usuario_id_usuario = ?";
			
			PreparedStatement p;
			
			try {
				p = this.conexao.prepareStatement(comando);
				p.setInt(1, id);
				p.setInt(2, idUsuario);
				
				p.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
	public List<Movimentacao> buscarCincoUltimasMov(int tipo, int idUser) {
				
			List<Movimentacao> listaDeMov = new ArrayList<Movimentacao>();
			Movimentacao movimentacao = null;
	
			String comando = "select * from movimentacao where tipo_movimentacao = ? and usuario_id_usuario = ? "
					+ "order by data_movimentacao desc limit 5";
			
			PreparedStatement p;
			try{
				p = this.conexao.prepareStatement(comando);
				p.setInt(1, tipo);
				p.setInt(2, idUser);
				ResultSet rs = p.executeQuery();
				while (rs.next()) {
					
					movimentacao = new Movimentacao();
					int idReceita = rs.getInt("id_movimentacao");
					int idUsuario = rs.getInt("usuario_id_usuario");
					int idCategoria = rs.getInt("categoria_id_categoria");
					float valorMovimentacao = rs.getFloat("valor_movimentacao");
					Date dataMovimentacao = rs.getDate("data_movimentacao");
					String descricaoMovimentacao = rs.getString("descricao_movimentacao");
					int idMovimentacao = rs.getInt("id_movimentacao");
					
					Categoria categoria = new JDBCCategoriaDAO(conexao).buscarCategoriaPorId(idCategoria, idUsuario);
					
					movimentacao.setUsuario( new Usuario() );
					movimentacao.setCategoria( categoria );
					
					movimentacao.setId(idReceita);
					movimentacao.getUsuario().setId(idUsuario);
					
					movimentacao.setDescricao(descricaoMovimentacao);
					movimentacao.setId(idMovimentacao);
					movimentacao.setDate(dataMovimentacao);
					movimentacao.setValor(valorMovimentacao);
					
					listaDeMov.add(movimentacao);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return listaDeMov;
		}
	
	public Float buscarSaldoTotal(int userId) {
		
		String comando = "select"
				+ " (select ifnull(sum(valor_movimentacao),0) from movimentacao where tipo_movimentacao = 0 and usuario_id_usuario = ?)-"
				+ "(select ifnull(sum(valor_movimentacao),0) from movimentacao where tipo_movimentacao = 1 and usuario_id_usuario = ?) as saldo "
				+ "from movimentacao m where usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		try {
			
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, userId);
			p.setInt(2, userId);
			p.setInt(3, userId);
			ResultSet rs = p.executeQuery();
			
			while(rs.next()){
				float valorTotal = rs.getFloat("saldo");
				return valorTotal;
			}
			
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return 0f;
	}

	public Movimentacao buscarMovimentacaoPorId(int id, int idUser) {
		String comando = "select * from movimentacao where id_movimentacao = ? and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		Movimentacao despesa = new Movimentacao();
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.setInt(2, idUser);
			
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				int idMovimentacao = rs.getInt("id_movimentacao");
				String descricao = rs.getString("descricao_movimentacao");
				int idCategoria = rs.getInt("categoria_id_categoria");
				float valorMovimentacao = rs.getFloat("valor_movimentacao");
				int tipoMovimentacao = rs.getInt("tipo_Movimentacao");
				Date dataMovimentacao = rs.getDate("data_movimentacao");
				
				despesa.setId(idMovimentacao);
				despesa.setDescricao(descricao);
				despesa.setCategoria( new Categoria() );
				despesa.getCategoria().setId(idCategoria);
				despesa.setTipoMov(TipoMovimentacao.getPorTipo(tipoMovimentacao));
				despesa.setDate(dataMovimentacao);
				despesa.setValor(valorMovimentacao);
				//despesa.setsDate(formatToFront.format(new Date(dataMovimentacao.getTime())));

			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return despesa;
	}
	
}

