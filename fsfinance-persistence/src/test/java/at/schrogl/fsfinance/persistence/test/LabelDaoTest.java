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
package at.schrogl.fsfinance.persistence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import at.schrogl.fsfinance.persistence.daos.ILabelDao;
import at.schrogl.fsfinance.persistence.entities.Label;
import at.schrogl.fsfinance.persistence.test.rules.PersistenceInitRule;

public class LabelDaoTest {

	private ILabelDao dao;
	private List<Label> allLabels;

	@ClassRule
	public static PersistenceInitRule<ILabelDao> pmInit = new PersistenceInitRule<>();

	@Before
	public void setup() {
		dao = pmInit.getDaoProxy(ILabelDao.class);
		pmInit.startTransaction();

		this.allLabels = dao.findAll();
		assertEquals("Wrong number of labels in database!", 3, this.allLabels.size());
	}

	@After
	public void tearDown() {
		pmInit.stopTransaction();
		this.allLabels = Collections.emptyList();
	}

	@Test
	public void testFindByName() {
		// Query all labels explicitly
		for (Label expectedLabel : allLabels) {
			List<Label> actualLabels = dao.findByName(expectedLabel.getName());
			assertTrue("assert_1", actualLabels.contains(expectedLabel));
		}

		// Query should be case sensitive
		String labelNameUppercase = allLabels.get(0).getName().toUpperCase();
		List<Label> actualLabels = dao.findByName(labelNameUppercase);
		assertEquals("assert_2", 0, actualLabels.size());

		// A label's name must not be null, so this has to return null
		assertEquals("assert_3", 0, dao.findByName(null).size());
	}

	@Test
	public void testFindByDescription() {
		// Query using full description
		List<Label> actualLabels = dao.findByDescriptionIgnoreCase(allLabels.get(0).getDescription());
		assertEquals("assert_1", 1, actualLabels.size());

		// Query should be case insensitive
		actualLabels = dao.findByDescriptionIgnoreCase(allLabels.get(0).getDescription().toUpperCase());
		assertEquals("assert_2", 1, actualLabels.size());

		// Query using part of description
		actualLabels = dao.findByDescriptionIgnoreCase("%copy%");
		assertEquals("assert_3", 0, actualLabels.size());
	}

	@Test
	public void testFindByDescriptionLike() {
		// Query using full description
		List<Label> actualLabels = dao.findByDescriptionLikeIgnoreCase(allLabels.get(0).getDescription());
		assertEquals("assert_1", 1, actualLabels.size());

		// Query should be case insensitive
		actualLabels = dao.findByDescriptionLikeIgnoreCase(allLabels.get(0).getDescription().toUpperCase());
		assertEquals("assert_2", 1, actualLabels.size());

		// Query using part of description
		actualLabels = dao.findByDescriptionLikeIgnoreCase("%copy%");
		assertEquals("assert_3", 1, actualLabels.size());

		// Query using part of description (case insensitive)
		actualLabels = dao.findByDescriptionLikeIgnoreCase("%cOpy%");
		assertEquals("assert_4", 1, actualLabels.size());
	}

}
