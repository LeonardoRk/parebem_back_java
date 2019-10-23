package br.com.parebem.reservas.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GeneralServices {
	public static JsonObject toJson(String dados) {
		return new Gson().fromJson(dados, JsonObject.class);
	}

}
