package com.ioBuilders.bank.domain.user.service;

import com.ioBuilders.bank.domain.user.ports.out.UserStorage;
import com.ioBuilders.bank.domain.user.model.User;
import com.ioBuilders.bank.domain.user.model.UserException;
import com.ioBuilders.bank.domain.user.ports.in.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    protected final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Optional<User> getUser(String dni) {
        return userStorage.findUser(dni);
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    @Transactional
    public void registerUser(User user) {
        if (userStorage.containsUser(user.getDni())) {
            throw new UserException(UserException.ERR_ALREADY_REGISTERED);
        }
        userStorage.createUser(user);
    }
}
