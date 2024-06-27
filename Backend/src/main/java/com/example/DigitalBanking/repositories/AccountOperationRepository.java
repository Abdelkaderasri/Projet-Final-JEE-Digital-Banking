package com.example.DigitalBanking.repositories;
import com.example.DigitalBanking.entites.AccountOperation;
import com.example.DigitalBanking.entites.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String accountId);

    @Query("SELECT COUNT(a) FROM AccountOperation a WHERE a.bankAccount.customer.id = :customerId")
    Long countByCustomerId(@Param("customerId") Long customerId);
    long countByType(OperationType type);
}

