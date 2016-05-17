package br.com.controleFinanceiroPessoal.login;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCUsuarioDAO;
import br.com.controleFinanceiroPessoal.objetos.Usuario;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	 
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Usuario usuario = new Usuario();
		
		try {
			
			usuario.setEmail(req.getParameter("emailUsuario"));
			usuario.setSenha(req.getParameter("senhaUsuario"));
			
			String contexto = req.getServletContext().getContextPath();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			boolean validacao = jdbcUsuario.validarUsuario(usuario);
			jdbcUsuario.recuperarDadosUsuario(usuario);
			conec.fecharConexao();
			HttpSession sessao = req.getSession();
			
					
			if(validacao) {
				sessao.setAttribute("login", req.getParameter("emailUsuario"));
				sessao.setAttribute("id", usuario.getId());
				resp.sendRedirect(contexto+"/sistema/sistema.html");
			} else {
				sessao.setAttribute("erro", "Login e senha incorretos");
				resp.sendRedirect(contexto+"/index.html#errorLogin");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
