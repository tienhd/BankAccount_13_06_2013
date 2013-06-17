import org.dbunit.DataSourceBasedDBTestCase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDao {
    private Connection connection;
    public BankAccountDao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
    public void save(BankAccountDTO bankAccountDTO) {

    }

    public BankAccountDTO getAccount(String accountNumber) throws SQLException {
        String queryString = "SELECT * FROM BANK_ACCOUNT WHERE ACCOUNT_NUMBER='" + accountNumber + "'";
        ResultSet resultSet = connection.createStatement().executeQuery(queryString);
        if(resultSet.next())
            return new BankAccountDTO (accountNumber,resultSet.getDouble("balance"), resultSet.getString("description"));
        else
            return null;
    }

    public void save(BankAccountDTO capture, String capture1) {

    }




}
