package com.example.ioBuilders.bank.storage.user;

import com.example.ioBuilders.bank.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

/**
 * @author i.fernandez@nchain.com
 *
 * Entity Class to persist Users
 */
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    private String dni;
    private String name;

    public UserEntity(User user) {
        this.dni = user.getDni();
        this.name = user.getName();
    }

    public User toDomain() {
        return new User(dni, name);
    }
}
