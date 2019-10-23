package br.com.parebem.reservas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.dao.Dao;

public class Evento {
	
	private int limite_convidados;
	private String local = null;
	Date date = new Date();
	private Object momento = new java.sql.Timestamp(date.getTime());
	
	public Evento() {
		
	}
	
	public Evento(int limite_convidados, String local, Object param) {
		super();
		this.limite_convidados = limite_convidados;
		this.local = local;
		this.momento = param;
	}
		
	public int getLimite_convidados() {
		return limite_convidados;
	}

	public void setLimite_convidados(int limite_convidados) {
		this.limite_convidados = limite_convidados;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Object getMomento() {
		return momento;
	}

	public void setMomento(Object momento) {
		this.momento = momento;
	}

	public String toString(ArrayList<HashMap<String, String>> linhas) {
		return new Gson().toJson(linhas);
	}

	public String listaEventos() {
		String sql = "select * from evento;";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toString(resultado);
	}
	
	public String listaEventosAbertos() {
		String sql = "select * from evento where vencido=0;";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toString(resultado);
	}
	
	
	public String insereEvento() {
		String sql = "insert into evento(momento, limite_convidados, local) values('"+
					this.momento + "','" + this.limite_convidados 
					+ "','" + this.local + "');";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toString(resultado);
	}
	
	public JsonArray localIdVencidoQtdPessoasEvento() {
		String sql = "select local, id_evento, vencido, limite_convidados from evento;";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		System.out.println(resultado);
		
		JsonArray ja = new JsonArray();
		for(int i = 0 ; i < resultado.size(); i++) {
			System.out.println("item " + i);
			String obj = new Gson().toJson(resultado.get(i));
			System.out.println(obj);
			JsonObject a = new Gson().fromJson(obj, JsonObject.class);
			
			System.out.println(a);
			ja.add(a);
		}
		System.out.println(ja);
		return ja;
	}
	
	public String venceEvento(int idEvento) {
		String sql = "update evento set vencido=1 where id_evento=" + idEvento + ";";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		String jsonString = new Gson().toJson(resultado);
		return jsonString;
	}
}
