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

import at.schrogl.fsfinance.persistence.daos.TransactionDao;
import at.schrogl.fsfinance.persistence.entities.Transaction;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit testcase for {@link TransactionDao}
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 */
public class TestTransactionDao extends AbstractTestDao {

	private static TransactionDao transactionDao;

	// =================================================================
	// Setup + Teardown
	// =================================================================

	@BeforeClass
	public static void setupDao() {
		transactionDao = new TransactionDao();
		transactionDao.setEntityManager(em);
	}

	// =================================================================
	// Tests
	// =================================================================

	@Test
	public void testGetByDate() {
		Transaction expected = transactionDao.getById(1L);
		assertNotNull(expected);

		List<Transaction> actuals = transactionDao.getByDate(expected.getDate());
		assertTrue(actuals.contains(expected));
	}

	@Test
	public void testListAll() {
		List<Transaction> allTransactions = transactionDao.listAll();
		assertEquals(3, allTransactions.size());
	}

}
