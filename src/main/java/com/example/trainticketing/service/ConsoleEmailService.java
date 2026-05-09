package com.example.trainticketing.service;

public class ConsoleEmailService implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("\n===== EMAIL NOTIFICATION =====");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Message:");
        System.out.println(body);
        System.out.println("==============================\n");
    }
}