package br.com.parebem.reservas.resource;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.mysql.fabric.xmlrpc.base.Array;

import br.com.parebem.reservas.dao.EventDao;
import br.com.parebem.reservas.model.CadastraUsuariosEventos;
import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.Evento;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.model.Usuario;
import br.com.parebem.reservas.util.GeneralServices;

@Path("cadastros")
public class CadastraResource {
	
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
		
		EventDao dao = new EventDao();
		List<Event> allEvents = dao.listAllEvents();
		JsonObject json = new JsonObject();
		ArrayList<String> object = new ArrayList<String>();
		
		for(int i = 0 ; i < allEvents.size(); i++) {
			Event e = allEvents.get(i);
			
			Set<User> usersInEvent = e.getUserEvents();
			for(User user: usersInEvent) {
				object.add(user.toString());
			}
			json.addProperty(e.toString(), object.toString());
		}
		System.out.println("The json data");
		System.out.println(json);
		
		return Response.ok(json).build();

	}
}
