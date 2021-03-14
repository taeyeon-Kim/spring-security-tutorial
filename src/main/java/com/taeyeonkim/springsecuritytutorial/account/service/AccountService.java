package com.taeyeonkim.springsecuritytutorial.account.service;

import com.taeyeonkim.springsecuritytutorial.account.model.Account;
import com.taeyeonkim.springsecuritytutorial.account.model.UserAccount;
import com.taeyeonkim.springsecuritytutorial.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 타입의 bean이 있으면 Spring security가 알아서 등록
 *
 * @Override
 * protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 *     auth.userDetailsService(accountService);
 * }
 */
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder encodePassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

//        return User.builder()
//                .username(account.getUsername())
//                .password(account.getPassword())
//                .roles(account.getRole())
//                .build();
        return new UserAccount(account);
    }

    public Account createNew(Account account) {
        account.encodePassword(encodePassword);
        return this.accountRepository.save(account);
    }
}
