package br.com.parebem.reservas.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String telephone;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(mappedBy = "userEvents")
	private Set<Event> events = new HashSet<Event>();
	
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUser_id() {
		return user_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		String text = "";
		text += "{";
		text += "\"user_id\":\"" + this.user_id + "\",";
		text += "\"name\":\"" + this.name + "\",";
		text += "\"password\":\"" + this.password + "\",";
		text += "\"telephone\":\"" + this.telephone + "\"";
		text += "}";
		return text;
	}
	
	
}
