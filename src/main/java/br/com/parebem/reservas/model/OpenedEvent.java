package br.com.parebem.reservas.model;

import java.util.Date;

public class OpenedEvent {

	private int eventId;
	private Date expirationMoment;
	
	public OpenedEvent(int eventId, Date expirationMoment) {
		this.eventId = eventId;
		this.expirationMoment = expirationMoment;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Date getExpirationMoment() {
		return expirationMoment;
	}

	public void setExpirationMoment(Date expirationMoment) {
		this.expirationMoment = expirationMoment;
	}
	
	
	
}	