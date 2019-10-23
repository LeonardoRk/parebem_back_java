package br.com.parebem.reservas.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.dao.Dao;

public class CadastraUsuariosEventos {

	public static JsonArray nomeUsuariosDoEvento(int id_evento){
		String sql = "select nome from usuario " + 
					"inner join usuario_evento " + 
					"on usuario.id_usuario = usuario_evento.id_usuario " + 
					"where id_evento = "+ id_evento + ";";
		ArrayList<HashMap<String, String>> resultadoBanco = Dao.executaComando(sql);
		JsonArray resultado = new JsonArray();
		
		for(int i = 0 ; i < resultadoBanco.size(); i++) {
			String jsonS = new Gson().toJson(resultadoBanco.get(i));
			resultado.add(new Gson().fromJson(jsonS, JsonObject.class));
		}
		return resultado;
	}
	
	public String insereUsuarioEventos(JsonObject json) {
		String id_usuario = json.get("id_usuario").toString().replace("\"", "");
		String id_evento = json.get("id_evento").toString().replace("\"", "");
		System.out.println("parametros recebidos");
		System.out.println(id_usuario + " " + id_evento);
		String sql = "insert into usuario_evento(id_usuario, id_evento) values(";
		sql += id_usuario + ",";
		sql += id_evento;
		sql += ");";
		ArrayList<HashMap<String, String>> resultadoBanco = Dao.executaComando(sql);
		System.out.println("Resultado do banco");
		System.out.println(resultadoBanco);
		return resultadoBanco.toString();
	}
}
