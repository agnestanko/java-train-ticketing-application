package com.example.trainticketing.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class SmtpEmailService implements EmailService {
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String from;

    public SmtpEmailService() {
        this.host = System.getenv("SMTP_HOST");
        this.port = System.getenv("SMTP_PORT");
        this.username = System.getenv("SMTP_USERNAME");
        this.password = System.getenv("SMTP_PASSWORD");
        this.from = System.getenv("SMTP_FROM");

        validateConfiguration();
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Real email sent successfully to: " + to);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to send email: " + exception.getMessage(), exception);
        }
    }

    private void validateConfiguration() {
        if (isBlank(host) || isBlank(port) || isBlank(username) || isBlank(password) || isBlank(from)) {
            throw new IllegalStateException(
                    "SMTP configuration is missing. Please set SMTP_HOST, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, and SMTP_FROM."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}