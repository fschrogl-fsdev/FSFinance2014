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
package at.schrogl.fsfinance.persistence.enums;

import at.schrogl.fsfinance.persistence.entities.User;

/**
 * Enumeration of all roles that can be assigned to an {@link User}
 * <p>
 Authorities are used to determine if an user is authorized to access specific
 resources.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public enum Authorities {

	ROLE_USER,
	ROLE_ADMIN;

}
