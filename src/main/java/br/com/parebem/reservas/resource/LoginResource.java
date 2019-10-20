package br.com.parebem.reservas.resource;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.modelo.Login;

@Path("login")
public class LoginResource {
	
	
	public JsonObject toJson(String dados) {
		return new Gson().fromJson(dados, JsonObject.class);
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticaUsuario(String conteudo) {
		System.out.println(conteudo);
		JsonObject dadosLogin = this.toJson(conteudo.toString());
		System.out.println(dadosLogin);
		Login login = new Login(dadosLogin.get("nome").toString().replace("\"", ""), 
				dadosLogin.get("senha").toString().replace("\"", ""));
		System.out.println(login.getPassword());
		System.out.println(login.getUserName());
		String status = null;
		status = login.validaLogin();
		
		if(status != "") {
			return Response.ok(toJson(status).toString())
					.status(200)
					.build();
		}else {
			return Response.serverError().build();
		}
		
		
	}
}
