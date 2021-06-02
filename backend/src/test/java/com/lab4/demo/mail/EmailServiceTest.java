package com.lab4.demo.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendMail() {
        emailService.sendMail("iulia.m.farcas@gmail.com", "Test", "Test");
    }
}