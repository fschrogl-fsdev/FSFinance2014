package at.schrogl.fsfinance.persistence.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

/**
 * Unit testcase for the EntityManager. The sole purpose of this test class is
 * to check if the EntityManager can be instantiated successfully.
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 * 
 */
public class TestEntityManager {

	@Test
	public void test() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("fsfinance");
		EntityManager em = emf.createEntityManager();

		em.close();
		emf.close();
	}

}
