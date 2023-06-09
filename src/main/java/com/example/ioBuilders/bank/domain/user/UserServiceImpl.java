package com.example.ioBuilders.bank.domain.user;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

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
    public void createUser(User user) {
        if (userStorage.containsUser(user.getDni())) {
            throw new UserException(UserException.ERR_ALREADY_REGISTERED);
        }
        userStorage.createUser(user);
    }
}
