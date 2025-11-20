package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.model.Transaction;
import com.guimarobo.Fintrack.repository.AccountRepository;
import com.guimarobo.Fintrack.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public Transaction createTransaction(Transaction transaction) {
        Long accountId = transaction.getAccount().getId();

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = getTransactionById(id);

        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setDate(updatedTransaction.getDate());

        Long accountId = updatedTransaction.getAccount().getId();
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        existingTransaction.setAccount(account);
        return transactionRepository.save(existingTransaction);
    }

    @Transactional
    public void deleteTransaction(Long id){
        transactionRepository.deleteById(id);
    }
 }
