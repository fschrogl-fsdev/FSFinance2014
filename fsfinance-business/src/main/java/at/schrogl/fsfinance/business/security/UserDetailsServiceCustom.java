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
package at.schrogl.fsfinance.business.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;

/**
 * Provides an {@link UserDetailsService}, used by Spring Security for
 * retrieving {@link UserDetails} objects. <code>UserDetails</code> objects
 * provide all credentials necessary to perform authentication requests.
 * <p>
 * Beyond that this object is used to encapsulate Spring Security-specifc
 * methods.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
/* Bean definition in spring-security.xml */
public class UserDetailsServiceCustom implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceCustom.class);

	private UserDao userDao;

	/**
	 * This method is used by Spring Security to retrieve a {@link UserDetails}
	 * object for the given <code>username</code>, which is used in turn to
	 * process a pending authentication request.
	 * 
	 * @param username
	 *            The username a <code>UserDetails</code> object is required for
	 * 
	 * @return Custom implementation of <code>UserDetails</code> with all
	 *         necessary information
	 * 
	 * @throws UsernameNotFoundException
	 *             if there exists no user with the given username
	 * 
	 * @see UserDetailsCustom
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Query for user '{}'", username);
		User reqUser = userDao.findByUsername(username);
		if (reqUser == null) {
			throw new UsernameNotFoundException("User '" + username + "' doesn't exist!");
		} else {
			Collection<? extends GrantedAuthority> authorities = Collections.emptySet();
			LOGGER.debug("Loaded {} authorities for user '{}'", authorities.size(), username);
			return new UserDetailsCustom(reqUser, authorities);
		}
	}

	public void loginUser(User user) {
		UserDetails details = loadUserByUsername(user.getUsername());
		Authentication auth = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(),
				details.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public String generateSalt(int length) {
		return null;
	}

	public String encryptPassword(String plainPassword, String salt) {
		return null;
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
