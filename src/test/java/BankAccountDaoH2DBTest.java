import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/17/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDaoH2DBTest {
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final String resourcePath = new File ("").getAbsolutePath() + "/src/test/resource";
    private static Connection persistConnection;
    private static Connection myConnection;

    @BeforeClass
    public static void setUpDriver() throws Exception{
        String schemaFile = resourcePath + "/schema.sql";
        RunScript.execute(JDBC_URL, USER, PASSWORD, schemaFile, Charset.forName("UTF8"), false);
    }

    @Before
    public void cleanInsertData() throws Exception {

        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        IDataSet dataSet = importData();
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    public static IDataSet importData() throws Exception{
        String dataFile = resourcePath + "/data.xml";
        return new FlatXmlDataSetBuilder().build(new FileInputStream(dataFile));

    }

    public static void persistDataSet (ReplacementDataSet replacementDataSet) throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(persistConnection),replacementDataSet);
    }

    private DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
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

    @Test
    public void testFindByAccountNumber() throws Exception {
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO account = bankAccountDao.getAccount("1234567890");

        assertEquals("1234567890", account.getAccountNumber());
    }

}
