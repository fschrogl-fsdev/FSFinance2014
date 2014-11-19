package at.schrogl.fsfinance.persistence.test.container;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import at.schrogl.fsfinance.persistence.daos.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class DaoContainerTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testContainerBeans() {
		IUserDao userDao = applicationContext.getBean(IUserDao.class);
		assertEquals("assert_1", 3, userDao.findAll().size());
	}

}
