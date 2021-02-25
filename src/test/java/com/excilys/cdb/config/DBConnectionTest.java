package test.java.com.excilys.cdb.config;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import main.java.com.excilys.cdb.dao.ComputerDAO;

public class DBConnectionTest extends DataSourceBasedDBTestCase {
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
    @Override
    protected DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(
          "jdbc:mysql://localhost:3306/computer-database-dbtest");
        dataSource.setUser("admincdb");
        dataSource.setPassword("qwerty1234");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/java/com/excilys/cdb/config/DefaultEntries.xml"));
    }
    
    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
    
    @Test
	public void testMe()  throws Exception {
	    IDataSet expectedDataSet = getDataSet();
	    ITable expectedTable = expectedDataSet.getTable("COMPUTER");
	    
//	    computerDAO.listComputers();
	    
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("COMPUTER");
	    assertEquals(expectedTable, actualTable);
    }

}