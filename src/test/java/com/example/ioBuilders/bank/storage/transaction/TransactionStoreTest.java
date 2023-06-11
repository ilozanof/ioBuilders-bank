package com.example.ioBuilders.bank.storage.transaction;

import com.example.ioBuilders.bank.domain.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author i.fernandez@nchain.com
 *
 * Testing Suite for the Transaction Store Adapter (H2 in-memory DB)
 */
@SpringBootTest
public class TransactionStoreTest {
    @Autowired
    private TransactionStore transactionStore;

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
