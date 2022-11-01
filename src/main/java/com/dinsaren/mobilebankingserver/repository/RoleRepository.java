package com.dinsaren.mobilebankingserver.repository;

import com.dinsaren.mobilebankingserver.models.Role;
import com.dinsaren.mobilebankingserver.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(UserRole name);
}
