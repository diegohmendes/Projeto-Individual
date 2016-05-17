package br.com.controleFinanceiroPessoal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.controleFinanceiroPessoal.jdbcinterface.UsuarioDAO;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO {
	
	private Connection conexao;
	
	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean cadastrarUsuario(Usuario usuario) {
		
		String comando = "insert into usuario (nome_usuario, senha_usuario, email_usuario) values (?,?,?)";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, usuario.getNome());
			p.setString(2, usuario.getSenha());
			p.setString(3, usuario.getEmail());
			
			p.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public boolean validarUsuario(Usuario usuario) {
		
		String comando = "select email_usuario, senha_usuario from usuario where email_usuario = ? and senha_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, usuario.getId());
			p.setString(1, usuario.getEmail());
			p.setString(2, usuario.getSenha());
			
			ResultSet rs = p.executeQuery();
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public Usuario recuperarDadosUsuario(Usuario usuario) {
		
		String comando = "select * from usuario where email_usuario = ? and senha_usuario = ?";
		
		PreparedStatement p;
		
		try {
		p = this.conexao.prepareStatement(comando);
		p.setString(1, usuario.getEmail());
		p.setString(2, usuario.getSenha());
		ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				int idUsuario = rs.getInt("id_usuario");
				String nomeUsuario = rs.getString("nome_usuario");
				String senhaUsuario = rs.getString("senha_usuario");
				String emailUsuario = rs.getString("email_usuario");
				
				usuario.setId(idUsuario);
				usuario.setNome(nomeUsuario);
				usuario.setSenha(senhaUsuario);
				usuario.setEmail(emailUsuario);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public boolean validarEmailUsuario(String email) {
		
		String comando = "select * from usuario where email_usuario = ?";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, email);
			
			ResultSet rs = p.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return false;
	}

}
