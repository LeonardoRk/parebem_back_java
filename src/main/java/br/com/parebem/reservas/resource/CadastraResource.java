package br.com.parebem.reservas.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.modelo.CadastraUsuariosEventos;
import br.com.parebem.reservas.modelo.Evento;
import br.com.parebem.reservas.modelo.Usuario;

@Path("cadastros")
public class CadastraResource {
	
	private JsonObject getJson(String json) {
		return new Gson().fromJson(json, JsonObject.class);
	}
	
	@POST
	@Path("usuarioseventos")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response insereUsuariosEventos(String conteudo) {
		JsonObject json = new Gson().fromJson(conteudo, JsonObject.class);
		CadastraUsuariosEventos c = new CadastraUsuariosEventos();
		System.out.println(conteudo);
		String retorno = c.insereUsuarioEventos(json);
		if(retorno.compareTo("[]") == 0) {
			String sucesso = "{\"status\":\"ok\"}";
			return Response.ok(sucesso).build();
		}else {
			return Response.serverError().build();
		}
			
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response infoPaginaEventos(){
		Evento e = new Evento();
		JsonArray idLocalEventos = e.localIdVencidoQtdPessoasEvento();

		Usuario u = new Usuario();
		String idNomeUsuarios = u.idNomeUsuarios();
		
		int qtdEventos = idLocalEventos.size();
		JsonObject usuariosCadaEvento = new JsonObject();
		
		for(int i = 0 ; i < qtdEventos; i++) {
			System.out.println("i: " + i);
			JsonObject eventoInfo = new Gson().fromJson(idLocalEventos.get(i).toString(), JsonObject.class);
			System.out.println("evento info: " + eventoInfo);
			
			String a = eventoInfo.get("id_evento").toString().replace("\"", "");
			System.out.println("evento:" + a);
			
			int idEventoAux = Integer.parseInt(a);
			usuariosCadaEvento.addProperty(a, CadastraUsuariosEventos.nomeUsuariosDoEvento(idEventoAux).toString());
			
		}
		System.out.println(usuariosCadaEvento);
		
		 
		String re = "{";
		re += "\"eventos\":" + idLocalEventos; 
		re += ",\"usuarios\":" + idNomeUsuarios;
		re += ",\"usuarios_evento\":" + usuariosCadaEvento;
		re += "}";
		System.out.println("objeto de retorno");
		System.out.println(re);
		return Response.ok(re).build();
	}
}
