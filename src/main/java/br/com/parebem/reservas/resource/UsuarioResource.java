package br.com.parebem.reservas.resource;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.modelo.Usuario;

@Path("usuarios")
public class UsuarioResource {

	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public String todosUsuarios() {
		Usuario usuarios = new Usuario();
		String jsonUsuarios = usuarios.listaUsuarios();
		System.out.println(jsonUsuarios);
		return jsonUsuarios;
	}
	
	private JsonObject toJson(String dados) {
		System.out.println("converter dados para json");
		return new Gson().fromJson(dados, JsonObject.class);
	}
	
	@POST
	@Path("criar")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response salvaUsuario(String conteudo) {
		System.out.println("post criar usuarios");
		System.out.println(conteudo);
		JsonObject usuarioJson = this.toJson(conteudo.toString());
		System.out.println(usuarioJson);
		Usuario usuario = new Usuario();
		System.out.println("Json de usuários criado");
		
		String nome = usuarioJson.get("nome").toString().replace("\"", "");
		String senha = usuarioJson.get("senha").toString().replace("\"", "");;
		String telefone = usuarioJson.get("telefone").toString().replace("\"", "");;
		System.out.println("Dados separados");
		System.out.println(nome + " " + senha + " " + telefone);
		
		String resultado = usuario.criarUsuario(nome, senha, telefone);
		System.out.println("o resultado de criar usuario");
		System.out.println(resultado);
		
		return Response.ok(resultado).build();
	}
}
