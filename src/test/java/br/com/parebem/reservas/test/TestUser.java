package br.com.parebem.reservas.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.parebem.reservas.modelo.User;
import br.com.parebem.reservas.util.JpaUtil;

public class TestUser {

	@Test
	public void usersAttributesInsertionTest() {
		String name = "Leonardo";
		String telephone = "99887878";
		String password = "senhasenha";
		
		User user = new User();
		user.setName(name);
		user.setTelephone(telephone);
		user.setPassword(password);
		
		EntityManager em = new JpaUtil().getEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		
		User insertedUser = em.find(User.class, user.getUser_id());
		
		em.remove(insertedUser);
		em.getTransaction().commit();
		
		em.close();
		
		assertEquals(insertedUser.getName(), name);
		assertEquals(insertedUser.getTelephone(), telephone);
		assertEquals(insertedUser.getPassword(), password);
	}
	
}
