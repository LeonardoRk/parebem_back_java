package br.com.parebem.reservas.testdao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.parebem.reservas.dao.UserDao;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.util.JpaUtil;

public class UserDaoTest {

	private EntityManager em;
	private User userTest;

	@Before
	public void initEntityManager() {
		this.em = new JpaUtil().getEntityManager();
		User u = new User();
		u.setName("Leo");
		u.setPassword("pass");
		u.setTelephone("+5561998869788");
		this.userTest = u;
	}
	
	@After
	public void finishEntityManager() {
		this.em.getTransaction().commit();
		this.em.close();
	}
	
	@Test
	public void userInsertionTest() {
		UserDao dao = new UserDao(this.em);
		dao.insertUser(this.userTest);
		
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
		User inserted = this.em.find(User.class, this.userTest.getUser_id());
		
		User u = this.em.find(User.class, this.userTest.getUser_id());
		this.em.remove(u);
		assertNotNull(inserted);
	}
	
	@Test 
	public void userNotExistenceTest(){
		UserDao dao = new UserDao(this.em);
		dao.insertUser(this.userTest);
		
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
		
		User u = this.em.find(User.class, this.userTest.getUser_id());
		this.em.remove(u);
		this.em.getTransaction().commit();
		this.em.close();
		String login = this.userTest.getName();
		String password = this.userTest.getPassword();
		
		
		EntityManager em = new JpaUtil().getEntityManager();
		UserDao dao2 = new UserDao(em);
		Boolean exist = dao2.userExist(login, password);

		assertFalse(exist);
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
	}
	
	@Test 
	public void userExistenceTest(){
		UserDao dao = new UserDao(this.em);
		dao.insertUser(this.userTest);
		
		String login = this.userTest.getName();
		String password = this.userTest.getPassword();
		dao.startEntityManager();
		Boolean exist = dao.userExist(login, password);
		
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
		User u = this.em.merge(this.userTest);
		this.em.remove(u);
		assertTrue(exist);
	}
	
	@Test
	public void testUserList() {
		UserDao dao = new UserDao(this.em);
		
		this.userTest.setName("Jarbas 2");
		dao.insertUser(this.userTest);
		
		this.em = new JpaUtil().getEntityManager();
		UserDao dao2 = new UserDao(this.em);
		List<User> users = dao2.listAllUsers();
		
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
		User u = this.em.find(User.class, this.userTest.getUser_id());
		this.em.remove(u);
		assertTrue(users.size() > 0);
	}
}
