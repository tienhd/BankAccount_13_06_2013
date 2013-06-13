import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: sqv-nbt
 * Date: 6/13/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountTest {
    BankAccountDao mockBankAccountDao = mock(BankAccountDao.class);

    @Before
    public void setUp() {
        BankAccount.setBankAccountDao(mockBankAccountDao);
    }

    @Test
    public void testOpenNewAccountThenPersistToDB() {
        String accountNumber = "1234567890";
        BankAccount.openAccount(accountNumber);

        ArgumentCaptor<BankAccountDTO> bankAccountCaptor = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockBankAccountDao).save(bankAccountCaptor.capture());
        assertEquals(bankAccountCaptor.getValue().getAccountNumber(),accountNumber);
        assertEquals(bankAccountCaptor.getValue().getBalance(),0,0.001);
    }
}
