package com.dinsaren.mobilebankingserver.repository;

import com.dinsaren.mobilebankingserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsernameAndStatus(String username,  String status);
  Optional<User> findByPhoneNumberAndStatus(String phone, String status);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
  Boolean existsByPhoneNumber(String phone);

  Boolean existsByNationalId(String nId);

  Optional<User> findByUserAccountIdAndStatus(String userAccountId, String status);

}
