package br.com.controleFinanceiroPessoal.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.controleFinanceiroPessoal.VO.MovimentacaoVO;
import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCMovimentacaoDAO;
import br.com.controleFinanceiroPessoal.objetos.Movimentacao;

@Path("/movimentacaoRest")
public class MovimentacaoRest extends UtilRest {
	
	@POST
	@Path("/cadastrarMovimentacao")
	@Consumes("application/*")
	public String cadastrarMovimentacao(String movimentacaoParam) {
		try {
			MovimentacaoVO movimentacaoVO = new ObjectMapper().readValue(movimentacaoParam, MovimentacaoVO.class);
			
			movimentacaoVO.setIdUsuario(getUsuario());
			Movimentacao movimentacao = movimentacaoVO.getMovimentacao();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			jdbcMovimentacao.cadastrarMovimentacao(movimentacao);	
			conec.fecharConexao();
			
			movimentacaoVO = new MovimentacaoVO();
			movimentacaoVO.convertToVO(movimentacao);
			return this.buildResponse(movimentacaoVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}
	
	@POST
	@Path("/exibirMovimentacao/{tipoMov}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String exibirMovimentacao(@PathParam("tipoMov") int tipo) {
		try{
			List<Movimentacao> listaDeMov = new ArrayList<Movimentacao>();
			Conexao conec = new Conexao();
			int idUsuario = getUsuario();

			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			listaDeMov = jdbcMovimentacao.buscarMovimentacao(tipo, idUsuario);
			
			MovimentacaoVO movimentacaoVO;
			
			List<MovimentacaoVO> listMovVO = new ArrayList<MovimentacaoVO>();
			
			for (Movimentacao movimentacao : listaDeMov) {
				movimentacaoVO = new MovimentacaoVO();
				movimentacaoVO.convertToVO(movimentacao);
				listMovVO.add(movimentacaoVO);
			}
			conec.fecharConexao();
			
			return this.buildResponse(listMovVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível recuperar a lista de Movimentacoes");
		}	
	}
	
	@POST
	@Path("/editarMovimentacao")
	@Consumes("application/*")
	public String editarCategoria(String movimentacaoParam) {
		
		try {
			MovimentacaoVO movimentacaoVO = new ObjectMapper().readValue(movimentacaoParam, MovimentacaoVO.class);
			//System.out.println("Tipo quando chega: "+movimentacaoVO.getTipo());
			movimentacaoVO.setIdUsuario(getUsuario());
			Movimentacao movimentacao = movimentacaoVO.getMovimentacao();
			//System.out.println("Tipo quando converte: "+movimentacao.getTipo());
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			jdbcMovimentacao.editarMovimentacao(movimentacao);
			
			//System.out.println("Tipo apos editar: "+movimentacao.getTipo());
			movimentacaoVO = new MovimentacaoVO();
			movimentacaoVO.convertToVO(movimentacao);
			//System.out.println("Tipo apos converter VO "+movimentacaoVO.getTipo());
			return this.buildResponse(movimentacaoVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível editar a movimentacao");
		}
	}
	
	@POST
	@Path("/removerMovimentacao/{idMovimentacao}")
 	@Consumes("application/*")
	public String removerCategoria(@PathParam("idMovimentacao") int id) {
		try {
			int idUsuario = getUsuario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			jdbcMovimentacao.removerMovimentacao(id, idUsuario);
			conec.fecharConexao();
			
			return this.buildResponse("Removido com sucesso");
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao remover categoria");
		}
	}
	
	@POST
	@Path("/buscarUltimasMovimentacoes/{tipoMov}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String exibirUltimasMov(@PathParam("tipoMov") int tipo) {
		try{
			List<Movimentacao> listaDeMov = new ArrayList<Movimentacao>();
			int idUsuario = getUsuario();
			//int idUsuario = (int) request.getSession().getAttribute("id");
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			listaDeMov = jdbcMovimentacao.buscarCincoUltimasMov(tipo, idUsuario);
			
			conec.fecharConexao();
			
			MovimentacaoVO movimentacaoVO;
			List<MovimentacaoVO> listMovVO = new ArrayList<MovimentacaoVO>();
			
 			for (Movimentacao movimentacao : listaDeMov) {
				movimentacaoVO = new MovimentacaoVO();
				movimentacaoVO.convertToVO(movimentacao);
				listMovVO.add(movimentacaoVO);
			}
			
			return this.buildResponse(listMovVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível recuperar a lista de Movimentacoes");
		}	
	}

	@POST
	@Path("/saldoMovimentacao")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String retornarSaldoTotal() {
		
		try {
			int idUsuario = getUsuario();
			//int idUsuario = (int) request.getSession().getAttribute("id");
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			Float saldoTotal = jdbcMovimentacao.buscarSaldoTotal(idUsuario);
//			MovimentacaoRest convert = new MovimentacaoRest();
//			String valorConvert = convert.convercaoMonetaria(saldoTotal);
			return this.buildResponse(saldoTotal);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível exibir o saldo Total");
		}
		
	}
	
	
	@POST
	@Path("/buscarMovimentacaoPorId/{idMovimentacao}")
 	@Consumes("application/*")
	public String buscarMovimentacaoPorId(@PathParam("idMovimentacao") int id) {
		try {
			//int idUsuario = (int) request.getSession().getAttribute("id");
			int idUsuario = getUsuario();
			Movimentacao movimentacao = new Movimentacao();
			movimentacao.setId(id);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMovimentacaoDAO jdbcMovimentacao = new JDBCMovimentacaoDAO(conexao);
			movimentacao = jdbcMovimentacao.buscarMovimentacaoPorId(id, idUsuario);
			conec.fecharConexao();
			MovimentacaoVO movimentacaoVO = new MovimentacaoVO();
			
			movimentacaoVO.convertToVO(movimentacao);
		
			return this.buildResponse(movimentacaoVO);
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao remover categoria");
		}
	}
	
}
