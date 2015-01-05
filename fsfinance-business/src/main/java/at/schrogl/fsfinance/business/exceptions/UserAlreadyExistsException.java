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
package at.schrogl.fsfinance.business.exceptions;

import at.schrogl.fsfinance.persistence.entities.User;

/**
 * This exception is thrown if a new user should be created which already
 * exists. Hence the new user would violate at least one unique constraint of
 * the {@link User} entity.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	private final User existingUser;
	private final User newUser;

	public UserAlreadyExistsException(final User existingUser, final User newUser) {
		if (existingUser == null || newUser == null)
			throw new IllegalArgumentException("Arguments must not be null!");

		this.existingUser = existingUser;
		this.newUser = newUser;
	}

	public User getExistingUser() {
		return existingUser;
	}

	public User getNewUser() {
		return newUser;
	}

	public String getOffendingProperty() {
		if (existingUser.getUsername().equalsIgnoreCase(newUser.getUsername()))
			return newUser.getUsername();
		else
			return newUser.getEmail();
	}

	@Override
	public String getMessage() {
		return String.format("User '%s' already exists!", existingUser.toString());
	}
}
