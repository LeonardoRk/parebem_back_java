package br.com.parebem.reservas.resource;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.parebem.reservas.service.LoginService;
import br.com.parebem.reservas.util.GeneralServices;

@Path("login")
public class LoginResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userAuthentication(String content) {
		try {
			System.out.println("The content received is:");
			System.out.println(content);
			String status = null;
			status = LoginService.validateLogin(content);
			System.out.println("The login state is: " + status);
			if(status != null) {
				return Response.ok(GeneralServices.toJson(status).toString())
						.status(200)
						.build();
			}else {
				return Response.status(400).build();
			}
		}catch (Exception e) {
			System.out.println("Server error in login resource");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
	}
}
