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

import br.com.controleFinanceiroPessoal.VO.CategoriaVO;
import br.com.controleFinanceiroPessoal.db.conexao.Conexao;
import br.com.controleFinanceiroPessoal.jdbc.JDBCCategoriaDAO;
import br.com.controleFinanceiroPessoal.objetos.Categoria;

@Path("/categoriaRest")
public class CategoriaRest extends UtilRest {
	
	@POST
	@Path("/cadastrarCategoria")
	@Consumes("application/*")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String cadastrarCategoria(String categoriaParam) {
		try{
			CategoriaVO categoriaVO = new ObjectMapper().readValue(categoriaParam, CategoriaVO.class);

			categoriaVO.setIdUsuario(getUsuario());
			Categoria categoria = new Categoria();
			categoria = categoriaVO.getCategoria();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao); 
			jdbcCategoria.cadastrarCategoria(categoria);
			conec.fecharConexao();
			
			categoriaVO.convertToVO(categoria);
			return this.buildResponse(categoriaVO);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao cadastrar a categoria.");	
		}
	}
	
	@POST
	@Path("/exibirCategoria")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String exibirCategoria(String categoriaParam) {
		try{
			List<Categoria> listaDeCategoria = new ArrayList<Categoria>();
			int idUsuario = getUsuario();			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			listaDeCategoria = jdbcCategoria.buscarCategoria(categoriaParam, idUsuario);
			conec.fecharConexao();
			
			CategoriaVO categoriaVO;
			List<CategoriaVO> ListCatVO = new ArrayList<CategoriaVO>();
			
			for (Categoria categoria : listaDeCategoria) {
				categoriaVO = new CategoriaVO();
				categoriaVO.convertToVO(categoria);
				ListCatVO.add(categoriaVO);
			}
			return this.buildResponse(ListCatVO);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível recuperar a lista de categorias");
		}
	}
	
	@POST
	@Path("/removerCategoria/{idCategoria}")
 	@Consumes("application/*")
	public String removerCategoria(@PathParam("idCategoria") int id) {
		try {
			int idUsuario = getUsuario();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			boolean validador = jdbcCategoria.removerCategoria(id, idUsuario);
			conec.fecharConexao();
			
			return this.buildResponse(validador);
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao remover categoria");
		}
	}
	
	@POST
	@Path("/editarCategoria")
	@Consumes("application/*")
	public String editarCategoria(String categoriaParam) {
		try {
			CategoriaVO categoriaVO  = new ObjectMapper().readValue(categoriaParam, CategoriaVO.class);
			
			categoriaVO.setIdUsuario(getUsuario());
			
			Categoria categoria = new Categoria();
			
			categoria = categoriaVO.getCategoria();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			jdbcCategoria.editarCategoria(categoria);
			conec.fecharConexao();
			
			categoriaVO = new CategoriaVO();
			categoriaVO.convertToVO(categoria);
			return this.buildResponse(categoriaVO);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Não foi possível editar a categoria");
		}
	}
	
	@POST
	@Path("/buscarPorId/{idCategoria}")
 	@Consumes("application/*")
	public String buscarCategoriaPorId(@PathParam("idCategoria") int id) {
		try {
			int idUsuario = getUsuario();
			
			Categoria categoria = new Categoria();
			categoria.setId(id);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			categoria = jdbcCategoria.buscarCategoriaPorId(id, idUsuario);
			conec.fecharConexao();
			
			CategoriaVO categoriaVO = new CategoriaVO();
			
			categoriaVO.convertToVO(categoria);
			return this.buildResponse(categoriaVO);
		
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildResponseError("Erro ao remover categoria");
		}
	}
	
}

