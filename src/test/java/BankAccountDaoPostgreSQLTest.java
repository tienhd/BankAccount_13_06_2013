import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/20/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDaoPostgreSQLTest {
    public static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/test";
    public static final String JDBC_SEVER_LOCATION = "localhost";
    public static final String JDBC_DATABASE = "test";

    public static final String JDBC_USERNAME = "postgres";
    public static final String JDBC_PASSWORD = "09020510";


    private static final String resourcePath = new File("").getAbsolutePath() + "/src/test/resource";
    private static Connection myConnection;

    @Before
    public void cleanInsertData() throws Exception {

        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        IDataSet dataSet = importData();
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    public IDataSet importData() throws Exception{
        String dataFile = resourcePath + "/data.xml";
        return new FlatXmlDataSetBuilder().build(new FileInputStream(dataFile));
    }

    private DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(JDBC_SEVER_LOCATION);
        dataSource.setDatabaseName(JDBC_DATABASE);
        dataSource.setUser(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);
        return dataSource;
    }

    @Before
    public void setConnection() throws Exception{
        myConnection = dataSource().getConnection();
    }

    @Test
    public void testOpenNewAccountWithZeroBalance() throws Exception {
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO account = new BankAccountDTO("0123456789");
        bankAccountDao.save(account);

        //test Query to DB
        String queryString = "SELECT * FROM BANK_ACCOUNT WHERE ACCOUNT_NUMBER = '0123456789' AND BALANCE = 0";
        ResultSet resultSet = myConnection.createStatement().executeQuery(queryString);

        assertTrue(resultSet.next());
    }
}
