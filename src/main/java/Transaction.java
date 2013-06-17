import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private static TransactionDao transactionDao;
    private static Calendar timeSystem;

    public static void setTransactionDao(TransactionDao transactionDao) {
        Transaction.transactionDao = transactionDao;
    }

    public static void setTimeSystem(Calendar timeSystem) {
        Transaction.timeSystem = timeSystem;
    }

    public static void depositedLog(String accountNumber, double amount, String log) {
        long timeStamp = timeSystem.getTimeInMillis();
        transactionDao.depositedLog(accountNumber,amount,timeStamp,log);
    }
}
