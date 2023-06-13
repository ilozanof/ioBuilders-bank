package com.ioBuilders.bank.domain.user.ports.in;

import com.ioBuilders.bank.domain.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * Operations allowed on Users at Business-level
 */
public interface UserService {

    /** Retrieves info of a specific USer */
    Optional<User> getUser(String dni);

    /** It returns the complete list of Users of the Bank (Should be paginated) */
    List<User> getUsers();

    /** It registers a new USer in the Bank */
    void registerUser(User user);
}
