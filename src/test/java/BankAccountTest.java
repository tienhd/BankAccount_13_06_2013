import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountTest {
    BankAccountDao mockBankAccountDao = mock(BankAccountDao.class);
    TransactionDao mockTransactionDao = mock(TransactionDao.class);

    final String accountNumber = "1234567890";
    @Before
    public void setUp() {
        reset(mockBankAccountDao);
        reset(mockTransactionDao);
        BankAccount.setBankAccountDao(mockBankAccountDao);
        BankAccount.setTransactionDao(mockTransactionDao);
    }

    @Test
    public void testOpenNewAccountThenPersistToDB() {

        BankAccount.openAccount(accountNumber);

        ArgumentCaptor<BankAccountDTO> bankAccountCaptor = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockBankAccountDao).save(bankAccountCaptor.capture());
        assertEquals(bankAccountCaptor.getValue().getAccountNumber(),accountNumber);
        assertEquals(bankAccountCaptor.getValue().getBalance(),0,0.001);
    }

    @Test
    public void testGetTheAccountInformation() {


        BankAccountDTO answerBankAccountDTO = new BankAccountDTO(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(answerBankAccountDTO);

        BankAccountDTO accountFromDB = BankAccount.getAccount(accountNumber);

        assertEquals(answerBankAccountDTO.getAccountNumber(),accountFromDB.getAccountNumber());
        assertEquals(answerBankAccountDTO.getBalance(),accountFromDB.getBalance(),0.001);

    }

    @Test
    public void testGetAccountInformationArgument() {
        BankAccount.getAccount(accountNumber);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockBankAccountDao).getAccount(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue(),accountNumber);

    }

    @Test
    public void testDepositMoneyToAccount() {
        double depositBalance = 50;
        String log = "deposited 50 to account";

        BankAccountDTO answerBankAccountDTO = new BankAccountDTO(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(answerBankAccountDTO);

        BankAccount.deposit(accountNumber,depositBalance,log);
        ArgumentCaptor<BankAccountDTO> accountDTOCaptor = ArgumentCaptor.forClass(BankAccountDTO.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockBankAccountDao).save(accountDTOCaptor.capture(),stringArgumentCaptor.capture());
        assertEquals(accountDTOCaptor.getValue().getAccountNumber(),accountNumber);
        assertEquals(accountDTOCaptor.getValue().getBalance(),50,0.001);
        assertEquals(stringArgumentCaptor.getValue(),log);
    }

    @Test
    public void testWithdrawMoneyToAccount() {
        double withdrawBalance = 50;
        String log = "withdraw 50 from account";

        BankAccountDTO answerBankAccountDTO = new BankAccountDTO(accountNumber,100);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(answerBankAccountDTO);

        BankAccount.withdraw(accountNumber,withdrawBalance,log);
        ArgumentCaptor<BankAccountDTO> accountDTOCaptor = ArgumentCaptor.forClass(BankAccountDTO.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockBankAccountDao).save(accountDTOCaptor.capture(),stringArgumentCaptor.capture());
        assertEquals(accountDTOCaptor.getValue().getAccountNumber(),accountNumber);
        assertEquals(accountDTOCaptor.getValue().getBalance(),50,0.001);
        assertEquals(stringArgumentCaptor.getValue(),log);
    }

    @Test
    public void testGetTransactionOccurred() {

        ArrayList<TransactionDTO> listTransaction = new ArrayList<TransactionDTO>();

        TransactionDTO transactionDTO1 = new TransactionDTO(accountNumber,50,10001,"deposited 50");
        TransactionDTO transactionDTO2 = new TransactionDTO(accountNumber,150,10100,"deposited 150");
        TransactionDTO transactionDTO3 = new TransactionDTO(accountNumber,-20,10500,"withdraw 20");

        listTransaction.add(transactionDTO1);
        listTransaction.add(transactionDTO2);
        listTransaction.add(transactionDTO3);

        when(mockTransactionDao.getTransactionOccurred(accountNumber)).thenReturn(listTransaction);
        ArrayList<TransactionDTO> resultList = BankAccount.getTransactionOccurred(accountNumber);
        int i = 0;
        for (TransactionDTO tr: listTransaction) {
            assertEquals(tr,resultList.get(i));
            i++;
        }

        ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockTransactionDao).getTransactionOccurred(accountNumberCaptor.capture());
        assertEquals(accountNumberCaptor.getValue(),accountNumber);
    }

    @Test
    public void testGetTransactionBetween2Times() {
        long startTime = 10000;
        long endTime = 10200;
        ArrayList<TransactionDTO> listTransaction = new ArrayList<TransactionDTO>();

        TransactionDTO transactionDTO1 = new TransactionDTO(accountNumber,50,10001,"deposited 50");
        TransactionDTO transactionDTO2 = new TransactionDTO(accountNumber,150,10100,"deposited 150");
        TransactionDTO transactionDTO3 = new TransactionDTO(accountNumber,-20,10500,"withdraw 20");

        listTransaction.add(transactionDTO1);
        listTransaction.add(transactionDTO2);
        when(mockTransactionDao.getTransactionOccurred(accountNumber,startTime,endTime)).thenReturn(listTransaction);
        ArrayList<TransactionDTO> resultList = BankAccount.getTransactionOccurred(accountNumber,startTime,endTime);
        int i = 0;
        for (TransactionDTO tr: listTransaction) {
            assertEquals(tr,resultList.get(i));
            i++;
        }

        ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> startTimeCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> endTimeCaptor = ArgumentCaptor.forClass(Long.class);
        verify(mockTransactionDao).getTransactionOccurred(accountNumberCaptor.capture(),startTimeCaptor.capture(),endTimeCaptor.capture());
        assertEquals(accountNumberCaptor.getValue(),accountNumber);
        assertEquals(startTimeCaptor.getValue().longValue(),startTime);
        assertEquals(endTimeCaptor.getValue().longValue(),endTime);
    }

    @Test
    public void testGetNTransactions() {
        int n = 2;
        ArrayList<TransactionDTO> listTransaction = new ArrayList<TransactionDTO>();

        TransactionDTO transactionDTO1 = new TransactionDTO(accountNumber,50,10001,"deposited 50");
        TransactionDTO transactionDTO2 = new TransactionDTO(accountNumber,150,10100,"deposited 150");
        TransactionDTO transactionDTO3 = new TransactionDTO(accountNumber,-20,10500,"withdraw 20");

        listTransaction.add(transactionDTO1);
        listTransaction.add(transactionDTO2);

        when(mockTransactionDao.getTransactionOccurred(accountNumber,n)).thenReturn(listTransaction);
        ArrayList<TransactionDTO> resultList = BankAccount.getTransactionOccurred(accountNumber,n);
        int i = 0;
        for (TransactionDTO tr: listTransaction) {
            assertEquals(tr,resultList.get(i));
            i++;
        }

        ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> numberCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockTransactionDao).getTransactionOccurred(accountNumberCaptor.capture(),numberCaptor.capture());

        assertEquals(accountNumberCaptor.getValue(),accountNumber);
        assertEquals(numberCaptor.getValue().intValue(),n);

    }
}
