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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

/**
 * Unit testcase for the EntityManager. The sole purpose of this test class is
 * to check if the EntityManager can be instantiated successfully.
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 * 
 */
public class TestEntityManager {

	@Test
	public void test() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("fsfinance");
		EntityManager em = emf.createEntityManager();

		em.close();
		emf.close();
	}

}
