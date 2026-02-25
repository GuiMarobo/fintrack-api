package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.model.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
    List<Account> findAll();
    Account findById(Long id);
    Account save(Account account);
    Account update(Long id, Account updatedAccount);
    Account patch(Long id, Map<String, String> fields);
    void delete(Long id);
}
