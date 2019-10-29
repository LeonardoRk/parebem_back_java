package br.com.parebem.reservas.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.model.Usuario;
import br.com.parebem.reservas.service.UserService;
import br.com.parebem.reservas.util.GeneralServices;

@Path("usuarios")
public class UserResource {
	
	@GET
	@Path("dez")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ab() {
		System.out.println("In here");
		JsonObject a = new JsonObject();
		a.addProperty("oi", "ae");
		System.out.println("answering");
		return Response.ok(a.toString()).build();
	}

	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response requestUsers() {
		try {
			System.out.println("requesting service");
			UserService userService = new UserService();
			List<User> users = userService.getAllUsers();
			System.out.println("All users caught");
			String usersJson = GeneralServices.fromListToJsonString(users, "all_users");
			System.out.println("Returning response");
			return Response.ok(usersJson).build();
		}catch (Exception e) {
			System.out.println("Exception to get all users");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("criar")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response saveUser(String content) {
		String status = null;
		try {
			System.out.println("post create user");
			UserService userService = new UserService();
			status = userService.createUser(content);
		}catch (Exception e) {
			Response.serverError().build();
		}
		
		if(status.compareTo("ok") == 0) {
			status = "{\"status\":\"" + status + "\"}";
			return Response.ok(status).build();
		}else {
			return Response.status(400).build();
		}
	}
}
