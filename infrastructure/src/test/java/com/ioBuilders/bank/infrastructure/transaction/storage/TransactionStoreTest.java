package com.ioBuilders.bank.infrastructure.transaction.storage;


import com.ioBuilders.bank.domain.transaction.model.Transaction;
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
        Transaction transaction = new Transaction("OriginAccount", "DestinationAccount", 50, new Date());
        transactionStore.createTransaction(transaction); // TODO: CHANGE create To "Save"
        Transaction transactionRead = this.transactionStore.getTransactionsByAccount("OriginAccount", 0).get(0);
        assertTrue(transactionRead.equals(transaction));
    }
}
