package br.com.parebem.reservas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.EventState;
import br.com.parebem.reservas.model.OpenedEvent;
import br.com.parebem.reservas.util.JpaUtil;

public class EventDao {

	private EntityManager em;

	public EventDao() {
		this.em = new JpaUtil().getEntityManager();
	}

	public List<OpenedEvent> getOpenedEvents() {
		String jpql = "select new " + 
				"br.com.parebem.reservas.model.OpenedEvent(e.eventId, e.expirationMoment) " + 
				" from Event e where e.state = :pState";
		TypedQuery<OpenedEvent> query = this.em.createQuery(jpql, OpenedEvent.class);
		query.setParameter("pState", EventState.ABERTO);
		
		List<OpenedEvent> openedEvents = query.getResultList();
		return openedEvents;
	}
	
	public List<Event> listAllEvents() {
		String jpql = "select e from Event e";
		TypedQuery<Event> query = this.em.createQuery(jpql, Event.class);
		List<Event> events = query.getResultList();
		return events;
	}
	
	public Event insertEvent(Event e) {
		this.openEntityManager();
		this.em.persist(e);
		Event insertedEvent = this.em.find(Event.class, e.getEventId());
		this.commitEntityManager();
		this.closeEntityManager();
		
		return insertedEvent;
	}
	
	public String expiryEvent(int eventId) {
		System.out.println("In expiry event dao method");
		this.openEntityManager();
		Event e = new Event();
		e = this.em.find(Event.class, eventId);
		
		String status = null;
		if(e != null) {
			e.setState(EventState.FECHADO);
			this.commitEntityManager();
			this.closeEntityManager();
			
			System.out.println("After execute command");
			status = "ok";
		}else {
			status = "user not found";
		}
		
		return status;
	}
	
	public void renewEntityManager() {
		this.em = new JpaUtil().getEntityManager();
	}
	
	private void openEntityManager() {
		this.em.getTransaction().begin();
	}
	
	private void commitEntityManager() {
		this.em.getTransaction().commit();
	}
	
	private void closeEntityManager() {
		this.em.close();
	}	
}
