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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import at.schrogl.fsfinance.persistence.daos.AccountDao;
import at.schrogl.fsfinance.persistence.entities.Account;

/**
 * Unit testcase for {@link AccountDao}
 * 
 * @author Fritz Schrogl
 * @since 0.1
 * 
 */
public class TestAccountDao extends AbstractTestDao {

	private static AccountDao accountDao;

	// =================================================================
	// Setup + Teardown
	// =================================================================

	@BeforeClass
	public static void setupDao() {
		accountDao = new AccountDao();
		accountDao.setEntityManager(em);
	}

	// =================================================================
	// Tests
	// =================================================================

	@Test
	public void testGetByName() {
		Account expected = accountDao.getById(1L);
		assertNotNull(expected);

		Account actual = accountDao.getByName(expected.getName());
		assertEquals(expected, actual);
	}

	@Test
	public void testListAll() {
		List<Account> allAccounts = accountDao.listAll();
		assertEquals(2, allAccounts.size());
	}

}