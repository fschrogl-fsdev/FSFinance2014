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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import at.schrogl.fsfinance.business.security.UserDetailsServiceCustom;
import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;

@Component
public class UserManagement implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagement.class);

	private UserDetailsServiceCustom securityDao;
	private UserDao userDao;

	public User register(final User newUser, final String passwordPlain) {
		// TODO Generate salt and encrypt password

		// Create/register user
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
