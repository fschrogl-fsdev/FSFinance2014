package at.schrogl.fsfinance.persistence.daos;

import java.util.List;

import at.schrogl.fsfinance.persistence.entities.User;

/**
 * DAO class for entity {@link User}
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 * 
 */
public class UserDao extends AbstractBaseDao<User> {

	// Names of NamedQueries
	public static final String NQ_ByUsername = "user_ByUsername";
	public static final String NQ_ByEmail = "user_ByEmail";

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

	public List<User> getByEMail(String email) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByEmail, User.class)
				.setParameter("email", email)
				.getResultList();
	}

}
