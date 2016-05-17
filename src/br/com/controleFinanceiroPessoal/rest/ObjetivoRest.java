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

import br.com.controleFinanceiroPessoal.VO.ObjetivoVO;
import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCCategoriaDAO;
import br.com.controleFinanceiroPessoal.jdbc.JDBCObjetivoDAO;
import br.com.controleFinanceiroPessoal.objetos.Categoria;
import br.com.controleFinanceiroPessoal.objetos.Conversor;
import br.com.controleFinanceiroPessoal.objetos.Objetivo;
import br.com.controleFinanceiroPessoal.objetos.Usuario;


@Path("/objetivoRest")
public class ObjetivoRest extends UtilRest {
	

	@POST
	@Path("/buscarObjetivo")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String buscarObjetivos(String objetivoParam) {
		try {
			List<Objetivo> listaDeObjetivos = new ArrayList<Objetivo>();
			Objetivo objetivo = new Objetivo();
			objetivo.setUsuario(new Usuario());
			objetivo.getUsuario().setId(getUsuario());
			
			ObjetivoVO objetivoVO;
			List<ObjetivoVO> listObjetivoVO = new ArrayList<ObjetivoVO>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivos = new JDBCObjetivoDAO(conexao);
			listaDeObjetivos = jdbcObjetivos.buscarObjetivos(objetivo);
			conec.fecharConexao();
			
			for (Objetivo objetivo2 : listaDeObjetivos) {
				objetivoVO = new ObjetivoVO();
				objetivoVO.convertToVO(objetivo2);
				objetivoVO.setfValorPercent((objetivo2.getValorCorrente() * 100) / objetivo2.getValor());
				listObjetivoVO.add(objetivoVO);
			}
			return this.buildResponse(listObjetivoVO);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
	}
	
	@POST
	@Path("/cadastrarObjetivo")
	@Consumes("application/*")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String cadastrarObjetivo(String objetivoParam) {
		try {
			ObjetivoVO objetivoVO = new ObjectMapper().readValue(objetivoParam, ObjetivoVO.class);

			objetivoVO.setIdUsuario(getUsuario());
			Objetivo objetivo = new Objetivo();
			Categoria categoria = new Categoria();
			
			objetivo = objetivoVO.getObjetivo();
			
			categoria.setDescricao(objetivo.getDescricao());
			categoria.setUsuario(new Usuario());
			categoria.getUsuario().setId(getUsuario());
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			jdbcCategoria.cadastrarCategoria(categoria);
			
			JDBCObjetivoDAO jdbcObjetivos = new JDBCObjetivoDAO(conexao);
			objetivo.setId(categoria.getId());
			jdbcObjetivos.cadastrarObjetivo(objetivo);
			conec.fecharConexao();
			
			objetivoVO = new ObjetivoVO();
			objetivoVO.convertToVO(objetivo);
			return this.buildResponse(objetivoVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}
	
	@POST
	@Path("/addValorObjetivo")
	@Consumes("application/*")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String addValorObjetivo(String objetivoParam) {
		try {
			ObjetivoVO objetivoVO = new ObjectMapper().readValue(objetivoParam, ObjetivoVO.class);
			objetivoVO.setIdUsuario(getUsuario());
			Objetivo objetivo = objetivoVO.getObjetivo();
			
			Conexao conec = new Conexao();
			
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivos = new JDBCObjetivoDAO(conexao);
			
			jdbcObjetivos.adicionarValorObjetivo(objetivo);
			objetivo = jdbcObjetivos.recuperarValorObjetivo(objetivo);
			conec.fecharConexao();
		
			objetivoVO = new ObjetivoVO();
			objetivoVO.convertToVO(objetivo);
	
			objetivoVO.setfValorPercent((objetivo.getValorCorrente() * 100) / objetivo.getValor());
			
			return this.buildResponse(objetivoVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError(e.getMessage());
		}
		
	}
	
	@POST
	@Path("/verificarObjetivo/{idObjetivo}")
 	@Consumes("application/*")
	public String verificarObjetivo(@PathParam("idObjetivo") int id) {
		try {
			Objetivo objetivo = new Objetivo();
			objetivo.setUsuario(new Usuario());
			objetivo.setId(id);
			objetivo.getUsuario().setId(getUsuario());
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivo = new JDBCObjetivoDAO(conexao);
			
			jdbcObjetivo.recuperarValorObjetivo(objetivo);

			// se ainda tiver saldo, remove o valor do saldo
			if(objetivo.getValorCorrente() <= 0) {
				jdbcObjetivo.removerObjetivo(objetivo);
				conec.fecharConexao();
				return this.buildResponse(true);
			} else {
				return this.buildResponse(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao conferir o objetivo");
		}
	}
	
	@POST
	@Path("/concluirObjetivo/{idObjetivo}")
 	@Consumes("application/*")
	public String concluirObjetivo(@PathParam("idObjetivo") int id) {
		try {
			Objetivo objetivo = new Objetivo();
			objetivo.setUsuario(new Usuario());
			objetivo.getUsuario().setId(getUsuario());
			objetivo.setId(id);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivo = new JDBCObjetivoDAO(conexao);
			objetivo = jdbcObjetivo.recuperarValorObjetivo(objetivo);
			jdbcObjetivo.mudarStatusObjetivo(objetivo);
			conec.fecharConexao();
			
			ObjetivoVO objetivoVO = new ObjetivoVO();
			
			objetivoVO.convertToVO(objetivo);
			
			return this.buildResponse(objetivoVO.getStatus());
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao excluir o objetivo");
		}
	}
	
	
	@POST
	@Path("/removerObjetivoOk/{idObjetivo}")
 	@Consumes("application/*")
	public String removerObjetivoOk(@PathParam("idObjetivo") int id) {
		try {
			Objetivo objetivo = new Objetivo();
			objetivo.setUsuario(new Usuario());
			objetivo.setId(id);
			objetivo.getUsuario().setId(getUsuario());
	
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivo = new JDBCObjetivoDAO(conexao);
			objetivo = jdbcObjetivo.recuperarValorObjetivo(objetivo);
			jdbcObjetivo.removerObjetivo(objetivo);
			jdbcObjetivo.removerObjetivoDespesa(objetivo);
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			jdbcCategoria.removerCategoria(objetivo.getId(), objetivo.getUsuario().getId());
			conec.fecharConexao();
			
			return this.buildResponse("Removido com sucesso");
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao excluir o objetivo");
		}
	}
	
	@POST
	@Path("/buscarValorTotal/{saldoTotal}")
	@Consumes("application/*")
	public String buscarValorTotalObjetivo(@PathParam("saldoTotal") float totalMov) {
		try {
			int idUsuario = getUsuario();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCObjetivoDAO jdbcObjetivoDAO = new JDBCObjetivoDAO(conexao);
			float valorObj = jdbcObjetivoDAO.buscarValorTotal(idUsuario);
			
			Conversor converter = new Conversor();
			totalMov = totalMov - valorObj;
			String valorTotal = converter.convertFloatToString(totalMov);
				
			conec.fecharConexao();
			
			return this.buildResponse(valorTotal);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao recuperar valor do objetivo");
		}	
	}
}
