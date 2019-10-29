package br.com.parebem.reservas.testdao;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.parebem.reservas.dao.EventDao;
import br.com.parebem.reservas.dao.UserDao;
import br.com.parebem.reservas.model.Event;
import br.com.parebem.reservas.model.EventState;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.util.JpaUtil;

public class RegisterInEventTest {
	
	private Event event;
	private EntityManager em;
	
	@Before
	public void createNewEvent() {
		EventDao dao = new EventDao();
		Event e = new Event();
		e.setState(EventState.ABERTO);
		dao.insertEvent(e);
		this.event = e;
		
		this.em = new JpaUtil().getEntityManager();
	}
	
	@After
	public void deleteEvent() {
		EntityManager em = new JpaUtil().getEntityManager();
		this.event = em.find(Event.class, this.event.getEventId());
		em.getTransaction().begin();
		em.remove(this.event);
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void checkIfUserIsInsertedInEvent() {
		User u = new User();
		u.setName("lio");
		u.setTelephone("+5574");
		u.setPassword("oioi");
		UserDao dao = new UserDao(new JpaUtil().getEntityManager());
		dao.insertUser(u);
		
		Event e = this.em.find(Event.class, this.event.getEventId());
		e.addUser(u);
		Set<User> set = e.getUserEvents();
		assertTrue(set.size() >= 1);
		e.removeUser(u);
		
		this.em.getTransaction().begin();
		u = this.em.find(User.class, u.getUser_id());
		this.em.remove(u);
		this.em.getTransaction().commit();
		this.em.close();
	}
	
	@Test
	public void checkIfNewEventHasNoUsersTest() {
		Event e = this.em.find(Event.class, this.event.getEventId());
		Set<User> eventUsers = e.getUserEvents();
		assertTrue(eventUsers.size() == 0);
	}
	
	public void getAllUsersFromOneEventTest() {
		EventDao dao = new EventDao();
		Event e = new Event();
		dao.insertEvent(e);
		int idEvent = e.getEventId();
		
		EntityManager em = new JpaUtil().getEntityManager();
		em.find(Event.class, idEvent);
	}
	
	
	 public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Leonardo ");
		Event event = new Event();
		EntityManager em = new JpaUtil().getEntityManager();
		Event e = em.find(Event.class, event.getEventId());
		User u = new User();
		u.setName("lio");
		u.setTelephone("+5574");
		u.setPassword("oioi");
		UserDao dao = new UserDao(new JpaUtil().getEntityManager());
		dao.insertUser(u);
		System.out.println(u);
		
		EventDao dao1 = new EventDao();
		dao1.insertEvent(e);
		System.out.println(e);
		e.addUser(u);
		assertTrue(u.getUser_id() != null);
		Set<User> eventUsers = e.getUserEvents();
	
		
		u = em.find(User.class, u.getUser_id());
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
		em.close();
	}
	
}
