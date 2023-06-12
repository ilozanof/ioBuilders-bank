package com.ioBuilders.bank.infrastructure.transaction.rest;

import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionException;
import com.ioBuilders.bank.domain.transaction.ports.in.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@RestController
@RequestMapping(path = "/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> executeTransaction(@RequestBody Transaction transaction) {
        try {
            this.transactionService.executeTransaction(transaction);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@RequestParam String accountId, @RequestParam int page) {
        try {
            return new ResponseEntity<>(this.transactionService.getTransactionsByAccount(accountId, page), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
