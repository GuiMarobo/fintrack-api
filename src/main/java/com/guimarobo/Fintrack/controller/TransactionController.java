package com.guimarobo.Fintrack.controller;

import com.guimarobo.Fintrack.model.Transaction;
import com.guimarobo.Fintrack.repository.AccountRepository;
import com.guimarobo.Fintrack.service.TransactionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransactions(@RequestBody Transaction transaction){
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionByiD(@PathVariable Long id){
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction){
        Transaction transaction = transactionService.updateTransaction(id, updatedTransaction);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
