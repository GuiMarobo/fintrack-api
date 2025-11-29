package com.guimarobo.Fintrack.controller;

import com.guimarobo.Fintrack.model.Account;
import com.guimarobo.Fintrack.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST — Criar conta
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.status(201).body(newAccount);
    }

    // GET — Listar todas as contas
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(accounts);
    }

    // GET — Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    // PUT — Atualizar conta
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id,
                                                 @Valid @RequestBody Account updatedAccount) {
        Account account = accountService.updateAccount(id, updatedAccount);
        return ResponseEntity.ok(account);
    }

    // DELETE — Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
