package com.example.ioBuilders.bank.storage.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * JPA Interface for Users Store
 */
public interface UserJpaStore extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByDni(String dni);
    void deleteByDni(String dni);
}
