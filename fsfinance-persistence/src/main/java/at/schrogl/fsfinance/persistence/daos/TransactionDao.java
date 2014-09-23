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

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import at.schrogl.fsfinance.persistence.entities.Transaction;

/**
 * DAO class for entity {@link Transaction}
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 */
public class TransactionDao extends AbstractBaseDao<Transaction> {

	// Names of NamedQueries
	public static final String NQ_ListAll = "transaction.listAll";
	public static final String NQ_ByDate = "transaction.ByDate";

	@Override
	public Transaction getById(Long id) {
		checkEntityManagerNotNull();
		return em.find(Transaction.class, id);
	}

	@Override
	public List<Transaction> listAll() {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ListAll, Transaction.class)
				.getResultList();
	}

	public List<Transaction> getByDate(Date date) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByDate, Transaction.class)
				.setParameter("date", date, TemporalType.TIMESTAMP)
				.getResultList();
	}

}
