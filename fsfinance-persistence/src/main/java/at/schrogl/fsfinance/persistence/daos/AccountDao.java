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

import at.schrogl.fsfinance.persistence.entities.Account;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA-enabled repository for {@link Account} entities.
 * <p>
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public interface AccountDao extends JpaRepository<Account, Long> {

	/**
	 * Finds an {@link Account} by name. Query is case sensitive.
	 * <p>
	 * @param name
	 *             The account name to search for.
	 * @return
	 *         The matching account entity or <code>null</code>
	 */
	public Account findByName(String name);

	/**
	 * Finds {@link Account}s by description. Query is case in-sensitive and supports wildcards.
	 * <p>
	 * @param description
	 *                    The description to search for. Use '%' as wildcard char, e.g. "%substring%"
	 * @return
	 *         List of accounts or an empty list
	 */
	public List<Account> findByDescriptionLikeIgnoreCase(String description);

	/**
	 * Finds {@link Account}s by description. Query is case in-sensitive and supports wildcards.
	 * <p>
	 * @param description
	 *                    The description to search for. Use '%' as wildcard char, e.g. "%substring%"
	 * @param pageable
	 *                    Optional. Handing a {@link PageRequest} object to the method enables paging.
	 * @return
	 *         A certain page containing a list of accounts or an empty list
	 */
	public Page<Account> findByDescriptionLikeIgnoreCase(Pageable pageable, String description);

}
