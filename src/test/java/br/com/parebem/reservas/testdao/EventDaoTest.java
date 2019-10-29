package br.com.parebem.reservas.testdao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;

import br.com.parebem.reservas.dao.EventDao;
import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.EventState;
import br.com.parebem.reservas.util.JpaUtil;

public class EventDaoTest {
	
	private Event event;
	private EntityManager em;
	
	@Before
	public void initializeData() {
		Event event = new Event();
		event.setExpirationMoment(new Date());
		event.setGuestLimit(3);
		event.setPlace("My home");
		event.setState(EventState.ABERTO);
		this.event = event;
		
		this.em = new JpaUtil().getEntityManager();
	}
	
	@Test
	public void eventInsertionTest() {
		EventDao dao = new EventDao();
		dao.insertEvent(this.event);
		int eventId = this.event.getEventId();
		
		EntityManager em = new JpaUtil().getEntityManager();
		Event insertedEvent = em.find(Event.class, eventId);
		em.getTransaction().begin();
		
		em.remove(insertedEvent);
		em.getTransaction().commit();
		em.close();
		
		assertNotNull(insertedEvent);
	}

	@Test
	public void expiryEventTest() {
		EventDao dao = new EventDao();
		
		dao.insertEvent(this.event);
		dao.renewEntityManager();
		String status = dao.expiryEvent(this.event.getEventId());
		
		this.em.getTransaction().begin();
		this.event = this.em.merge(this.event);
		this.em.remove(this.event);
		this.em.getTransaction().commit();
		this.em.close();
		assertTrue(status.compareTo("ok") == 0);
	}
	
	@Test
	public void listAllEventsTest() {
		EventDao dao = new EventDao();
		
		dao.insertEvent(this.event);
		dao.renewEntityManager();
		Event e = new Event();
		e.setGuestLimit(20);
		e.setState(EventState.ABERTO);
		dao.insertEvent(e);
		dao.renewEntityManager();
		List<Event> events = dao.listAllEvents();
		
		EntityManager em = new JpaUtil().getEntityManager();
		em.getTransaction().begin();
		Event event = em.find(Event.class, this.event.getEventId());
		em.remove(event);
		
		e = em.find(Event.class, e.getEventId());
		em.remove(e);
		em.getTransaction().commit();
		em.close();
		
		assertTrue(events.size() >= 2);
	}
	
	@Test
	public void expiryNotExistingEventTest() {
		EventDao dao = new EventDao();
		String status = dao.expiryEvent(this.event.getEventId());
		
		this.em.getTransaction().begin();
		this.em.remove(this.event);
		this.em.getTransaction().commit();
		this.em.close();
		assertTrue(status.compareTo("user not found") == 0);
	}
}
