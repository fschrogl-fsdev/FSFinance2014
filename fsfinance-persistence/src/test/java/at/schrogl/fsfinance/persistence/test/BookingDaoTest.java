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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import at.schrogl.fsfinance.persistence.daos.IBookingDao;
import at.schrogl.fsfinance.persistence.entities.Booking;
import at.schrogl.fsfinance.persistence.test.rules.PersistenceInitRule;

public class BookingDaoTest {

	private IBookingDao dao;
	private List<Booking> allBookings;

	@ClassRule
	public static PersistenceInitRule<IBookingDao> pmInit = new PersistenceInitRule<>();

	@Before
	public void setup() {
		dao = pmInit.getDaoProxy(IBookingDao.class);
		pmInit.startTransaction();

		this.allBookings = dao.findAll();
		assertEquals("Wrong number of bookings in database!", 5, this.allBookings.size());
	}

	@After
	public void tearDown() {
		pmInit.stopTransaction();
		this.allBookings = Collections.emptyList();
	}

	@Test
	public void testFindByDate() {
		// Query all accounts explicitly
		for (Booking expectedTransaction : allBookings) {
			List<Booking> actualTransactions = dao.findByDate(expectedTransaction.getDate());
			assertTrue("assert_1", actualTransactions.contains(expectedTransaction));
		}

		// An transaction's date must not be null, so this has to return null
		assertEquals("assert_2", 0, dao.findByDate(null).size());
	}

	@Test
	public void testFindByDateLessThan() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Transactions prior 2009-05-11 17:50:01
		List<Booking> actualTransactions = dao.findByDateLessThan(sdf.parse("2009-05-11 17:50:01"));
		assertEquals("assert_1", 2, actualTransactions.size());
		assertTrue("assert_2", actualTransactions.contains(dao.findOne(4L)));
		assertTrue("assert_3", actualTransactions.contains(dao.findOne(5L)));
	}

	@Test
	public void testFindByDateGreaterThan() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Transactions after 2014-08-28 12:00:00
		List<Booking> actualTransactions = dao.findByDateGreaterThan(sdf.parse("2014-08-28 12:00:00"));
		assertEquals("assert_1", 2, actualTransactions.size());
		assertTrue("assert_2", actualTransactions.contains(dao.findOne(2L)));
		assertTrue("assert_3", actualTransactions.contains(dao.findOne(3L)));
	}

	@Test
	public void testFindByDateBetween() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// Transactions between 2009-01-01 12:00:00 and 2014-08-10 12:00:00
		List<Booking> actualTransactions = dao.findByDateBetween(sdf.parse("2009-01-01 12:00:00"),
				sdf.parse("2014-08-10 12:00:00"));
		assertEquals("assert_1", 2, actualTransactions.size());
		assertTrue("assert_2", actualTransactions.contains(dao.findOne(1L)));
		assertTrue("assert_3", actualTransactions.contains(dao.findOne(5L)));
	}

	@Test
	public void testFindByAmountLessThan() {
		// Bookings with negative amounts
		List<Booking> actualBookings = dao.findByAmountLessThan(BigDecimal.ZERO);
		assertEquals("assert_1", dao.findOne(1L), actualBookings.get(0));

		// Bookings with amount < 100.00
		actualBookings = dao.findByAmountLessThan(new BigDecimal("100.00"));
		assertEquals("assert_2a", 3, actualBookings.size());
		assertTrue("assert_2b", actualBookings.contains(dao.findOne(1L)));
		assertTrue("assert_2c", actualBookings.contains(dao.findOne(3L)));
		assertTrue("assert_2d", actualBookings.contains(dao.findOne(4L)));
	}

	@Test
	public void testFindByAmountGreaterThan() {
		// Bookings with amount > 100.00
		List<Booking> actualBookings = dao.findByAmountGreaterThan(new BigDecimal("100.00"));
		assertEquals("assert_1a", 2, actualBookings.size());
		assertTrue("assert_1b", actualBookings.contains(dao.findOne(2L)));
		assertTrue("assert_1c", actualBookings.contains(dao.findOne(5L)));
	}

	@Test
	public void testFindByAmountBetween() {
		// Bookings with -5.00 < amount < 67.35
		List<Booking> actualBookings = dao.findByAmountBetween(new BigDecimal("-5.00"), new BigDecimal("67.35"));
		assertEquals("assert_1a", 2, actualBookings.size());
		assertTrue("assert_1b", actualBookings.contains(dao.findOne(3L)));
		assertTrue("assert_1c", actualBookings.contains(dao.findOne(4L)));
	}

	@Test
	public void testFindByDateBetweenAndAmountBetween() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal fromAmount = new BigDecimal("0"), toAmount = new BigDecimal("20");

		// 1972 - 1983
		List<Booking> actualBookings = dao.findByDateBetweenAndAmountBetween(sdf.parse("1972-01-01 00:00:00"),
				sdf.parse("1983-12-01 12:00:00"), fromAmount, toAmount);
		assertEquals("assert_1", 0, actualBookings.size());

		// 2003 - 2016
		actualBookings = dao.findByDateBetweenAndAmountBetween(sdf.parse("2003-01-01 00:00:00"),
				sdf.parse("2016-12-01 12:00:00"), fromAmount, toAmount);
		assertEquals("assert_2a", 1, actualBookings.size());
		assertEquals("assert_2b", dao.findOne(3L), actualBookings.get(0));
	}

}
