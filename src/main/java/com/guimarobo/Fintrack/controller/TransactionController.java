package com.guimarobo.Fintrack.controller;

import com.guimarobo.Fintrack.model.Transaction;
import com.guimarobo.Fintrack.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // POST — Criar transação
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    // GET — Listar transações
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();

        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(transactions);
    }

    // GET — Buscar transação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    // PUT — Atualizar transação
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
                                                         @Valid @RequestBody Transaction updatedTransaction) {
        Transaction saved = transactionService.update(id, updatedTransaction);
        return ResponseEntity.ok(saved);
    }

    // PATCH — Atualizar parcialmente transação
    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> patchTransaction(@PathVariable Long id,
                                                        @RequestBody Map<String, String> fields) {
        Transaction transaction = transactionService.patch(id, fields);
        return ResponseEntity.ok(transaction);
    }

    // DELETE — Apagar transação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
