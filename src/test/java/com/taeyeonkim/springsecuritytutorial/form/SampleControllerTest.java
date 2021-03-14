package com.taeyeonkim.springsecuritytutorial.form;

import com.taeyeonkim.springsecuritytutorial.account.model.Account;
import com.taeyeonkim.springsecuritytutorial.account.model.UserAccount;
import com.taeyeonkim.springsecuritytutorial.account.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    private Account user;
    private Account admin;

    @Before
    public void setup() {
        user = new Account();
        user.setUsername("taeyeon");
        user.setPassword("123");
        user.setRole("USER");

        admin = new Account();
        admin.setUsername("taeyeon");
        admin.setPassword("123");
        admin.setRole("ADMIN");
    }

    @Test
    @WithAnonymousUser
    public void indexAnonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void indexUser() throws Exception {
        mockMvc.perform(get("/").with(user(new UserAccount(user))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminUser() throws Exception {
        mockMvc.perform(get("/admin").with(user(new UserAccount(user))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminAdmin() throws Exception {
        mockMvc.perform(get("/admin").with(user(new UserAccount(admin))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void loginSuccess() throws Exception {
        String username = "taeyeon";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void loginFail() throws Exception {
        String username = "taeyeon";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}