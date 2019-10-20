package br.com.parebem.reservas;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Servidor {
	
	private static Runnable t1 = new Runnable() {
        public void run() {
            try{
            	System.out.println("rodando thread");
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
                		
                		
                		JsonParser jp = new JsonParser();
                		Object obj = jp.parse(conteudo);
                		JsonArray jA = new JsonArray();
                		jA = (JsonArray) obj;
                		for(int i = 0 ; i < jA.size(); i++) {
                			String ss = jA.get(i).toString();
                			JsonObject json = new Gson().fromJson(ss, JsonObject.class);
                			String momento = json.get("momento").toString();
                			momento = momento.replace("\"", "");
                			
                			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");  
                			LocalDateTime now = LocalDateTime.now();  
                			Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                			
                			String pattern="yyyy-MM-dd HH:mm:ss.S";
                			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                			
                			Date dateVencimento = formatter.parse(momento);
                		
                			String horaVencimento = formatter.format(dateVencimento);

                			System.out.println("hora de vencimento");
                			System.out.println(horaVencimento);
                			if(dateVencimento.compareTo(nowDate) < 0) {
                				System.out.println("Vencer um evento ultrapassado");
                				String id_evento = json.get("id_evento").toString();
                				String dJson = "{id_evento:" + id_evento + "}";
                    			target.path("/eventos/venceevento").request().post(Entity.json(dJson), String.class);
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
    
	public static void main(String[] args) throws IOException {
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig config = new ResourceConfig().packages("br.com.parebem.reservas");
		config.register(new CORSFilter());
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		System.out.println("Servidor rodando");
		new Thread(t1).start();
		System.in.read();
		System.out.println("servidor parando");
		server.stop();	
	}
}


