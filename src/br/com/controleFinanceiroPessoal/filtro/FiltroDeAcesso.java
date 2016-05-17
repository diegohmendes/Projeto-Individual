package br.com.controleFinanceiroPessoal.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltrodeAcesso
 */
public class FiltroDeAcesso implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroDeAcesso() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

			String contexto = req.getServletContext().getContextPath();

			try {
				HttpSession sessao = ((HttpServletRequest) req).getSession();
				
				String usuario = null;
				
				usuario = (String) sessao.getAttribute("login");

				
				if (usuario == null) {

					sessao.setAttribute("erro", "Voce não esta logado no sistema");
					
					((HttpServletResponse) resp).sendRedirect(contexto+"/index.html");

				} else {
					chain.doFilter(req, resp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}