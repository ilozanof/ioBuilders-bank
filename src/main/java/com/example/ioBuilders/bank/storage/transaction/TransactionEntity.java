package com.example.ioBuilders.bank.storage.transaction;

import com.example.ioBuilders.bank.domain.transaction.Transaction;
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
        this.originAccountId = transaction.getOriginAccountId();
        this.destinationAccountId = transaction.getDestinationAccountId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
    }

    public Transaction toDomain() {
        return new Transaction(this.originAccountId, this.destinationAccountId, this.amount, this.date);
    }
}
