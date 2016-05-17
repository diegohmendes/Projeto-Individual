package br.com.controleFinanceiroPessoal.rest;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.controleFinanceiroPessoal.VO.UsuarioVO;
import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCUsuarioDAO;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

@Path("/usuarioRest")
public class UsuarioRest extends UtilRest {

	@POST
	@Path("/cadastrarUsuario")
	@Consumes("application/*")
	public String cadastrarUsuario(String usuarioParam) {
		try{
			UsuarioVO usuarioVO = new ObjectMapper().readValue(usuarioParam, UsuarioVO.class);
			
			Usuario usuario = new Usuario();
			
			usuario = usuarioVO.getUsuario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcusuario = new JDBCUsuarioDAO(conexao);
			boolean validacao = jdbcusuario.validarEmailUsuario(usuario.getEmail());
			
			if(validacao == false) {
				jdbcusuario.cadastrarUsuario(usuario);
				conec.fecharConexao();
				
				usuarioVO = new UsuarioVO();
				usuarioVO.convertToVO(usuario);
				return this.buildResponse(usuarioVO);
			} else {
				conec.fecharConexao();
				return this.buildResponse("email já cadastrado");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
	}
	
	
	@Context
	private HttpServletRequest request;
	
	@POST
	@Path("/recuperarLogin")
	@Consumes("application/*")
	public String RecuperarLogin(String usuarioParam) {
		try{
			String idEmail = (String) request.getSession().getAttribute("login");
			return this.buildResponse(idEmail);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
	}
	
}
