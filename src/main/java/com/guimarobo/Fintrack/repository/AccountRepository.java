package com.guimarobo.Fintrack.repository;

import com.guimarobo.Fintrack.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
