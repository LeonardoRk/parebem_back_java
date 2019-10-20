package br.com.parebem.reservas.resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.parebem.reservas.modelo.Evento;

@Path("eventos")
public class EventoResource {

	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public String todosEventos() {
		Evento eventos = new Evento();
		String jsonEventos = eventos.listaEventos();
		return jsonEventos;
	}
	
	@GET
	@Path("abertos")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public String todosEventosAbertos() {
		Evento eventos = new Evento();
		String jsonEventos = eventos.listaEventosAbertos();
		return jsonEventos;
	}
	
	
	
	@POST
	@Path("venceevento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response venceEvento(String conteudo) {
		System.out.println("Em vencer evento");
		Evento e = new Evento();
		JsonObject json = new Gson().fromJson(conteudo, JsonObject.class);
		System.out.println("transformado em json");
		String id_usuario = json.get("id_evento").toString().replace("\"", "");
		String resultado = e.venceEvento(Integer.parseInt(id_usuario));
		System.out.println(resultado);
		return Response.ok(resultado).build();
	}
	
	
	@Path("criar")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response criaEvento(String desformat) {
		System.out.println("Cria evento novo");
		JsonObject conteudo = new Gson().fromJson(desformat, JsonObject.class);
		String local = conteudo.get("local").toString().replace("\"", "");
		String momento = conteudo.get("momento").toString().replace("\"", "");
		System.out.println("indo para formatar horário");
		String pattern="yyyy-MM-dd'T'HH:mm";
		System.out.println("O momento");
		System.out.println(momento);
		SimpleDateFormat formatter = new SimpleDateFormat(pattern); // your template here
		Date date = null;
		String currentTime = null;
		try {
			System.out.println("iniciando bloco try");
			date = formatter.parse(momento);
			System.out.println("realizando parse de horário");
			//dateDB = new Date(dateStr.getTime());
			//System.out.println("O date db" + dateDB);
			currentTime = formatter.format(date);
		}catch (ParseException | java.text.ParseException e) {
			// TODO: handle exception
			System.out.println("Caiu em exceção");
		}
		
		int limite_convidados = conteudo.get("limite_convidados").getAsInt();
		System.out.println("date string");
		System.out.println(currentTime);
		Evento e = new Evento(limite_convidados, local, currentTime);
		String result = "";
		result  = e.insereEvento();
		return Response.ok(result).build();
	}
}
