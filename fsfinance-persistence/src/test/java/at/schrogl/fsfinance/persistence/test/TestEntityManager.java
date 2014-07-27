package at.schrogl.fsfinance.persistence.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class TestEntityManager {

	@Test
	public void test() {
		try {
			/*
			Configuration hbCfg = new Configuration().configure();
			SessionFactory sf = hbCfg.buildSessionFactory(new
					StandardServiceRegistryBuilder().build());
			Session hbSession = sf.getCurrentSession();
			hbSession.close();
			sf.close();
			*/

			EntityManagerFactory emf =
					Persistence.createEntityManagerFactory("fsfinance");
			EntityManager em = emf.createEntityManager();
			em.close();

		} catch (Throwable ex) {
			System.err.println(ex);
			ex.printStackTrace();
		}

		// SchemaExport hbm2ddl = new SchemaExport(hbCfg);
		// hbm2ddl.create(true, false);
	}

}
