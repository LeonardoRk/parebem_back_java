package br.com.parebem.reservas.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.parebem.reservas.dao.UserDao;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.util.JpaUtil;

public class TestUser {
	
	private EntityManager em;
	
	@Before
	public void initEntityManager() {
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
	}
	
	@After
	public void closeConnection() {
		this.em.close();
	}
	
	@Test
	public void entityManagerMethodTest() {
		assertNotNull(this.em);
	}
	
	@Test
	public void listUsersFromDatabaseTest() {
		this.em = new JpaUtil().getEntityManager();
		UserDao dao = new UserDao(this.em);
		dao.listAllUsers();
		System.out.println(dao.listAllUsers());
	}

	@Test
	public void usersAttributesInsertionTest() {
		String name = "Leonardo";
		String telephone = "99887878";
		String password = "senhasenha";
		
		User user = new User();
		user.setName(name);
		user.setTelephone(telephone);
		user.setPassword(password);
		System.out.println("Inicio de teste");
		
		this.em.persist(user);
		
		
		User insertedUser = em.find(User.class, user.getUser_id());
		
		this.em.remove(insertedUser);
		this.em.getTransaction().commit();
		
		assertEquals(insertedUser.getName(), name);
		assertEquals(insertedUser.getTelephone(), telephone);
		assertEquals(insertedUser.getPassword(), password);
	}
	
}
