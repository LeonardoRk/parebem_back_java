package br.com.parebem.reservas.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("novo_controle");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
