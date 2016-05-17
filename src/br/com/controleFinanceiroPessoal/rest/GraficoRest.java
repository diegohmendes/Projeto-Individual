package br.com.controleFinanceiroPessoal.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCGraficoDAO;
import br.com.controleFinanceiroPessoal.objetos.Grafico;
import br.com.controleFinanceiroPessoal.objetos.Usuario;


@Path("/graficoRest")
public class GraficoRest extends UtilRest {
	
	@POST
	@Path("/gerarGrafico")
	@Consumes("application/*")
	public String gerarGraficoTotal(String graficoParam) {
		try {
			Grafico grafico = new ObjectMapper().readValue(graficoParam, Grafico.class);
			int idUsuario = getUsuario();
			//int idUsuario = (int) request.getSession().getAttribute("id");
			grafico.setUsuario( new Usuario() );
			grafico.getUsuario().setId(idUsuario);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCGraficoDAO jdbcGrafico = new JDBCGraficoDAO(conexao);
			
			List totalMov = new ArrayList();
			totalMov = jdbcGrafico.gerarGraficoTotal(totalMov, grafico);
			conec.fecharConexao();
			
			return this.buildResponse(totalMov);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}
	
	@POST
	@Path("/gerarGraficoReceita")
	@Consumes("application/*")
	public String gerarGraficoReceita(String graficoParam) {
		try {
			Grafico grafico = new ObjectMapper().readValue(graficoParam, Grafico.class);
			int idUsuario = getUsuario();
			//int idUsuario = (int) request.getSession().getAttribute("id");
			grafico.setUsuario( new Usuario() );
			grafico.getUsuario().setId(idUsuario);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCGraficoDAO jdbcGrafico = new JDBCGraficoDAO(conexao);
			
			List catReceita = new ArrayList();
			catReceita = jdbcGrafico.gerarGraficoReceita(catReceita, grafico);
			conec.fecharConexao();

			
			return this.buildResponse(catReceita);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}
	
	@POST
	@Path("/gerarGraficoDespesa")
	@Consumes("application/*")
	public String gerarGraficoDespesa(String graficoParam) {
		try {
			Grafico grafico = new ObjectMapper().readValue(graficoParam, Grafico.class);
			int idUsuario = getUsuario();
			//int idUsuario = (int) request.getSession().getAttribute("id");
			grafico.setUsuario( new Usuario() );
			grafico.getUsuario().setId(idUsuario);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCGraficoDAO jdbcGrafico = new JDBCGraficoDAO(conexao);
			
			List catReceita = new ArrayList();
			catReceita = jdbcGrafico.gerarGraficoDespesa(catReceita, grafico);
			conec.fecharConexao();
			
			return this.buildResponse(catReceita);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}

}