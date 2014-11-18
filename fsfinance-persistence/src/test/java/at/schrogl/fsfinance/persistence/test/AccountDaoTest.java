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

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import at.schrogl.fsfinance.persistence.daos.IAccountDao;
import at.schrogl.fsfinance.persistence.entities.Account;
import at.schrogl.fsfinance.persistence.test.rules.PersistenceInitRule;

public class AccountDaoTest {

	private IAccountDao dao;
	private List<Account> allAccounts;

	@ClassRule
	public static PersistenceInitRule<IAccountDao> pmInit = new PersistenceInitRule<>();

	@Before
	public void setup() {
		dao = pmInit.getDaoProxy(IAccountDao.class);
		pmInit.startTransaction();

		this.allAccounts = dao.findAll();
		assertEquals("Wrong number of accounts in database!", 3, this.allAccounts.size());
	}

	@After
	public void tearDown() {
		pmInit.stopTransaction();
		this.allAccounts = Collections.emptyList();
	}

	@Test
	public void testFindByName() {
		// Query all accounts explicitly
		for (Account expectedAccount : allAccounts) {
			Account actualAccount = dao.findByName(expectedAccount.getName());
			assertEquals("assert_1", expectedAccount, actualAccount);
		}

		// Query should be case sensitive
		String accountNameUppercase = allAccounts.get(0).getName().toUpperCase();
		Account actualAccount = dao.findByName(accountNameUppercase);
		assertEquals("assert_2", null, actualAccount);

		// An account's name must not be null, so this has to return null
		assertEquals("assert_3", null, dao.findByName(null));
	}

	@Test
	public void testFindByDescriptionLikeIgnoreCase() {
		// Query using full description
		List<Account> actualAccounts = dao.findByDescriptionLikeIgnoreCase(allAccounts.get(0).getDescription());
		assertEquals("assert_1", 1, actualAccounts.size());

		// Query should be case insensitive
		actualAccounts = dao.findByDescriptionLikeIgnoreCase(allAccounts.get(0).getDescription().toUpperCase());
		assertEquals("assert_2", 1, actualAccounts.size());

		// Query using part of description
		actualAccounts = dao.findByDescriptionLikeIgnoreCase("%ac.2%");
		assertEquals("assert_3", 1, actualAccounts.size());

		// Query using part of description (case insensitive)
		actualAccounts = dao.findByDescriptionLikeIgnoreCase("%AC.2%");
		assertEquals("assert_4", 1, actualAccounts.size());
	}

}
