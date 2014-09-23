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

import at.schrogl.fsfinance.persistence.entities.User;

/**
 * DAO class for entity {@link User}
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 */
public class UserDao extends AbstractBaseDao<User> {

	// Names of NamedQueries
	public static final String NQ_ListAll = "user.listAll";
	public static final String NQ_ByUsername = "user.ByUsername";
	public static final String NQ_ByEmail = "user.ByEmail";

	@Override
	public User getById(Long id) {
		checkEntityManagerNotNull();
		return em.find(User.class, id);
	}

	@Override
	public List<User> listAll() {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ListAll, User.class)
				.getResultList();
	}

	public User getByUsername(String username) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByUsername, User.class)
				.setParameter("username", username)
				.getSingleResult();
	}

	public User getByEMail(String email) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByEmail, User.class)
				.setParameter("email", email)
				.getSingleResult();
	}

}
