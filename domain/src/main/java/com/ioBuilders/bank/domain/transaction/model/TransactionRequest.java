package com.ioBuilders.bank.domain.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@Data
public class TransactionRequest {
    private static AtomicLong REQUEST_ID = new AtomicLong();
    private long id = 0;
    private String originAccountId;
    private String destinationAccountId;
    private float amount;
    private Date date;


    public TransactionRequest(String originAccountId, String destinationAccountId, float amount, Date date) {
        this.id = REQUEST_ID.incrementAndGet();
        this.originAccountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.date = date;
    }
}
