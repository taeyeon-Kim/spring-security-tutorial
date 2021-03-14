package com.taeyeonkim.springsecuritytutorial.account.controller;

import com.taeyeonkim.springsecuritytutorial.account.model.Account;
import com.taeyeonkim.springsecuritytutorial.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final AccountService accountService;

    @GetMapping
    public String signupForm(Model model) {
        model.addAttribute("account", new Account());
        return "sign-up";
    }

    @PostMapping
    public String processSignUp(@ModelAttribute Account account) {
        account.setRole("USER");
        accountService.createNew(account);
        return "redirect:/";
    }

    @PostMapping("/admin")
    public String processSignUpAdmin(@ModelAttribute Account account) {
        account.setRole("ADMIN");
        accountService.createNew(account);
        return "redirect:/";
    }
}
