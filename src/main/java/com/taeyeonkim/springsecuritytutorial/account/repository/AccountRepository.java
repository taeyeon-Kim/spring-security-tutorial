package com.taeyeonkim.springsecuritytutorial.account.repository;

import com.taeyeonkim.springsecuritytutorial.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
