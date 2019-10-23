package br.com.parebem.reservas.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.parebem.reservas.model.Login;
import br.com.parebem.reservas.util.GeneralServices;

public class LoginService {
	
	public static String validateLogin(String content) {
		String status = null;
		
		JsonObject loginData = GeneralServices.toJson(content.toString());
		System.out.println(loginData);
		
		String userLogin = loginData.get("nome").toString().replace("\"", "");
		String userPassword = loginData.get("senha").toString().replace("\"", "");
		Login login = new Login(userLogin, userPassword);
		System.out.println(login.getPassword());
		System.out.println(login.getUserName());
		
		status = login.searchUser();
		
		return status;
	}
}
