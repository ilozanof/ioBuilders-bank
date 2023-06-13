package com.ioBuilders.bank.infrastructure.rest.transaction;


import com.ioBuilders.bank.domain.transaction.model.Transaction;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;


/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class TransactionView extends RepresentationModel<TransactionView> {

    private Transaction transaction;

    public TransactionView(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getFrom() {
        return transaction.getOriginAccountId();
    }
    public String getTo() {
        return transaction.getDestinationAccountId();
    }
    public Date getDate() {return this.transaction.getDate();}
    public float getAmount() { return this.transaction.getAmount();}
}
