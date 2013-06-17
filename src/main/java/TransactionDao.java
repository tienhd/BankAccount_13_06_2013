import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDao {

    public void depositedLog(String accountNumber, double amount, long timeStamp, String log) {

    }

    public void withdrawLog(String accountNumber, double amount, long timeStamp, String log) {

    }

    public ArrayList<TransactionDTO> getTransactionOccurred(String accountNumber) {
        return null;
    }

    public ArrayList<TransactionDTO> getTransactionOccurred(String accountNumber, long startTime, long endTime) {
        return null;
    }
}
