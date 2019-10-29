package br.com.parebem.reservas.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.Evento;
import br.com.parebem.reservas.model.OpenedEvent;
import br.com.parebem.reservas.service.EventService;
import br.com.parebem.reservas.util.GeneralServices;

@Path("eventos")
public class EventResource {

	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response allEvents() {
		try {
			EventService ev = new EventService();
			List<Event> events = ev.getAllEvents();
			String allEvents = GeneralServices.fromListToJsonString(events, "all_events");
			return Response.ok(allEvents).build();
		}catch (Exception e) {
			System.out.println("Exception to get all events");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("abertos")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response todosEventosAbertos() {
		List<OpenedEvent> openedEvents = null;
		try {
			EventService eventService = new EventService();
			openedEvents = eventService.listOpenedEvents();
		}catch(Exception e) {
			System.out.println("Event resource exception");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
		System.out.println("in event resource");
		System.out.println(openedEvents);
		String jsonString = GeneralServices.fromListToJsonString(openedEvents, "opened_events");
		System.out.println("the prepared data to response");
		System.out.println(jsonString);
		return Response.ok(jsonString).build();
	}
	
	@POST
	@Path("venceevento")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response closeEvent(String content) {
		String status = null;
		try {
			EventService eventService = new EventService();
			status = eventService.closeEventById(content);
		}catch (Exception e) {
			System.out.println("Exception in closing event");
			return Response.serverError().build();
		}
	
		int code;	
		if(status.compareTo("ok") == 0) {
			code = 200;
		}else {
			code = 400;
		}
		
		return Response.status(code).build();
	}
	
	
	@Path("criar")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response criaEvento(String desformat) {
		System.out.println("Cria evento novo");
		String status = null;
		try {
			EventService eventService = new EventService();
			status = eventService.createEvent(desformat);
		}catch (Exception e) {
			System.out.println("Exception in inserting event");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
		if(status != null) {
			if(!status.contains("not")) {
				return Response.ok(status).build();
			}else {
				return Response.status(400).build();
			}
		}else {
			return Response.status(400).build();
		}
	}
}
