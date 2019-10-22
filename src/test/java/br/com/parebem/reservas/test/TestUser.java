package br.com.parebem.reservas.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.parebem.reservas.modelo.User;

public class TestaUser {

	public static void main(String[] args) {
		User user = new User();
		user.setNome("Leonardo");
		user.setTelefone("9988987786");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("novo_controle");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
	}
}
