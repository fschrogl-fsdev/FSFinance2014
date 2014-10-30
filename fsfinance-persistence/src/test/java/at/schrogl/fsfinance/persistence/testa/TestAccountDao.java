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
package at.schrogl.fsfinance.persistence.testa;

import at.schrogl.fsfinance.persistence.daos.impl.AccountDao;
import at.schrogl.fsfinance.persistence.entities.Account;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit testcase for {@link AccountDao}
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
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
