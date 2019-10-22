package br.com.parebem.reservas.modelo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Calendar expirationMoment;
	
	@Column(name="state", nullable=false, columnDefinition = "varchar(20) default 'ABERTO'  ")
	@Enumerated(value= EnumType.STRING)
	private EventState state;
	
	@ManyToMany
	private List<User> userEvents;
	
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

	public Calendar getExpirationMoment() {
		return expirationMoment;
	}

	public void setExpirationMoment(Calendar expirationMoment) {
		this.expirationMoment = expirationMoment;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

}
