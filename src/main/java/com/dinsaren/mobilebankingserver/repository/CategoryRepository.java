package com.dinsaren.mobilebankingserver.repository;

import com.dinsaren.mobilebankingserver.models.Category;
import com.dinsaren.mobilebankingserver.models.OtpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByStatusOrderByIndexAsc(String status);
    List<Category> findAllByCategoryCodeAndStatusOrderByIndexAsc(String code, String status);
    Category findByIdAndStatus(Integer id, String status);
}
