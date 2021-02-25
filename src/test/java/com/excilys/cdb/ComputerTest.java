package test.java.com.excilys.cdb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.excilys.cdb.dao.ComputerDAO;

public class ComputerTest {
	
	@Test
	public void testFinById() {
		String name = ComputerDAO.getInstance().getComputerById(577).get().getName();
		assertEquals("g", name);
	}
}

