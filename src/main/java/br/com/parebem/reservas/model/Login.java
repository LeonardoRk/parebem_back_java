package br.com.parebem.reservas.model;

import java.util.ArrayList;
import java.util.HashMap;
import br.com.parebem.reservas.dao.Dao;

public class Login {
	private String userName = null;
	private String password = null;
	
	public Login(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String searchUser() {
		String sql = "select nome, senha from usuario where nome=\"" + this.userName + 
				"\" and senha=\"" + this.password + "\" ; ";
		ArrayList<HashMap<String, String>> retorno = new ArrayList<HashMap<String, String>>();
		retorno = Dao.executaComando(sql);
		System.out.println("O retorno da query");
		System.out.println(retorno);
		int tamanhoArray = retorno.size();
		String status = null;
		if(tamanhoArray > 0) {
			status = "{status:\"ok\"}";
		}else {
			status = "{status:\"nao encontrado\"}";
		}
		return status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
