package com.dinsaren.mobilebankingserver.repository;

import com.dinsaren.mobilebankingserver.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    List<UserAccount> findAllByUserAccountIdAndStatus(String userAccountId, String status);
    UserAccount findByAccountNumberAndStatus(String accountNo, String status);
}
