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
package at.schrogl.fsfinance.business;

import at.schrogl.fsfinance.business.exceptions.UserAlreadyExistsException;
import at.schrogl.fsfinance.business.security.UserDetailsServiceCustom;
import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.enums.Authorities;
import java.io.Serializable;
import java.util.Properties;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Component;

@Component
public class UserManagement implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagement.class);

	private Properties appConfig;
	private UserDetailsServiceCustom securityDao;
	private UserDao userDao;

	@Transactional(value = TxType.REQUIRED, dontRollbackOn = UserAlreadyExistsException.class)
	public User register(final User newUser, final String rawPassword) throws UserAlreadyExistsException {
		if (newUser == null || rawPassword == null)
			throw new IllegalArgumentException("Method's arguments must not be null!");

		// Check if user already exists
		User existingUser = userDao.findFirstByUsernameOrEmailAllIgnoreCase(newUser.getUsername(), newUser.getEmail());
		if (existingUser != null) {
			LOGGER.warn("Can't create user '{}'. User already exists", existingUser);
			throw new UserAlreadyExistsException(existingUser, newUser);
		}

		// Generate hashed password
		newUser.setPassword(securityDao.encryptPassword(rawPassword));
		LOGGER.debug("Generated hashed password for user '{}': {}", newUser.getUsername(), newUser.getPassword());

		// Set missing properties and authorities
		newUser.setEnabled(Boolean.TRUE);
		newUser.getAuthorities().add(Authorities.ROLE_USER);

		// Make user admin if its the first user in database
		if (userDao.count() == 0L) {
			LOGGER.info("User '{}' is first user to be registered. Adding admin authority!", newUser);
			newUser.getAuthorities().add(Authorities.ROLE_ADMIN);
		}

		// Persist user
		LOGGER.debug("Persisting new user '{}'", newUser.getUsername());
		return userDao.save(newUser);
	}

	@Transactional(value = TxType.REQUIRED)
	public User loginUser(User user) {
		if (user == null)
			throw new IllegalArgumentException("User must not be null!");

		LOGGER.debug("Trying to login user {}", user);
		if (userDao.findOne(user.getId()) != null) {
			LOGGER.info("Loging in user {}", user);
			securityDao.loginUser(user);
		} else {
			LOGGER.warn("Unable to login user {} Loging out current user!", user);
			securityDao.logoutCurrentUser();
		}
		return securityDao.getCurrentUser();
	}

	@Transactional(TxType.REQUIRED)
	public User findByUsername(String username) {
		User foundUser = userDao.findByUsernameIgnoreCase(username);
		LOGGER.debug("Looking for username '{}', found: {}", username, foundUser);
		return foundUser;
	}

	@Transactional(TxType.REQUIRED)
	public User findByEmail(String email) {
		User foundUser = userDao.findByEmailIgnoreCase(email);
		LOGGER.debug("Looking for email '{}', found: {}", email, foundUser);
		return foundUser;
	}

	@Transactional(TxType.NOT_SUPPORTED)
	public User getCurrentUser() {
		return securityDao.getCurrentUser();
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	@Inject
	public void setPropertyPlaceholder(PropertySourcesPlaceholderConfigurer propertyPlaceholder) {
		PropertySources sources = propertyPlaceholder.getAppliedPropertySources();
		PropertySource<?> source = sources.get("localProperties");
		appConfig = (Properties) source.getSource();
	}
	
	@Inject
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Inject
	public void setSecurityDao(UserDetailsServiceCustom securityDao) {
		this.securityDao = securityDao;
	}

}
