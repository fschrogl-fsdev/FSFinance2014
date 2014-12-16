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

import at.schrogl.fsfinance.persistence.entities.Label;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA-enabled repository for {@link Label} entities.
 * <p>
 * @author Fritz Schrogl
 * @since 0.1.0
 */
public interface LabelDao extends JpaRepository<Label, Long> {

	/**
	 * Finds {@link Label}s by name. Query is case sensitive.
	 * <p>
	 * @param name
	 *             Label name to search for
	 * @return
	 *         A list of {@link Label}s or an empty list
	 */
	public List<Label> findByName(String name);

	/**
	 * Finds {@link Label}s by name. Query is case sensitive and supports paging.
	 * <p>
	 * @param name
	 *                 Label name to search for
	 * @param pageable
	 *                 Optional. Handing a {@link PageRequest} object to the method enables paging.
	 * @return
	 *         The requested page containing a list with {@link Label}s or an empty list
	 */
	public Page<Label> findByName(Pageable pageable, String name);

	/**
	 * Finds {@link Label}s by their description. Query is case in-sensitive.
	 * <p>
	 * @param description
	 *                    The <b>exact</b> description to search for
	 * @return
	 *         A list of {@link Label}s or an empty list
	 */
	public List<Label> findByDescriptionIgnoreCase(String description);

	/**
	 * Finds {@link Label}s by their description. Query is case in-sensitive.
	 * <p>
	 * @param description
	 *                    The <b>exact</b> description to search for
	 * @param pageable
	 *                    Optional. Handing a {@link PageRequest} object to the method enables paging.
	 * @return
	 *         A certain page containing a list with {@link Label}s entities or an empty list
	 */
	public Page<Label> findByDescriptionIgnoreCase(Pageable pageable, String description);

	/**
	 * Finds {@link Label}s by their description. Query is case in-sensitive and supports searching for substrings within descriptions.
	 * <p>
	 * @param description
	 *                    The description to search for. Use '%' as wildcard char, e.g. "%substring%".
	 * @return
	 *         A list of {@link Label}s or an empty list
	 */
	public List<Label> findByDescriptionLikeIgnoreCase(String description);

	/**
	 * Finds {@link Label}s by their description. Query is case in-sensitive and supports searching for substrings within descriptions.
	 * <p>
	 * @param description
	 *                    The description to search for. Use '%' as wildcard char, e.g. "%substring%".
	 * @param pageable
	 *                    Optional. Handing a {@link PageRequest} object to the method enables paging.
	 * @return
	 *         A list of {@link Label}s or an empty list
	 */
	public Page<Label> findByDescriptionLikeIgnoreCase(Pageable pageable, String description);

}
