package at.schrogl.fsfinance.persistence.daos;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Common base class for all DAOs. Provides some basic data access.
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 * 
 * @param <T>
 *            Entity class, the DAO is acting on
 */
public abstract class AbstractBaseDao<T> {

	/** Unique name/id for the named query "listAll (rows)" */
	public static final String NQ_ListAll = "entity_ListAll";

	protected EntityManager em;

	/**
	 * Sanity method. Checks if {@link AbstractBaseDao#em} is set and throws a
	 * NPE if not.
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

	public void saveOrUpdate(T entity) {
		checkEntityManagerNotNull();
		em.persist(entity);
	}

	public void saveOrUpdate(Collection<T> entities) {
		checkEntityManagerNotNull();
		for (T entity : entities) {
			em.persist(entity);
		}
	}

	public void delete(T entity) {
		checkEntityManagerNotNull();
		em.remove(entity);
	}

	public void delete(Collection<T> entities) {
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
