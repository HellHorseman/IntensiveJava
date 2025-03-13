package ru.y_lab.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private String host; // SMTP-сервер
    private String port; // Порт SMTP-сервера
    private String username; // Логин от почты
    private String password; // Пароль от почты

    public EmailService(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    // Метод для отправки email
    public void sendEmail(String to, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Используем TLS для безопасности

        // Создаем сессию с аутентификацией
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Создаем сообщение
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Отправитель
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Получатель
            message.setSubject(subject); // Тема письма
            message.setText(text); // Текст письма

            // Отправляем сообщение
            Transport.send(message);
            System.out.println("Email отправлен успешно на адрес: " + to);
        } catch (MessagingException e) {
            System.out.println("Ошибка при отправке email: " + e.getMessage());
        }
    }
}
