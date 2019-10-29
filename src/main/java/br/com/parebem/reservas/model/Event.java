package br.com.parebem.reservas.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	private String place;
	private int guestLimit;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationMoment;
	
	@Column(name="state", nullable=false, columnDefinition = "varchar(20) default 'ABERTO'  ")
	@Enumerated(value= EnumType.STRING)
	private EventState state;
	
	@ManyToMany
	@JoinTable(joinColumns = {@JoinColumn(name="fk_event")}, 
							inverseJoinColumns = {@JoinColumn(name="fk_user")})
	private Set<User> userEvents = new HashSet<User>();
	
	public void addUser(User u) {
		this.userEvents.add(u);
		u.getEvents().add(this);
	}
	
	public void removeUser(User u) {
		this.userEvents.remove(u);
		u.getEvents().remove(this);
	}
	
	public Set<User> getUserEvents() {
		return userEvents;
	}

	public void setUserEvents(Set<User> userEvents) {
		this.userEvents = userEvents;
	}

	public int getEventId() {
		return eventId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getGuestLimit() {
		return guestLimit;
	}

	public void setGuestLimit(int guestLimit) {
		this.guestLimit = guestLimit;
	}

	public Date getExpirationMoment() {
		return expirationMoment;
	}

	public void setExpirationMoment(Date expirationMoment) {
		this.expirationMoment = expirationMoment;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		String text = "";
		text += "{";
		text += "\"eventId\":" + this.eventId + ',';
		text += "\"expirationMoment\":\"" + this.expirationMoment + "\",";
		text += "\"guestLimit\":" + this.guestLimit + ',';
		text += "\"place\":\"" + this.place + "\",";
		text += "\"state\":\"" + this.state + "\"";
		text += "}";
		return text;
	}

}
