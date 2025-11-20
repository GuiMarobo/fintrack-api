package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.model.Account;
import com.guimarobo.Fintrack.model.User;
import com.guimarobo.Fintrack.repository.AccountRepository;
import com.guimarobo.Fintrack.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
    }

    @Transactional
    public Account createAccount(Account account) {
        Long userId = account.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        account.setUser(user);
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long id, Account updatedAccount) {
        Account existingAccount = getAccountById(id);
        existingAccount.setBankName(updatedAccount.getBankName());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setBalance(updatedAccount.getBalance());
        existingAccount.setUser(updatedAccount.getUser());
        return accountRepository.save(existingAccount);
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
