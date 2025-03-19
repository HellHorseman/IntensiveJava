package ru.y_lab.service;

import lombok.AllArgsConstructor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Сервис для отправки электронных писем.
 * Использует SMTP-сервер для отправки сообщений.
 */
@AllArgsConstructor
public class EmailService {
    private String host;
    private String port;
    private String username;
    private String password;

    /**
     * Отправляет электронное письмо.
     *
     * @param to      Адрес получателя.
     * @param subject Тема письма.
     * @param text    Текст письма.
     */
    public void sendEmail(String to, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Email отправлен успешно на адрес: " + to);
        } catch (MessagingException e) {
            System.out.println("Ошибка при отправке email: " + e.getMessage());
        }
    }
}
