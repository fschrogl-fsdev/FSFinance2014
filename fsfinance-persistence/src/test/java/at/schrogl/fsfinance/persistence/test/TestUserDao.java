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

import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit testcase for {@link UserDao}.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 */
public class TestUserDao extends AbstractTestDao {

	private static UserDao userDao;

	// =================================================================
	// Setup + Teardown
	// =================================================================

	@BeforeClass
	public static void setupDao() {
		userDao = new UserDao();
		userDao.setEntityManager(em);
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
		User expected = userDao.getById(1L);
		assertNotNull(expected);

		User actual = userDao.getByEMail(expected.getEmail());
		assertEquals(expected, actual);
	}

	@Test
	public void testListAll() {
		List<User> allUsers = userDao.listAll();
		assertEquals(2, allUsers.size());
	}

}
