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
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import at.schrogl.fsfinance.persistence.daos.UserDao;
import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.enums.Authorities;

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

	private PasswordEncoder pwdEncoder;
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
		LOGGER.debug("Login-Query for user '{}'", username);
		User reqUser = userDao.findByUsernameIgnoreCase(username);
		if (reqUser == null) {
			String errMsg = String.format("A user named '%s' doesn't exist!", username);
			LOGGER.info(errMsg);
			throw new UsernameNotFoundException(errMsg);
		} else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			for (Authorities role : reqUser.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(role.toString()));
			}
			LOGGER.debug("Loaded {} authorities for user '{}'", authorities.size(), username);
			
			return new UserDetailsCustom(reqUser, authorities);
		}
	}

	public void loginUser(User user) {
		UserDetails details = loadUserByUsername(user.getUsername());
		Authentication auth = new UsernamePasswordAuthenticationToken(details, details.getPassword(),
				details.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsCustom userDetails = (UserDetailsCustom) auth.getPrincipal();
		return userDetails.getPrincipal();
	}

	public void logoutCurrentUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public String encryptPassword(String rawPassword) {
		return pwdEncoder.encode(rawPassword);
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public void setPwdEncoder(PasswordEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
