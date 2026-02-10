package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getAccount() == null || transaction.getAccount().getId() == null) {
            throw new IllegalArgumentException("A conta relacionada é obrigatória.");
        }

        Long accountId = transaction.getAccount().getId();

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        applyBalanceChange(account, transaction.getType(), transaction.getAmount());

        accountRepository.save(account);

        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {

        Transaction existingTransaction = getTransactionById(id);

        var account = accountRepository.findById(updatedTransaction.getAccount().getId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        revertBalanceChange(account, existingTransaction.getType(), existingTransaction.getAmount());

        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setDate(updatedTransaction.getDate());
        existingTransaction.setAccount(account);

        applyBalanceChange(account, updatedTransaction.getType(), updatedTransaction.getAmount());

        accountRepository.save(account);

        return transactionRepository.save(existingTransaction);
    }

    @Transactional
    public void deleteTransaction(Long id){

        Transaction transaction = getTransactionById(id);

        var account = transaction.getAccount();

        revertBalanceChange(account, transaction.getType(), transaction.getAmount());

        accountRepository.save(account);

        transactionRepository.deleteById(id);
    }


    private void applyBalanceChange(com.guimarobo.Fintrack.model.Account account, String type, java.math.BigDecimal amount) {
        if (type.equalsIgnoreCase("DESPESA")) {
            account.setBalance(account.getBalance().subtract(amount));
        } else if (type.equalsIgnoreCase("ENTRADA")) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            throw new IllegalArgumentException("Tipo de transação inválido: '" + type + "'. Use 'DESPESA' ou 'ENTRADA'.");
        }
    }

    private void revertBalanceChange(com.guimarobo.Fintrack.model.Account account, String type, java.math.BigDecimal amount) {
        if (type.equalsIgnoreCase("DESPESA")) {
            account.setBalance(account.getBalance().add(amount));
        } else if (type.equalsIgnoreCase("ENTRADA")) {
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            throw new IllegalArgumentException("Tipo de transação inválido: '" + type + "'. Use 'DESPESA' ou 'ENTRADA'.");
        }
    }
}
