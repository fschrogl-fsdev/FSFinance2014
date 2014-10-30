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
		entityManagerFactory = Persistence.createEntityManagerFactory("fsfinance");
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
