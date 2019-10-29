package br.com.parebem.reservas.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.gson.JsonObject;

import br.com.parebem.reservas.dao.UserDao;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.util.GeneralServices;
import br.com.parebem.reservas.util.JpaUtil;

public class UserService {
	
	public String createUser(String content) {
		System.out.println("Received user content");
		
		try {
			JsonObject json = GeneralServices.toJson(content);
			System.out.println(json);
			
			String name = json.get("name").toString().replace("\"", "");
			String telephone = json.get("telephone").toString().replace("\"", "");
			String password = json.get("password").toString().replace("\"", "");
			
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setTelephone(telephone);
			
			UserDao dao = new UserDao(new JpaUtil().getEntityManager());
			dao.insertUser(user);
			System.out.println("User inserted");
			return "ok";
		}catch (Exception e) {
			System.out.println("Exception found to create user");
			System.out.println(e.getMessage());
			return "not ok";
		}
	}

	public List<User> getAllUsers() {
		System.out.println("Method get all users");
		EntityManager em = new JpaUtil().getEntityManager();
		UserDao dao = new UserDao(em);
		List<User>users =  dao.listAllUsers();
		System.out.println("Returning all users");
		return users;
	}
}
