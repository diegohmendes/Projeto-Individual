package br.com.controleFinanceiroPessoal.rest;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;


public class UtilRest {
	
	@Context
	private HttpServletRequest request;

	public int getUsuario(){
		int id = (int) request.getSession().getAttribute("id");
		return id;
	};
	
	public String buildResponse(Object result) {
		
		StringWriter fw = new StringWriter();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.writeValue(fw, result);
			
			return fw.toString();
			
		} catch (Exception ex) {
			ResponseBuilder rb = Response.serverError();
			return rb.toString();
		}
		
	}
	
	public String buildResponseError(Object str) {
		
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		
		rb = rb.type("text/plain");
		
		return rb.toString();
		
	}
}	
