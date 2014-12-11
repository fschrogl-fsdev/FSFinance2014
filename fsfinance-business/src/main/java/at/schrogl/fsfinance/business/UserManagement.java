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

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import at.schrogl.fsfinance.business.exceptions.UserAlreadyExistsException;
import at.schrogl.fsfinance.business.security.UserDetailsServiceCustom;
import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;

@Component
public class UserManagement implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagement.class);

	private UserDetailsServiceCustom securityDao;
	private UserDao userDao;

	@Transactional(value = TxType.REQUIRED, dontRollbackOn = UserAlreadyExistsException.class)
	public User register(final User newUser, final String rawPassword) throws UserAlreadyExistsException {
		if (newUser == null || rawPassword == null)
			throw new IllegalArgumentException("Method's arguments must not be null!");

		// Check if user already exists
		if (userDao.findByUsername(newUser.getUsername()) != null) {
			LOGGER.warn("Can't create user '{}'. Username already exists", newUser.getUsername());;
			throw new UserAlreadyExistsException(newUser.getUsername());
		}

		// Generate hashed password and enable user
		String hashedPassword = securityDao.encryptPassword(rawPassword);
		LOGGER.debug("Generated hashed password for user '{}': {}", newUser.getUsername(), hashedPassword);
		newUser.setPassword(hashedPassword);
		newUser.setEnabled(Boolean.TRUE);

		// Persist user
		LOGGER.debug("Persisting new user '{}'", newUser.getUsername());
		return userDao.save(newUser);
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	@Inject
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Inject
	public void setSecurityDao(UserDetailsServiceCustom securityDao) {
		this.securityDao = securityDao;
	}

}
