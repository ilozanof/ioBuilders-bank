package com.ioBuilders.bank.domain.user.ports.in;

import com.ioBuilders.bank.domain.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * Operations allowed on Users
 */
public interface UserService {
    Optional<User> getUser(String dni);
    List<User> getUsers();
    void createUser(User user);
}
