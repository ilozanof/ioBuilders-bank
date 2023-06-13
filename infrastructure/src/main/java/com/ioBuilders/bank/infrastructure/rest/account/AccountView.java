package com.ioBuilders.bank.infrastructure.rest.account;

import com.ioBuilders.bank.domain.account.model.Account;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author i.fernandez@nchain.com
 *
 * View Representation of an Account
 */
public class AccountView extends RepresentationModel<AccountView> {
    private Account account;

    public AccountView(Account account) {
        this.account = account;
    }

    public String getAccountId() { return this.account.getAccountId();}
    public String getOwner() { return "[" + this.account.getUser().getDni() + "] " + this.account.getUser().getName();}
    public float getBalance() { return this.account.getBalance();}
}