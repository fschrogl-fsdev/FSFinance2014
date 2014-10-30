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
package at.schrogl.fsfinance.persistence.testa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTestDao {

	protected static EntityManagerFactory emf;
	protected static EntityManager em;
	protected EntityTransaction tx;

	@BeforeClass
	public static void setupClass() {
		emf = Persistence.createEntityManagerFactory("fsfinance");
		em = emf.createEntityManager();
	}

	@AfterClass
	public static void teardownClass() {
		if (em != null)
			em.close();
		if (emf != null)
			emf.close();
	}

	@Before
	public void setup() {
		tx = em.getTransaction();
		tx.begin();
		tx.setRollbackOnly();
	}

	@After
	public void teardown() {
		if (tx.isActive()) {
			tx.rollback();
		}
	}

}
