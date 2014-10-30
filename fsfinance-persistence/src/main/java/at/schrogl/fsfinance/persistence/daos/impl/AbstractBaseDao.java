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
package at.schrogl.fsfinance.persistence.daos.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Common base class for all DAOs. Provides some basic data access.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 * @param <T>
 *            Entity class, the DAO is acting on
 */
public abstract class AbstractBaseDao<T> {

	protected EntityManager em;

	/**
	 * Sanity method. Checks if {@link AbstractBaseDao#em} is set and throws a
	 * <code>NullPointerException</code> if not.
	 * 
	 * @throws NullPointerException
	 *             If the entity manager for the DAO is not set
	 */
	protected void checkEntityManagerNotNull() throws NullPointerException {
		if (em == null) {
			throw new NullPointerException("EntityManager not set! DAO not functional!");
		}
	}

	// =================================================================
	// Data Access
	// =================================================================

	public abstract T getById(Long id);

	public abstract List<T> listAll();

	public void persist(T entity) {
		checkEntityManagerNotNull();
		em.persist(entity);
	}

	public void persist(Collection<T> entities) {
		checkEntityManagerNotNull();
		for (T entity : entities) {
			em.persist(entity);
		}
	}

	public void remove(T entity) {
		checkEntityManagerNotNull();
		em.remove(entity);
	}

	public void remove(Collection<T> entities) {
		checkEntityManagerNotNull();
		for (T entity : entities) {
			em.remove(entity);
		}
	}

	// =================================================================
	// Getter + Setter
	// =================================================================

	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

}
