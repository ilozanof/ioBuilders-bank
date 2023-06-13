package com.ioBuilders.bank.infrastructure.rest.transaction;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.ports.in.AccountService;
import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;
import com.ioBuilders.bank.domain.transaction.ports.in.TransactionService;
import com.ioBuilders.bank.infrastructure.rest.ResourceNotFoundException;
import com.ioBuilders.bank.infrastructure.rest.account.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@RestController
@RequestMapping(path = "/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    private TransactionView toView(Transaction transaction) {

        TransactionView transactionView = new TransactionView(transaction);
        Optional<Account> originAccount = this.accountService.getAccount(transaction.getOriginAccountId());
        Optional<Account> destAccount = this.accountService.getAccount(transaction.getDestinationAccountId());
        Link linkSelf = linkTo(methodOn(TransactionController.class).getTransaction(transaction.getId())).withSelfRel();
        transactionView.add(linkSelf);
        if (originAccount.isPresent()) {
            Link originAccountLink = linkTo(methodOn(AccountController.class).getAccount(originAccount.get().getAccountId())).withRel("Origin Account");
            transactionView.add(originAccountLink);
        }
        if (destAccount.isPresent()) {
            Link destAccountLink = linkTo(methodOn(AccountController.class).getAccount(destAccount.get().getAccountId())).withRel("Destination Account");
            transactionView.add(destAccountLink);
        }
        return transactionView;
    }

    @PostMapping(path = "")
    public ResponseEntity<TransactionView> executeTransaction(@RequestBody TransactionRequest transactionReq) {
        int transactionId = this.transactionService.executeTransaction(transactionReq);
        Transaction transactionRead = this.transactionService.getTransaction(transactionId).get();
        return new ResponseEntity<>(toView(transactionRead), HttpStatus.OK);
    }

    @GetMapping(path = "/{transactionId}", produces = "application/json")
    public ResponseEntity<TransactionView> getTransaction(@PathVariable int transactionId) {
        Optional<Transaction> transaction = this.transactionService.getTransaction(transactionId);
        if (transaction.isEmpty()) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Transaction");
        }
        return new ResponseEntity<>(toView(transaction.get()), HttpStatus.OK);
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<TransactionView>> getTransactionsByAccount(@RequestParam String accountId, @RequestParam int page) {
        try {
            return new ResponseEntity<>(this.transactionService.getTransactionsByAccount(accountId, page).stream().map(t -> toView(t)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
