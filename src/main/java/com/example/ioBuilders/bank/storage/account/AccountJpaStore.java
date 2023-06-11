package com.example.ioBuilders.bank.storage.account;

import com.example.ioBuilders.bank.storage.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * JPA Interface for Accounts Store
 */
public interface AccountJpaStore extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByIban(String iban);
    List<AccountEntity> findByUserId(int userId);

    @Modifying
    @Query("UPDATE AccountEntity a set a.balance = a.balance + ?2 where a.iban = ?1")
    void updateBalance(String iban, float deltaAmount);
}
