package com.guimarobo.Fintrack.repository;

import com.guimarobo.Fintrack.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
