package com.example.ioBuilders.bank.storage.account;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.storage.user.UserEntity;
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
    private String iban;
    private float balance;
    @ManyToOne
    private UserEntity user;

    public AccountEntity(String iban, UserEntity user) {
        this.iban = iban;
        this.balance = 0;
        this.user = user;
    }


    public Account toDomain() {
        return new Account(this.iban, this.balance);
    }
}
