package com.example.ioBuilders.bank.storage.user;

import com.example.ioBuilders.bank.domain.user.User;
import com.example.ioBuilders.bank.domain.user.UserException;
import com.example.ioBuilders.bank.domain.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 *
 * ADAPTER that implements the PORT USerStorage, providing RDBMS Storage for Users
 */
public class UserStore implements UserStorage {

    private final UserJpaStore userJpaStore;

    @Autowired
    public UserStore(UserJpaStore userJpaStore) {
        this.userJpaStore = userJpaStore;
    }

    @Override
    public Optional<User> findUser(String dni) {
        return userJpaStore.findByDni(dni).map(UserEntity::toDomain);
    }

    @Override
    public void createUser(User user) {
        if (userJpaStore.findByDni(user.getDni()).isPresent()) {
            throw new UserException(UserException.ERR_ALREADY_REGISTERED);
        }
        userJpaStore.save(new UserEntity(user));
    }

    // TODO: CHECK REMOVING IF RELATIONS EXIST
    @Override
    public void removeUser(String dni) {
        if (userJpaStore.findByDni(dni).isPresent()) {
            throw new UserException(UserException.ERR_ALREADY_REGISTERED);
        }
        userJpaStore.deleteByDni(dni);
    }

    @Override
    public void clear() {
        userJpaStore.deleteAll();
    }

    @Override
    public List<User> getUsers() {
        return userJpaStore.findAll().stream().map(UserEntity::toDomain).collect(Collectors.toList());
    }
}
