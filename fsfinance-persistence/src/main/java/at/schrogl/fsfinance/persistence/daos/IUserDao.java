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
package at.schrogl.fsfinance.persistence.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import at.schrogl.fsfinance.persistence.entities.User;

public interface IUserDao extends JpaRepository<User, Long> {

	/**
	 * Finds a {@link User} by his/her username. Query is case sensitive.
	 * 
	 * @param username
	 *            Username to search for
	 * @return The {@link User} entity if found, otherwise <code>null</code>
	 */
	public User findByUsername(String username);

	/**
	 * Finds a {@link User} by his/her email address.
	 * 
	 * @param email
	 *            Email address to search for
	 * @return The {@link User} entity if found, otherwise <code>null</code>
	 */
	public User findByEmail(String email);

	/**
	 * Finds all {@link User}s with the given forename or surname. Query is case
	 * insensitive. Queries for <code>null</code> attributes are properly
	 * handled.
	 * 
	 * @param forename
	 *            A user's forename to search for
	 * @param surname
	 *            A user's surname to search for
	 * @return List containing all matching {@link User} entities or an empty
	 *         list
	 */
	public List<User> findByForenameOrSurnameAllIgnoreCase(String forename, String surname);

	/**
	 * Finds all {@link User}s with the given forename or surname. Query is case
	 * insensitive. Queries for <code>null</code> attributes are properly
	 * handled. This method supports paging.
	 * 
	 * @param forename
	 *            A user's forename to search for
	 * @param surname
	 *            A user's surname to search for
	 * @param pageable
	 *            Handing a {@link PageRequest} object to the method enables
	 *            paging. Paging also supports sorting through the {@link Sort}
	 *            class. Paging requires two database queries, first total
	 *            amount of rows in the table are counted, second the requested
	 *            amount of rows is selected.
	 * @return A certain page containing a list with {@link User} entities or an
	 *         empty list
	 */
	public Page<User> findByForenameOrSurnameAllIgnoreCase(String forename, String surname, Pageable pageable);

	/**
	 * Same as {@link #findByForenameOrSurnameAllIgnoreCase(String, String)} but
	 * with <b>and</b> instead of <b>or</b>.
	 */
	public List<User> findByForenameAndSurnameAllIgnoreCase(String forename, String surname);

	/**
	 * Same as
	 * {@link #findByForenameOrSurnameAllIgnoreCase(String, String, Pageable)}
	 * but with <b>and</b> instead of <b>or</b>.
	 */
	public Page<User> findByForenameAndSurnameAllIgnoreCase(String forename, String surname, Pageable pageable);
	
}
