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
	public void testFindByForenameOrSurnameAllIgnoreCase() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		List<User> actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("joe1", null);
		assertEquals(2, actualUsers.size());

		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase(null, "doe");
		assertEquals(2, actualUsers.size());
	}

	@Test
	public void testFindByForenameOrSurnameAllIgnoreCase_PageableSort() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		Page<User> actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("joe1", "doe", new PageRequest(0, 1));
		assertEquals(1, actualUsers.getContent().size());
	}

	@Test
	public void testFindByForenameAndSurnameAllIgnoreCase() {
		List<User> allUsers = dao.findAll();
		assertFalse("No users in database!", allUsers.isEmpty());

		List<User> actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe1", "doe");
		assertEquals(1, actualUsers.size());

		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase(null, "doe");
		assertEquals(0, actualUsers.size());
	}

}
