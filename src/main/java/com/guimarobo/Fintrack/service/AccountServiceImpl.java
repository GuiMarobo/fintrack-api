package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.exception.NotFoundException;
import com.guimarobo.Fintrack.model.Account;
import com.guimarobo.Fintrack.model.User;
import com.guimarobo.Fintrack.repository.AccountRepository;
import com.guimarobo.Fintrack.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    @Transactional
    public Account save(Account account) {
        if (account.getUser() == null || account.getUser().getId() == null) {
            throw new IllegalArgumentException("Usuário da conta não informado.");
        }

        User user = userRepository.findById(account.getUser().getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        account.setUser(user);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account update(Long id, Account updatedAccount) {
        Account existingAccount = findById(id);

        if (updatedAccount.getUser() == null || updatedAccount.getUser().getId() == null) {
            throw new IllegalArgumentException("Usuário da conta não informado.");
        }

        User user = userRepository.findById(updatedAccount.getUser().getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        existingAccount.setBankName(updatedAccount.getBankName());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setBalance(updatedAccount.getBalance());
        existingAccount.setUser(user);

        return accountRepository.save(existingAccount);
    }

    @Override
    @Transactional
    public Account patch(Long id, Map<String, String> fields) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("Nenhum campo informado para atualização.");
        }

        String newBankName = null;
        String newAccountType = null;
        BigDecimal newBalance = null;
        User newUser = null;

        if (fields.containsKey("bankName")) {
            newBankName = fields.get("bankName");
            if (newBankName == null || newBankName.isBlank()) {
                throw new IllegalArgumentException("O nome do banco não pode ser vazio.");
            }
        }

        if (fields.containsKey("accountType")) {
            newAccountType = fields.get("accountType");
            if (newAccountType == null || newAccountType.isBlank()) {
                throw new IllegalArgumentException("O tipo da conta não pode ser vazio.");
            }
        }

        if (fields.containsKey("balance")) {
            String balanceStr = fields.get("balance");
            if (balanceStr == null || balanceStr.isBlank()) {
                throw new IllegalArgumentException("O saldo não pode ser vazio.");
            }
            try {
                newBalance = new BigDecimal(balanceStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Saldo inválido. Informe um número válido.");
            }
        }

        if (fields.containsKey("userId")) {
            String userIdStr = fields.get("userId");
            if (userIdStr == null || userIdStr.isBlank()) {
                throw new IllegalArgumentException("O ID do usuário não pode ser vazio.");
            }
            try {
                Long userId = Long.parseLong(userIdStr);
                newUser = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID de usuário inválido. Informe um número válido.");
            }
        }

        Account existingAccount = findById(id);

        if (newBankName != null) existingAccount.setBankName(newBankName);
        if (newAccountType != null) existingAccount.setAccountType(newAccountType);
        if (newBalance != null) existingAccount.setBalance(newBalance);
        if (newUser != null) existingAccount.setUser(newUser);

        return accountRepository.save(existingAccount);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        accountRepository.delete(findById(id));
    }
}
