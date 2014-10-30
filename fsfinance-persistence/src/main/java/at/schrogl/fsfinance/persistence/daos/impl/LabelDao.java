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
package at.schrogl.fsfinance.persistence.daos.impl;

import java.util.List;

import at.schrogl.fsfinance.persistence.entities.Label;

/**
 * DAO class for entity {@link Label}
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 * 
 */
public class LabelDao extends AbstractBaseDao<Label> {

	// Names of NamedQueries
	public static final String NQ_ListAll = "label.listAll";
	public static final String NQ_ByName = "label.ByName";

	@Override
	public Label getById(Long id) {
		checkEntityManagerNotNull();
		return em.find(Label.class, id);
	}

	@Override
	public List<Label> listAll() {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ListAll, Label.class)
				.getResultList();
	}

	public List<Label> getByName(String name) {
		checkEntityManagerNotNull();
		return em.createNamedQuery(NQ_ByName, Label.class)
				.setParameter("name", name)
				.getResultList();
	}

}
