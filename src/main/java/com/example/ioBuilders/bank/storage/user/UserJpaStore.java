package com.example.ioBuilders.bank.storage.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author i.fernandez@nchain.com
 *
 * JPA Interface for USers Store
 */
public interface UserJpaStore extends JpaRepository<UserEntity, String> {}
