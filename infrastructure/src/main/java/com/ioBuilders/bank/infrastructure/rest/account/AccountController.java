package com.ioBuilders.bank.infrastructure.rest.account;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.model.AccountException;
import com.ioBuilders.bank.domain.account.ports.in.AccountService;
import com.ioBuilders.bank.domain.user.model.User;
import com.ioBuilders.bank.domain.user.model.UserException;
import com.ioBuilders.bank.domain.user.ports.in.UserService;
import com.ioBuilders.bank.infrastructure.rest.ResourceNotFoundException;
import com.ioBuilders.bank.infrastructure.rest.transaction.TransactionController;
import com.ioBuilders.bank.infrastructure.rest.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 *
 * REST Interface for Accounts
 */
@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    @Autowired
    public AccountController(AccountService accountService,
                             UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    private AccountView toView(Account account) {
        Link linkUser = linkTo(methodOn(UserController.class).getUser(account.getUser().getDni())).withRel("user");
        Link linkSelf = linkTo(methodOn(AccountController.class).getAccount(account.getAccountId())).withSelfRel();
        Link linkTransactions = linkTo(methodOn(TransactionController.class).getTransactionsByAccount(account.getAccountId(), 0)).withRel("transactions");

        AccountView accountView = new AccountView(account);
        accountView.add(linkUser);
        accountView.add(linkSelf);
        accountView.add(linkTransactions);
        return accountView;
    }

    @GetMapping(path = "/{accountId}", produces = "application/json")
    public ResponseEntity<AccountView> getAccount(@PathVariable String accountId) {
        Optional<Account> account = accountService.getAccount(accountId);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Account");
        }
        return new ResponseEntity<>(toView(account.get()), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<AccountView> createAccount(@RequestBody String userId) {
        try {
            String accountId = this.accountService.createAccount(userId);
            Account account = this.accountService.getAccount(accountId).get();
            return new ResponseEntity<>(toView(account), HttpStatus.OK);
        } catch (AccountException | UserException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<AccountView>> getAccounts(@RequestParam(required = false) String userId) {
        if (userId == null) {
            return new ResponseEntity<>(accountService.getAllAccounts().stream().map(a -> toView(a)).collect(Collectors.toList()), HttpStatus.OK);
        }
        Optional<User> user = this.userService.getUser(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User");
        }
        return new ResponseEntity<>(accountService.getAccountsByUser(userId).stream().map(a -> toView(a)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
