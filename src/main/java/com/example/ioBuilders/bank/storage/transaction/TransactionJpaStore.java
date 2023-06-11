package com.example.ioBuilders.bank.storage.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author i.fernandez@nchain.com
 *
 * JPA interface for Transactions Store
 */
public interface TransactionJpaStore extends JpaRepository<TransactionEntity, Integer> {
    @Query("SELECT t from TransactionEntity t WHERE t.originAccountId = ?1 or t.destinationAccountId = ?1 order by t.date desc")
    Page<TransactionEntity> findTransactionEntitiesByAccount(String accountId, Pageable pageable);
}
