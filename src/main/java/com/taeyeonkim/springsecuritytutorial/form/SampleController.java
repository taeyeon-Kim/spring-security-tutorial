package com.taeyeonkim.springsecuritytutorial.form;

import com.taeyeonkim.springsecuritytutorial.account.model.Account;
import com.taeyeonkim.springsecuritytutorial.common.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/")
    public String index(Model model, @CurrentUser Account account) {
        sampleService.dashboard();
        if (account == null) {
            model.addAttribute("message", "Hello Spring Security");
        } else {
            model.addAttribute("message", "Hello, " + account.getUsername());
        }

        return "index";
    }

    @GetMapping("/info")
    public String info(Model model, Principal principal) {
        model.addAttribute("message", "Info");
        return "info";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }
}