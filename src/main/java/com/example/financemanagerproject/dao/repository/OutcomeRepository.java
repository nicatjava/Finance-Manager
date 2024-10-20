package com.example.financemanagerproject.dao.repository;

import com.example.financemanagerproject.dao.entity.OutcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<OutcomeEntity, Long> {
    List<OutcomeEntity> findByCategory(String category);
}
