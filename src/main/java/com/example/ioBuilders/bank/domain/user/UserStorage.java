package com.example.ioBuilders.bank.domain.user;

import java.util.List;
import java.util.Optional;

/**
 * A PORT that provides Storage capabilities for Users.
 */
public interface UserStorage {
    default boolean containsUser(String dni) {
        return findUser(dni).isPresent();
    }
    Optional<User> findUser(String dni);
    void createUser(User user);
    void removeUser(String dni);
    List<User> getUsers();
}
