package com.ioBuilders.bank.infrastructure.storage.transaction;


import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;
import com.ioBuilders.bank.infrastructure.BankApplicationTest;
import com.ioBuilders.bank.infrastructure.TestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

/**
 * @author i.fernandez@nchain.com
 *
 * Testing Suite for the Transaction Store Adapter (H2 in-memory DB)
 */
@SpringBootTest(classes = BankApplicationTest.class)
@Import(TestConfiguration.class)
public class TransactionStoreTest {
    @Autowired
    private TransactionStoreForTesting transactionStore;

    @BeforeEach
    private void setup() {
        transactionStore.clear();
    }

    @Test
    public void givenTxIsCreated_thenICanRetrieveIt() {
        TransactionRequest transactionReq = new TransactionRequest("OriginAccount", "DestinationAccount", 50, new Date());
        transactionStore.createTransaction(transactionReq); // TODO: CHANGE create To "Save"
        Transaction transactionRead = this.transactionStore.getTransactionsByAccount("OriginAccount", 0).get(0);
        assertEquals(transactionRead.getOriginAccountId(), transactionReq.getOriginAccountId());
        assertEquals(transactionRead.getDestinationAccountId(), transactionReq.getDestinationAccountId());
        assertEquals(transactionRead.getAmount(), transactionReq.getAmount());
        assertEquals(transactionRead.getDate().getTime(), transactionReq.getDate().getTime());
    }
}
