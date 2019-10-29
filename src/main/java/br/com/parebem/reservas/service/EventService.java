package br.com.parebem.reservas.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.parebem.reservas.dao.EventDao;
import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.EventState;
import br.com.parebem.reservas.model.Evento;
import br.com.parebem.reservas.model.OpenedEvent;

public class EventService {

	public List<OpenedEvent> listOpenedEvents() {
		EventDao eventDao = new EventDao();
		List<OpenedEvent> openedEvents = eventDao.getOpenedEvents();
		
		return openedEvents;
	}
	
	public String createEvent(String desformat) {
		System.out.println("The received content is: ");
		System.out.println(desformat);
		
		JsonObject content = new Gson().fromJson(desformat, JsonObject.class);
		System.out.println("formated content");
		System.out.println(content);
		
		String place = content.get("place").toString().replace("\"", "");
		String expirationMoment  = content.get("expirationMoment").toString().replace("\"", "");
		System.out.println("trying to format expiration moment");
		String pattern="yyyy-MM-dd'T'HH:mm";
		System.out.println("O expirationMoment ");
		System.out.println(expirationMoment );
		SimpleDateFormat formatter = new SimpleDateFormat(pattern); // your template here
		Date date = null;
		Boolean error = false;
		try {
			System.out.println("iniciando bloco try");
			date = formatter.parse(expirationMoment );
			System.out.println("parsing time");
		}catch (java.text.ParseException e) {
			System.out.println("Exception in formatting data");
			error = true;
		}
		
		int guestLimit  = content.get("guestLimit").getAsInt();
		System.out.println("date string");
		Event e = new Event();
		e.setGuestLimit(guestLimit);
		e.setPlace(place);
		e.setExpirationMoment(date);
		e.setState(EventState.ABERTO);
		
		EventDao dao = new EventDao();
		Event insertedEvent = null;
		insertedEvent = dao.insertEvent(e);
		
		String data = null;
		if(!error) {
			if(insertedEvent != null) {
				data = "{\"status\":\"ok\"}";
			}else {
				data = "{\"status\":\"not ok\"}";
			}
		}else {
			// nothing to do in here
		}
		return data;
	}

	public String closeEventById(String content) {
		System.out.println("Closing event");
		JsonObject json = new Gson().fromJson(content, JsonObject.class);
		System.out.println(json);
		System.out.println("parsing to json");
		EventDao dao = new EventDao();
		
		String eventId = json.get("eventId").toString();
		System.out.println("eventId " + eventId);
		String result = dao.expiryEvent(Integer.parseInt(eventId));
		System.out.println("Dao evento result");
		System.out.println(result);
	
		String status = null;
		if(result.compareTo("ok") == 0) {
			status = result;
		}else {
			status = "not ok";
		}
		
		return status;
	}

	public List<Event> getAllEvents() {
		List<Event> events = new EventDao().listAllEvents();
		return events;
	}

}
