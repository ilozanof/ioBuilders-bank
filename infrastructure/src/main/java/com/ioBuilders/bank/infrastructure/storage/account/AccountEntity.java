package com.ioBuilders.bank.infrastructure.storage.account;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.infrastructure.storage.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author i.fernandez@nchain.com
 *
 * Entioty class to persist Accounts
 */
@Entity
@NoArgsConstructor
@Data
public class AccountEntity {
    @Id
    @GeneratedValue
    private int id;
    private String accountId;
    private float balance;
    @ManyToOne
    private UserEntity user;

    public AccountEntity(String accountId, UserEntity user) {
        this.accountId = accountId;
        this.balance = 0;
        this.user = user;
    }

    public Account toDomain() {
        return new Account(this.user.toDomainWithoutAccounts(), this.accountId, this.balance);
    }
}
