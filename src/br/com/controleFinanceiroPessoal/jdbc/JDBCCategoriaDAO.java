package br.com.controleFinanceiroPessoal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.controleFinanceiroPessoal.objetos.Categoria;

public class JDBCCategoriaDAO {
	
	private Connection conexao;
	
	public JDBCCategoriaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean cadastrarCategoria(Categoria categoria) {
		
		String comando = "insert into categoria (descricao_categoria, categoria.usuario_id_usuario) values (?,?)";
				
		PreparedStatement p;

		try{
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, categoria.getDescricao());
			p.setInt(2, categoria.getUsuario().getId());
			
			p.execute();
			//parei aqui problema na inclusão
			ResultSet rs = p.getGeneratedKeys();
			
			while (rs.next()) {
				categoria.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Categoria> buscarCategoria(String categoriaParam, int idUser) {
		
		List<Categoria> listaDeCategoria = new ArrayList<Categoria>();
		Categoria categoria = null;
		
		String comando = "select * from categoria where usuario_id_usuario = ?";
		PreparedStatement p;
		
		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idUser);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				categoria = new Categoria();
				int idCategoria = rs.getInt("id_categoria");
				String descricao = rs.getString("descricao_categoria");
				
				categoria.setId(idCategoria);
				categoria.setDescricao(descricao);
				listaDeCategoria.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDeCategoria;
	}

	public boolean removerCategoria(int id, int idCategoria) {
		String comando = "delete from categoria where id_categoria = ? and usuario_id_usuario = ?";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.setInt(2, idCategoria);
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean editarCategoria(Categoria categoria) {
		String comando = "update categoria set descricao_categoria = ? where id_categoria = ? and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, categoria.getDescricao());
			p.setInt(2, categoria.getId());
			p.setInt(3, categoria.getUsuario().getId());
			
			p.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Categoria buscarCategoriaPorId(int idCategoria, int idUsuario) {
		
		String comando = "select * from categoria where id_categoria = ? and usuario_id_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idCategoria);
			p.setInt(2, idUsuario);
			
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id_categoria");
				String descricao = rs.getString("descricao_categoria");
				
				Categoria categoria = new Categoria();
				
				categoria.setId(id);
				categoria.setDescricao(descricao);
				return categoria;
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
