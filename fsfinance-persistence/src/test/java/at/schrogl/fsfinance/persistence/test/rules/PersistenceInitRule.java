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
package at.schrogl.fsfinance.persistence.test.rules;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.rules.ExternalResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

/**
 * A JUnit Rule for setting up the entity manager and Spring's Data JPA
 * repository factory. Should be used as a class rule.
 *
 * @param <I>
 *            The {@link JpaRepository}-extending interface under test
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public class PersistenceInitRule<I> extends ExternalResource {

	private I dao;
	private JpaRepositoryFactory repoFactory;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction tx;

	@Override
	protected void before() throws Throwable {
		entityManagerFactory = Persistence.createEntityManagerFactory("fsfinance-test");
		entityManager = entityManagerFactory.createEntityManager();
		repoFactory = new JpaRepositoryFactory(entityManager);
	}

	@Override
	protected void after() {
		if (entityManager != null)
			entityManager.close();
		if (entityManagerFactory != null)
			entityManagerFactory.close();
	}

	public void startTransaction() {
		stopTransaction();
		tx = entityManager.getTransaction();
		tx.begin();
		tx.setRollbackOnly();
	}

	public void stopTransaction() {
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
		tx = null;
	}

	public I getDaoProxy(Class<I> daoProxy) {
		return (dao != null) ? dao : repoFactory.getRepository(daoProxy);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public JpaRepositoryFactory getJpaRepoFactory() {
		return repoFactory;
	}

	public EntityTransaction getTransaction() {
		return tx;
	}

}
