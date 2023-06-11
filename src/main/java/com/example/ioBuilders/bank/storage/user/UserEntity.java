package com.example.ioBuilders.bank.storage.user;

import com.example.ioBuilders.bank.domain.user.User;
import com.example.ioBuilders.bank.storage.account.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 *
 * Entity Class to persist Users
 */
@Entity
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;
    private String dni;
    private String name;

    // TODO: This CASXCADE Is WRONG. WE SHOULD CHECK IF THERE ARE ACCOUNTS BEFORE REMOVING THE USER. ENOUGH FOR THIS POC?
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

    public UserEntity(User user) {
        this.dni = user.getDni();
        this.name = user.getName();
    }

    public User toDomain() {
        return new User(dni, name, accounts.stream().map(AccountEntity::toDomain).collect(Collectors.toList()));
    }
}
