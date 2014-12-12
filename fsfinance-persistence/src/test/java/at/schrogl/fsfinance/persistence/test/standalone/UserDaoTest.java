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
package at.schrogl.fsfinance.persistence.test.standalone;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.test.rules.PersistenceInitRule;

/**
 * Integration test for class {@link UserDao} usind Spring Data JPA in
 * standalone-mode.
 * <p>
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public class UserDaoTest {

	private UserDao dao;
	private List<User> allUsers;

	@ClassRule
	public static PersistenceInitRule<UserDao> pmInit = new PersistenceInitRule<>();

	@Before
	public void setup() {
		dao = pmInit.getDaoProxy(UserDao.class);
		pmInit.startTransaction();

		this.allUsers = dao.findAll();
		assertEquals("Wrong number of users in database!", 3, this.allUsers.size());
	}

	@After
	public void tearDown() {
		pmInit.stopTransaction();
		this.allUsers = Collections.emptyList();
	}

	@Test
	public void testfindByUsernameIgnoreCase() {
		// Query every user explicitly
		for (User expectedUser : allUsers) {
			User actualUser = dao.findByUsernameIgnoreCase(expectedUser.getUsername());
			assertEquals("assert_1", expectedUser, actualUser);
		}

		// Empty username is not permitted and should always return no user
		assertEquals("assert_2", null, dao.findByUsernameIgnoreCase(null));

		// Query is assumed to be case in-sensitive
		User expectedUser = allUsers.get(0);
		String usernameUpperCase = expectedUser.getUsername().toUpperCase();
		assertEquals("assert_3", dao.findByUsernameIgnoreCase(usernameUpperCase), expectedUser);
	}

	@Test
	public void testfindByEmailIgnoreCase() {
		// Query every user explicitly
		for (User expectedUser : allUsers) {
			User actualUser = dao.findByEmailIgnoreCase(expectedUser.getEmail());
			assertEquals("assert_1", expectedUser, actualUser);
		}

		// Empty email is not permitted and should always return no user
		assertEquals("assert_2", null, dao.findByEmailIgnoreCase(null));

		// Query is assumed to be case sensitive
		User expectedUser = allUsers.get(0);
		String emailUpperCase = expectedUser.getEmail().toUpperCase();
		assertEquals("assert_3", dao.findByEmailIgnoreCase(emailUpperCase), expectedUser);
	}

	@Test
	public void testFindbyUsernameOrEmailAllIgnoreCase() {
		// Query every user explicitly
		for (User expectedUser : allUsers) {
			assertEquals("assert_1", expectedUser, dao.findFirstByUsernameOrEmailAllIgnoreCase(expectedUser.getUsername(), null));
			assertEquals("assert_2", expectedUser, dao.findFirstByUsernameOrEmailAllIgnoreCase(null, expectedUser.getEmail()));
			assertEquals("assert_3", expectedUser,
					dao.findFirstByUsernameOrEmailAllIgnoreCase(expectedUser.getUsername(), expectedUser.getEmail()));
		}
		
		// Empty username or email are not permitted
		assertEquals("assert_4", null, dao.findFirstByUsernameOrEmailAllIgnoreCase(null, null));
		
		// Query is case in-sensitive
		User expectedUser = allUsers.get(0);
		String usernameUpperCase = expectedUser.getUsername().toUpperCase();
		String emailUpperCase = expectedUser.getEmail().toUpperCase();
		assertEquals("assert_5", expectedUser, dao.findFirstByUsernameOrEmailAllIgnoreCase(usernameUpperCase, emailUpperCase));
	}

	@Test
	public void testFindByForenameOrSurnameAllIgnoreCase() {
		List<User> actualUsers = null;

		// All users with forename 'joe1' *or* surname is null
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("joe1", null);
		assertEquals("assert_1", 2, actualUsers.size());

		// All users with forename is null *or* surname 'doe'
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase(null, "doe");
		assertEquals("assert_2", 2, actualUsers.size());

		// All users with forname '' *or* surname ''
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("", "");
		assertEquals("assert_3", 0, actualUsers.size());

		// Query should be case insensitive
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("JoE1", null);
		assertEquals("assert_4", 2, actualUsers.size());

		// Query should be case insensitive
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase(null, "DoE");
		assertEquals("assert_5", 2, actualUsers.size());
	}

	@Test
	public void testFindByForenameOrSurnameAllIgnoreCase_Pageable() {
		Page<User> actualUsers = null;

		// All users with forename 'joe1' *or* surname is null
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("joe1", null, new PageRequest(0, 2));
		assertEquals("assert_1a", 2, actualUsers.getTotalElements());
		assertEquals("assert_1b", 1, actualUsers.getTotalPages());

		// All users with forename is null *or* surname 'doe'
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase(null, "doe", new PageRequest(0, 2));
		assertEquals("assert_2a", 2, actualUsers.getTotalElements());
		assertEquals("assert_2b", 1, actualUsers.getTotalPages());

		// All users with forname '' *or* surname ''
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("", "", new PageRequest(0, 1));
		assertEquals("assert_3a", 0, actualUsers.getTotalElements());
		assertEquals("assert_3b", 0, actualUsers.getTotalPages());

		// Query should be case insensitive
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase("JoE1", null, new PageRequest(0, 1));
		assertEquals("assert_4a", 2, actualUsers.getTotalElements());
		assertEquals("assert_4b", 2, actualUsers.getTotalPages());

		// Query should be case insensitive
		actualUsers = dao.findByForenameOrSurnameAllIgnoreCase(null, "DoE", new PageRequest(0, 1));
		assertEquals("assert_5a", 2, actualUsers.getTotalElements());
		assertEquals("assert_5b", 2, actualUsers.getTotalPages());
	}

	@Test
	public void testFindByForenameAndSurnameAllIgnoreCase() {
		List<User> actualUsers = null;

		// All users with forename 'joe3' *and* surname is null
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe3", null);
		assertEquals("assert_1", 1, actualUsers.size());

		// All users with forename 'joe2' *and* surname is 'doe'
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe2", "doe");
		assertEquals("assert_2", 1, actualUsers.size());

		// All users with forename 'joe1' *and* surname is null
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe1", null);
		assertEquals("assert_3", 0, actualUsers.size());

		// Query should be case insensitive
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("JoE3", null);
		assertEquals("assert_4", 1, actualUsers.size());

		// Query should be case insensitive
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("JoE2", "DOE");
		assertEquals("assert_5", 1, actualUsers.size());
	}

	@Test
	public void testFindByForenameAndSurnameAllIgnoreCase_Pageable() {
		Page<User> actualUsers = null;

		// All users with forename 'joe3' *and* surname is null
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe3", null, new PageRequest(0, 2));
		assertEquals("assert_1a", 1, actualUsers.getTotalElements());
		assertEquals("assert_1b", 1, actualUsers.getTotalPages());

		// All users with forename 'joe2' *and* surname is 'doe'
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe2", "doe", new PageRequest(0, 10));
		assertEquals("assert_2a", 1, actualUsers.getTotalElements());
		assertEquals("assert_2b", 1, actualUsers.getTotalPages());

		// All users with forename 'joe1' *and* surname is null
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("joe1", null, null);
		assertEquals("assert_3a", 0, actualUsers.getTotalElements());
		assertEquals("assert_3b", 1, actualUsers.getTotalPages());

		// Query should be case insensitive
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("JoE3", null, null);
		assertEquals("assert_4a", 1, actualUsers.getTotalElements());
		assertEquals("assert_4b", 1, actualUsers.getTotalPages());

		// Query should be case insensitive
		actualUsers = dao.findByForenameAndSurnameAllIgnoreCase("JoE2", "DOE", null);
		assertEquals("assert_5a", 1, actualUsers.getTotalElements());
		assertEquals("assert_5b", 1, actualUsers.getTotalPages());
	}

}
