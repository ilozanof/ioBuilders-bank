package com.ioBuilders.bank.domain.user.ports.out;

import com.ioBuilders.bank.domain.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * A PORT that provides Storage capabilities for Users.
 */
public interface UserStorage {
    /** Indicates if the User given is tored */
    default boolean containsUser(String dni) {
        return findUser(dni).isPresent();
    }
    /** Retrieves a specific User */
    Optional<User> findUser(String dni);

    /** It saves a new User in the Store */
    void createUser(User user);

    /** It returns the complete list of Users in the Store (Should be paginated) */
    List<User> getUsers();
}
