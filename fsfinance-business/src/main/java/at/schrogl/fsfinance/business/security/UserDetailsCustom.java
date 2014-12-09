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

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import at.schrogl.fsfinance.persistence.entities.User;

/**
 * Custom implementation of Spring Security's {@link UserDetails}, which
 * subclasses {@link org.springframework.security.core.userdetails.User} as a
 * template.
 * <p>
 * Spring Security uses this class to perform an authentication. All necessary
 * credentials are available through the implemented {@link UserDetails}
 * interface. Instances of this class are created by
 * {@link UserDetailsServiceCustom}.
 * 
 * @see UserDetailsServiceCustom
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public class UserDetailsCustom extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private final User principal;

	// ================================================================
	// Constructors
	// ================================================================

	public UserDetailsCustom(User principal, Collection<? extends GrantedAuthority> authorities) {
		super(principal.getUsername(), principal.getPassword(), authorities);
		this.principal = principal;
	}

	// ================================================================
	// Getter and Setter
	// ================================================================

	public User getPrincipal() {
		return principal;
	}

}
