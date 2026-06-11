package com.eDiya.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("interstellarvoyage007@gmail.com","Testing Java mail sender", "Hi How are you?");
    }
}
