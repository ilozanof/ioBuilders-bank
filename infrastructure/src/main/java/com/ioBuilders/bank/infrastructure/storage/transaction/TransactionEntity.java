package com.ioBuilders.bank.infrastructure.storage.transaction;

import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author i.fernandez@nchain.com
 *
 * Entity class to persist Transactions
 */
@Entity
@Data
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue
    private int id;
    private String originAccountId;
    private String destinationAccountId;
    private float amount;
    private Date date;

    public TransactionEntity(Transaction transaction) {
        this.id = transaction.getId();
        this.originAccountId = transaction.getOriginAccountId();
        this.destinationAccountId = transaction.getDestinationAccountId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
    }

    public TransactionEntity(TransactionRequest transactionReq) {
        this.originAccountId = transactionReq.getOriginAccountId();
        this.destinationAccountId = transactionReq.getDestinationAccountId();
        this.amount = transactionReq.getAmount();
        this.date = transactionReq.getDate();
    }

    public Transaction toDomain() {
        return new Transaction(this.id, this.originAccountId, this.destinationAccountId, this.amount, this.date);
    }
}
