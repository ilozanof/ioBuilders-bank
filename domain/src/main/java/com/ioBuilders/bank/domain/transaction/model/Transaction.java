package com.ioBuilders.bank.domain.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author i.fernandez@nchain.com
 *
 * A Transaction represents a movement of money from one Account to another.
 * Accounts might NOT belong to this Bank.
 *
 * This class is immutable
 */
@Data
@AllArgsConstructor
public class Transaction {
    private final String originAccountId;
    private final String destinationAccountId;
    private final float amount;
    private final Date date;

    @Override
    public int hashCode() {
        return Objects.hash(originAccountId, destinationAccountId, amount, date.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Transaction other) {
            return Objects.equals(originAccountId, other.originAccountId)
                    && Objects.equals(destinationAccountId, other.destinationAccountId)
                    && Objects.equals(amount, other.amount)
                    && Objects.equals(this.date.getTime(), other.date.getTime());
        } else return false;
    }
}
