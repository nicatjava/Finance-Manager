package com.example.financemanagerproject.dao.repository;

import com.example.financemanagerproject.dao.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findBySource(String source);
}
