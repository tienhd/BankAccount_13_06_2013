/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {

    private String accountNumber;
    private double balance;

    public BankAccountDTO(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
