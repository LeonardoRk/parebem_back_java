package br.com.parebem.reservation;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.util.GeneralServices;


public class Server {
	
	private static Runnable monitorOpenEvents = new Runnable() {
        public void run() {
            try{
            	System.out.println("thread running");
            	long start = System.currentTimeMillis();
                while(true) {
                	long elapsedTimeMillis = System.currentTimeMillis()-start;
                	float elapsedTimeSec = elapsedTimeMillis/1000F;
                	if(elapsedTimeSec >= 0.5) {
                		start = System.currentTimeMillis();
                		Client client = ClientBuilder.newClient();
                		WebTarget target = client.target("http://localhost:8080");
                		String conteudo = null;
                		conteudo = target.path("/eventos/abertos").request().get(String.class);
                		
                		JsonObject jsonR = GeneralServices.toJson(conteudo);  
                		
                		JsonArray jA = new JsonArray();
                		jA = (JsonArray) jsonR.get("opened_events");
                		System.out.println(jA);
                		
                		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                		String pattern="yyyy-MM-dd HH:mm:ss";
                		for(int i = 0 ; i < jA.size(); i++) {
                			System.out.println("elemento: " + i);
                			
                			String row = jA.get(i).getAsString();
                			JsonObject jsonEvent = GeneralServices.toJson(row);
                	
                			String dateStr = jsonEvent.get("expirationMoment").getAsString();
                			System.out.println("Date str");
                			System.out.println(dateStr);
                			
                			SimpleDateFormat ft = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa", Locale.US);
                			Date d = ft.parse(dateStr);
                			ft.applyPattern(pattern);
                			System.out.println("Is this what i expect?");
                			System.out.println(ft.format(d));
                			
                			JsonElement elementJson = jsonEvent.get("expirationMoment");
                			
                			if(elementJson != null) {
                    			LocalDateTime now = LocalDateTime.now();  
                    			Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                    			System.out.println(nowDate);
                    			
                    			System.out.println("expiration date");
                    			System.out.println(d);
                    			
                    			if(d.compareTo(nowDate) < 0) {
                    				System.out.println("Vencer um evento ultrapassado");
                    				String eventId = jsonEvent.get("eventId").toString();
                    				System.out.println(eventId);
                    				String dJson = "{eventId:" + eventId + "}";
                        			target.path("/eventos/venceevento").request().post(Entity.json(dJson), String.class);
                        		}
                			}else {
                				System.out.println("null element");
                			}

                		}
                		
                	}
                	
                }
            } catch (Exception e){
            	System.out.println("Caiu em alguma exceção");
            	System.out.println(e.getMessage());
            }
 
        }
    };
    
    static HttpServer server;
    
	public static void main(String[] args) throws IOException {
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig config = new ResourceConfig().packages("br.com.parebem.reservas");
		config.register(new CORSFilter());
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		System.out.println("Server running");
		Thread monitoring =  new Thread(monitorOpenEvents);
		//monitoring.start();
		System.in.read();
		System.out.println("server stopping");
		server.stop();	
		System.out.println("server stopped");
		monitoring.interrupt();
		System.out.println("Thread stopping");
		System.exit(0);
	}
}


