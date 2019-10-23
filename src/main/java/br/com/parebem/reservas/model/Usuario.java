package br.com.parebem.reservas.modelo;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import br.com.parebem.reservas.dao.Dao;

public class Usuario {
	private int id_usuario;
	private String nome;
	private String telefone;
	
	public Usuario() {
		
	}

	public Usuario(int id_ususario, String nome, String telefone) {
		this.id_usuario = id_ususario;
		this.nome = nome;
		this.telefone = telefone;
	}
	
	private String toJson(ArrayList<HashMap<String, String>> linhas) {
		return new Gson().toJson(linhas);
	}
	
	public String listaUsuarios() {
		String sql = "select * from usuario;";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toJson(resultado);
	}
	
	public String criarUsuario(String nome, String senha, String telefone) {
		System.out.println("Modelo criar usuário");
		String sql = "insert into usuario(nome, senha, telefone) values( '"+ 
					nome + "' , '" + senha + "' , '" + telefone + "' );";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toJson(resultado);
	}
	
	public String idNomeUsuarios() {
		String sql = "select id_usuario, nome from usuario;";
		ArrayList<HashMap<String, String>> resultado = Dao.executaComando(sql);
		return this.toJson(resultado);
	}
}
