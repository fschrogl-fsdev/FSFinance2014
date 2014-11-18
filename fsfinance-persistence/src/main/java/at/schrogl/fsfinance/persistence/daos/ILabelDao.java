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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import at.schrogl.fsfinance.persistence.entities.Label;

public interface ILabelDao extends JpaRepository<Label, Long> {

	public List<Label> findByName(String name);

	public Page<Label> findByName(String name, Pageable pageable);

	public List<Label> findByDescriptionIgnoreCase(String description);

	public Page<Label> findByDescriptionIgnoreCase(String description, Pageable pageable);

	public List<Label> findByDescriptionLikeIgnoreCase(String description);

	public Page<Label> findByDescriptionLikeIgnoreCase(String description, Pageable pageable);

}
