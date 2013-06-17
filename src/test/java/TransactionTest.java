import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Calendar;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/17/13
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionTest {
    TransactionDao mockTransactionDao = mock(TransactionDao.class);
    Calendar mockTime = mock(Calendar.class);
    final String accountNumber = "1234567890";

    @Before
    public void setUp() {
        reset(mockTransactionDao);
        reset(mockTime);
        Transaction.setTransactionDao(mockTransactionDao);
        Transaction.setTimeSystem(mockTime);
    }

    @Test
    public void testDepositLog() {
        double amount = 50;
        long timeStamp = 10000;
        String log = "deposited 50";
        when(mockTime.getTimeInMillis()).thenReturn(timeStamp);
        ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> amountMoneyCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Long> timeStampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);

        Transaction.depositedLog(accountNumber, amount, log);
        verify(mockTransactionDao).depositedLog(accountNumberCaptor.capture(), amountMoneyCaptor.capture(), timeStampCaptor.capture(), logCaptor.capture());
        assertEquals(accountNumberCaptor.getValue(),accountNumber);
        assertEquals(amountMoneyCaptor.getValue().doubleValue(),amount,0.001);
        assertEquals(timeStampCaptor.getValue().longValue(),timeStamp);
        assertEquals(logCaptor.getValue(),log);
    }

    @Test
    public void testWithdrawLog() {
        double amount = 50;
        long timeStamp = 10000;
        String log = "withdraw 50";
        when(mockTime.getTimeInMillis()).thenReturn(timeStamp);
        ArgumentCaptor<String> accountNumberCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> amountMoneyCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Long> timeStampCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);

        Transaction.withdrawLog(accountNumber, amount, log);
        verify(mockTransactionDao).withdrawLog(accountNumberCaptor.capture(), amountMoneyCaptor.capture(), timeStampCaptor.capture(), logCaptor.capture());
        assertEquals(accountNumberCaptor.getValue(),accountNumber);
        assertEquals(amountMoneyCaptor.getValue().doubleValue(),-amount,0.001);
        assertEquals(timeStampCaptor.getValue().longValue(),timeStamp);
        assertEquals(logCaptor.getValue(),log);
    }
}
