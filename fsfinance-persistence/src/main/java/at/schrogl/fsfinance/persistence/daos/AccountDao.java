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

import java.util.List;

import at.schrogl.fsfinance.persistence.entities.Account;

/**
 * DAO class for entity {@link Account}
 * 
 * @author Fritz Schrogl
 * @since 0.1
 * 
 */
public class AccountDao extends AbstractBaseDao<Account> {

	// Names of NamedQueries
	public static final String NQ_ListAll = "account.listAll";
	public static final String NQ_ByName = "account.ByName";

	@Override
	public Account getById(Long id) {
		checkEntityManagerNotNull();
		return em.find(Account.class, id);
	}

	@Override
	public List<Account> listAll() {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ListAll, Account.class)
				.getResultList();
	}

	public Account getByName(String name) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByName, Account.class)
				.setParameter("name", name)
				.getSingleResult();
	}

}
