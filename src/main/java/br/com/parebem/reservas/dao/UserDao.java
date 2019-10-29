package br.com.parebem.reservas.dao;
import br.com.parebem.reservas.model.User;
import br.com.parebem.reservas.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


public class UserDao {

	private EntityManager em;

	public UserDao(EntityManager em) {
		this.em = em;
		this.em.getTransaction().begin();
	}
	
	public void startEntityManager() {
		this.em = new JpaUtil().getEntityManager();
		this.em.getTransaction().begin();
	}
	
	public void insertUser(User u) {
		this.em.persist(u);
		this.em.getTransaction().commit();
		this.em.close();
	}
	
	public List<User> listAllUsers(){
		String jpql = "select u from User u";
		TypedQuery<User> query = this.em.createQuery(jpql, User.class);
		
		return query.getResultList();
	}
	
	public Boolean userExist(String name, String password) {
		String jpql = "select u from User u " + 
					"where u.name = :pName and u.password = :pPassword";
		TypedQuery<User> query = this.em.createQuery(jpql, User.class);
		query.setParameter("pName", name);
		query.setParameter("pPassword", password);
		
		List<User> user = query.getResultList();
		
		Boolean userExist = null;
		if(user.size() > 0) {
			userExist = true;
		}else {
			userExist = false;
		}
		System.out.println("The user existence is: "+ userExist);
		return userExist;
	}
}
