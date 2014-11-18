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
package at.schrogl.fsfinance.persistence.daos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import at.schrogl.fsfinance.persistence.entities.Booking;

public interface IBookingDao extends JpaRepository<Booking, Long> {

	public List<Booking> findByDate(Date date);

	public Page<Booking> findByDate(Date date, Pageable pageable);

	public List<Booking> findByDateLessThan(Date date);

	public Page<Booking> findByDateLessThan(Date date, Pageable pageable);

	public List<Booking> findByDateGreaterThan(Date date);

	public Page<Booking> findByDateGreaterThan(Date date, Pageable pageable);

	public List<Booking> findByDateBetween(Date from, Date to);

	public Page<Booking> findByDateBetween(Date from, Date to, Pageable pageable);

	public List<Booking> findByAmountLessThan(BigDecimal amount);

	public Page<Booking> findByAmountLessThan(BigDecimal amount, Pageable pageable);

	public List<Booking> findByAmountGreaterThan(BigDecimal amount);

	public Page<Booking> findByAmountGreaterThan(BigDecimal amount, Pageable pageable);

	public List<Booking> findByAmountBetween(BigDecimal from, BigDecimal to);

	public Page<Booking> findByAmountBetween(BigDecimal from, BigDecimal to, Pageable pageable);

	public List<Booking> findByDateBetweenAndAmountBetween(Date fromDate, Date toDate, BigDecimal fromAmount,
			BigDecimal toAmount);

	public Page<Booking> findByDateAndAmountBetween(Date fromDate, Date toDate, BigDecimal fromAmount,
			BigDecimal toAmount, Pageable pageable);

}
