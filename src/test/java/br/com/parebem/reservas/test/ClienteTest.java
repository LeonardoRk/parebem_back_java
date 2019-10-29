package br.com.parebem.reservas.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;


public class ClienteTest {
	
	HttpServer server = null;
	/*
	@Before
	public void iniciaServer() {
		System.out.println("Iniciando server para testes");
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig config = new ResourceConfig().packages("br.com.parebem.reservas");
		this.server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}
	
	@After
	public void paraServer() {
		System.out.println("parando servidor ao fim de teste");
		this.server.stop();
	}
	*/
	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = null;
		conteudo = target.path("/usuarios").request().get(String.class);
		System.out.println(conteudo);
		Assert.assertNotNull(conteudo);
	}
	
	@Test
	public void getOpenedEventsTest() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Response conteudo = null;
		conteudo = target.path("/eventos/abertos").request().get(Response.class);
		System.out.println(conteudo);
		
		
		//Object obj = parser.parse(conteudo);
		//JsonObject jsonObject = (JsonObject) obj;
		//assertNotNull(jsonObject.get("opened_events"));
		
	}
	
	
	
	
	
	
}
