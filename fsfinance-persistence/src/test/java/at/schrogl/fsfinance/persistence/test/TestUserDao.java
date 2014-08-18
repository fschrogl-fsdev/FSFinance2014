/**
 * This file is part of FSFinance.
 *
 * FSFinance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSFinance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSFinance. If not, see <http://www.gnu.org/licenses/>.
 */
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
