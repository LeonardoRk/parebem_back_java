package br.com.parebem.reservas.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GeneralServices {
	public static JsonObject toJson(String dados) {
		JsonObject data = new Gson().fromJson(dados, JsonObject.class);
		System.out.println("in tojson of general-services");
		System.out.println(data);
		System.out.println(data.getClass());
		return data;
	}
	
	public static String fromListToJsonString(List events, String key){
		JsonArray ja = new JsonArray();
		System.out.println("in method from list to json of general services class");
		for(Object obj: events) {
			System.out.println("Iterating over object");
			System.out.println(obj.toString());
			String jsonString = obj.toString();
			System.out.println("The json string");
			ja.add(jsonString);
		}
		System.out.println("Finished iterating");
		JsonObject data = new JsonObject();
		data.add(key, ja);
		System.out.println("RETURNING DATA TO STRING");
		return data.toString();
	}
	
	public static String toJsonString(String arr) {
		return new Gson().toJson(arr);
	}

	public static String formatJsonDate(JsonElement jsonElement) {
		System.out.println("method formatJsonDate to string");
		System.out.println(jsonElement);
		String dateString = jsonElement.toString();
		System.out.println("The date string");
		System.out.println(dateString);
		
		return dateString;
	}


}
