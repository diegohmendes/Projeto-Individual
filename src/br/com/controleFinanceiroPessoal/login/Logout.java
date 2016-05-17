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
import br.com.controleFinanceiroPessoal.rest.UtilRest;

/**
 * Servlet implementation class Login
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
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
		try {		
			String contexto = req.getServletContext().getContextPath();
			
			HttpSession sessao = req.getSession();
			
			sessao.invalidate();
			//resp.sendRedirect("http://localhost:8080/controleFinanceiroPessoal/index.html");
			
			
			resp.getWriter().write( new UtilRest().buildResponse(contexto+"/index.html") );
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
