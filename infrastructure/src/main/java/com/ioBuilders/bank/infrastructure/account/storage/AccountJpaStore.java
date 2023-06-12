package com.ioBuilders.bank.infrastructure.account.storage;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Query("SELECT a FROM AccountEntity a where a.iban = ?1")
    Optional<AccountEntity> findByIbanForUpdate(String iban);

    @Modifying
    @Query("UPDATE AccountEntity a set a.balance = a.balance + ?2 where a.iban = ?1")
    void updateBalance(String iban, float deltaAmount);
}
