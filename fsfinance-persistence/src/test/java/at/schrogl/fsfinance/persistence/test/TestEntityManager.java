package at.schrogl.fsfinance.persistence.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import at.schrogl.fsfinance.entities.Account;
import at.schrogl.fsfinance.entities.User;

public class TestEntityManager {

	@Test
	public void test() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("fsfinance");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		User user = new User();
		user.setUsername("maerowinger");
		user.setPassword("sha1:lkjasdfijlk");
		user.setSalt("asdf");

		Account account = new Account();
		account.setName("1st-account");
		account.setUser(user);
		
		em.persist(user);
		em.persist(account);
		tx.commit();
		
		em.close();
		emf.close();
	}

}
