import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {

    private static BankAccountDao bankAccountDao;
    private static TransactionDao transactionDao;

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.bankAccountDao = bankAccountDao;
    }

    public static void openAccount(String accountNumber) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO(accountNumber);
        bankAccountDao.save(bankAccountDTO);
    }

    public static BankAccountDTO getAccount(String accountNumber) {
        return bankAccountDao.getAccount(accountNumber);
    }

    public static void deposit(String accountNumber, double depositBalance, String log) {
        BankAccountDTO accountDTO = bankAccountDao.getAccount(accountNumber);
        double balance = accountDTO.getBalance() + depositBalance;
        accountDTO.setBalance(balance);
        bankAccountDao.save(accountDTO,log);
    }

    public static void withdraw(String accountNumber, double withdrawBalance, String log) {
        BankAccountDTO accountDTO = bankAccountDao.getAccount(accountNumber);
        double balance = accountDTO.getBalance() - withdrawBalance;
        accountDTO.setBalance(balance);
        bankAccountDao.save(accountDTO,log);
    }

    public static void setTransactionDao(TransactionDao transactionDao) {
        BankAccount.transactionDao = transactionDao;
    }

    public static ArrayList<TransactionDTO> getTransactionOccurred() {
        return transactionDao.getTransactionOccurred();
    }
}
