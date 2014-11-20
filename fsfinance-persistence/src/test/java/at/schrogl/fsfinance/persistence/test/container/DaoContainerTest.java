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
package at.schrogl.fsfinance.persistence.test.container;

import at.schrogl.fsfinance.persistence.daos.AccountDao;
import at.schrogl.fsfinance.persistence.daos.BookingDao;
import at.schrogl.fsfinance.persistence.daos.LabelDao;
import at.schrogl.fsfinance.persistence.daos.UserDao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test of all DAO classes using Spring's application context/container.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DaoContainerTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testContainer_AccountDao() {
		AccountDao accountDao = applicationContext.getBean(AccountDao.class);
		assertNotNull("assert_1", accountDao);
		assertEquals("assert_2", 3, accountDao.findAll().size());
	}

	@Test
	public void testContainer_BookingDao() {
		BookingDao bookingDao = applicationContext.getBean(BookingDao.class);
		assertNotNull("assert_1", bookingDao);
		assertEquals("assert_2", 5, bookingDao.findAll().size());
	}

	@Test
	public void testContainer_LabelDao() {
		LabelDao labelDao = applicationContext.getBean(LabelDao.class);
		assertNotNull("assert_1", labelDao);
		assertEquals("assert_2", 3, labelDao.findAll().size());
	}

	@Test
	public void testContainer_UserDao() {
		UserDao userDao = applicationContext.getBean(UserDao.class);
		assertNotNull("assert_1", userDao);
		assertEquals("assert_2", 3, userDao.findAll().size());
	}

}
