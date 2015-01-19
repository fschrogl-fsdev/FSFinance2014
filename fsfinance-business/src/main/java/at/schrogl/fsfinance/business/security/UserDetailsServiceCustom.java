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

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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

	/*
	 * XXX PasswordEncoder does not implement Serializable, hence this class can not be serialized.
	 * This is/might be a problem if the user's session gets persisted, since this class is, through
	 * class UserManagement, is injected into class HomeHandler, which is part of a user's session.
	 * 
	 * Maybe this should be fixed somehow. For now enable <Manager pathname="" /> in Tomcat's
	 * context.xml to disable persistent sessions.
	 */
	
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
	@Transactional(value = TxType.REQUIRED, dontRollbackOn = UsernameNotFoundException.class)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Trying to load user '{}' for Spring Security", username);
		User reqUser = userDao.findByUsernameIgnoreCase(username);
		if (reqUser == null) {
			String errMsg = String.format("A user named '%s' doesn't exist! Unable to load user.", username);
			LOGGER.info(errMsg);
			throw new UsernameNotFoundException(errMsg);
		} else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			for (Authorities role : reqUser.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(role.toString()));
			}
			LOGGER.debug("Loaded {} authorities for user '{}'", authorities.size(), reqUser);

			return new UserDetailsCustom(reqUser, authorities);
		}
	}

	/**
	 * Logs in the given user. The login will be bound to the current
	 * session/thread.
	 * 
	 * @param user
	 *            The {@link User} that should be logged in
	 * 
	 * @throws UsernameNotFoundException
	 *             If the given user is not persisted, i.e. his/her username
	 *             doesn't exist
	 */
	@Transactional(TxType.REQUIRED)
	public void loginUser(User user) {
		user = (user != null) ? user : new User();
		UserDetails details = loadUserByUsername(user.getUsername());
		Authentication auth = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * Gets the currently logged in {@link User} with respect to the current
	 * session/thread.
	 * 
	 * @return The logged in user or <code>null</code> if the user isn't logged
	 *         in
	 */
	@Transactional(TxType.SUPPORTS)
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof UserDetailsCustom)
			return ((UserDetailsCustom) auth.getPrincipal()).getPrincipal();
		else
			return null;
	}

	/**
	 * Terminates the session for the currently logged in {@link User}. If there
	 * is no currently logged in user this method does nothing.
	 */
	@Transactional(TxType.SUPPORTS)
	public void logoutCurrentUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/**
	 * Uses the configured Spring Security <code>PasswordEncoder</code> to
	 * encrypt the given password/string.
	 * 
	 * @param rawPassword
	 *            The password/string to encrypt
	 * @return The encrypted password/string. Contains the version of the
	 *         encryption algorithm used, the number of encryption round, the
	 *         salt used and the actual encrypted password
	 */
	@Transactional(TxType.SUPPORTS)
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
