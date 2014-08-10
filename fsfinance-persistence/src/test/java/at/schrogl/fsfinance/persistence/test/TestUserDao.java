package at.schrogl.fsfinance.persistence.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;

/**
 * Unit testcase for {@link UserDao}.
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 * 
 */
public class TestUserDao {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static UserDao userDao;

	private EntityTransaction tx;

	// =================================================================
	// Setup + Teardown
	// =================================================================

	@BeforeClass
	public static void setupClass() {
		emf = Persistence.createEntityManagerFactory("fsfinance");
		em = emf.createEntityManager();

		userDao = new UserDao();
		userDao.setEntityManager(em);
	}

	@AfterClass
	public static void teardownClass() {
		em.close();
		emf.close();
	}

	@Before
	public void setup() {
		tx = em.getTransaction();
		tx.begin();
		tx.setRollbackOnly();
	}

	@After
	public void teardown() {
		if (tx.isActive()) {
			tx.rollback();
		}
	}

	// =================================================================
	// Tests
	// =================================================================

	@Test
	public void testGetByUsername() {
		User user1 = userDao.getById(1L);
		assertNotNull(user1);
		assertNotNull(userDao.getByUsername(user1.getUsername()));
	}

	@Test
	public void testGetByEMail() {
		User user1 = userDao.getById(1L);
		assertNotNull(user1);

		List<User> users = userDao.getByEMail(user1.getEmail());
		assertTrue(users.size() >= 1);
	}

	@Test
	public void testListAll() {
		List<User> users = userDao.listAll();
		assertTrue(users.size() >= 1);
	}

}
