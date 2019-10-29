package br.com.parebem.reservas.service;

import com.google.gson.JsonObject;

import br.com.parebem.reservas.dao.UserDao;
import br.com.parebem.reservas.util.GeneralServices;
import br.com.parebem.reservas.util.JpaUtil;

public class LoginService {
	
	public static String validateLogin(String content) {		
		JsonObject loginData = GeneralServices.toJson(content.toString());
		System.out.println(loginData);
		
		String userLogin = loginData.get("name").toString().replace("\"", "");
		String userPassword = loginData.get("password").toString().replace("\"", "");
		UserDao dao = new UserDao(new JpaUtil().getEntityManager());
		Boolean status = dao.userExist(userLogin, userPassword);
		String data = "{\"status\":";
		if(status == true) {
			data += "\"ok\"}";
		}else if(status == false) {
			data += "\"usuário não encontrado\"}";
		}else {
			data = null;
		}
		return data;
	}
}
