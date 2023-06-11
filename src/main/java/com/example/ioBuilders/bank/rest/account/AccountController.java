package com.example.ioBuilders.bank.rest.account;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountException;
import com.example.ioBuilders.bank.domain.account.AccountService;
import com.example.ioBuilders.bank.domain.user.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * REST Interface for Accounts
 */
@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/{accountId}", produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        Optional<Account> account = accountService.getAccount(accountId);
        return account.isPresent()
                ? new ResponseEntity<>(account.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> createAccount(@RequestBody String userId) {
        try {
            this.accountService.createAccount(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccountException | UserException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<Account>> getAccounts(@RequestParam(required = false) String userId) {
        return (userId != null)
                ? new ResponseEntity<>(accountService.getAccounts(userId), HttpStatus.OK)
                : new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
}
