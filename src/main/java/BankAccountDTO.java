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

    public BankAccountDTO(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BankAccountDTO) {
            BankAccountDTO bankAccountDTOObj = (BankAccountDTO) o;
            return ((bankAccountDTOObj.getBalance() == this.balance) && (bankAccountDTOObj.getAccountNumber().equals(this.accountNumber)));
        }
        return false;
    }
}
