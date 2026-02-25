package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> findAll();
    Transaction findById(Long id);
    Transaction save(Transaction transaction);
    Transaction update(Long id, Transaction updatedTransaction);
    Transaction patch(Long id, Map<String, String> fields);
    void delete(Long id);
}
