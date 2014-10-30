package at.schrogl.fsfinance.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import at.schrogl.fsfinance.persistence.daos.IUserDao;
import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.test.rules.PersistenceInitRule;

public class UserDaoTest {

	private IUserDao dao;

	@ClassRule
	public static PersistenceInitRule<IUserDao> pmInit = new PersistenceInitRule<>();

	@Before
	public void setup() {
		dao = pmInit.getDaoProxy(IUserDao.class);
		pmInit.startTransaction();
	}

	@After
	public void tearDown() {
		pmInit.stopTransaction();
	}
	
	@Test
	public void testFindByUsername() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		for (User expectedUser : allUsers) {
			User actualUser = dao.findByUsername(expectedUser.getUsername());
			assertEquals("Expected user: " + expectedUser + "; Actual user: " + actualUser, expectedUser, actualUser);
		}

		User actualUser = dao.findByUsername("X");
		assertNull("Expected user: null; Actual user: " + actualUser, actualUser);
	}

	@Test
	public void testFindByEmail() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		for (User expectedUser : allUsers) {
			User actualUser = dao.findByEmail(expectedUser.getEmail());
			assertEquals("Expected user: " + expectedUser + "; Actual user: " + actualUser, expectedUser, actualUser);
		}

		User actualUser = dao.findByEmail("X");
		assertNull("Expected user: null; Actual user: " + actualUser, actualUser);
	}

	@Test
	public void testFindByForenameOrSurname() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		List<User> actualUsers = dao.findByForenameOrSurname("joe1", null);
		assertEquals(1, actualUsers.size());

		actualUsers = dao.findByForenameOrSurname(null, "doe");
		assertEquals(2, actualUsers.size());
	}

	@Test
	public void testFindByForenameOrSurname_Pageable() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		Page<User> actualUsers = dao.findByForenameOrSurname(null, "doe", new PageRequest(1, 1));
		assertEquals(1, actualUsers.getContent().size());
	}

	@Test
	public void testFindByForenameAndSurname() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		List<User> actualUsers = dao.findByForenameAndSurname("joe1", "doe");
		assertEquals(1, actualUsers.size());

		actualUsers = dao.findByForenameAndSurname(null, "doe");
		assertEquals(0, actualUsers.size());
	}

}
