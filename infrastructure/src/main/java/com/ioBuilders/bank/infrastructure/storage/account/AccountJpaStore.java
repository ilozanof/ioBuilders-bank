package com.ioBuilders.bank.infrastructure.storage.account;

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
    Optional<AccountEntity> findByAccountId(String accountId);
    List<AccountEntity> findByUserId(int userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Query("SELECT a FROM AccountEntity a where a.accountId = ?1")
    Optional<AccountEntity> findByAccountIdForUpdate(String accountId);

    @Modifying
    @Query("UPDATE AccountEntity a set a.balance = a.balance + ?2 where a.accountId = ?1")
    void updateBalance(String accountId, float deltaAmount);
}
