package com.taeyeonkim.springsecuritytutorial.account.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), account.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
