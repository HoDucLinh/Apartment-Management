/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.services.impl;

import com.apartment_management.services.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env;

    @Override
    public void sendNewPackageNotification(String recipientEmail, String packageName, Date createdAt) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", env.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));

        String username = env.getProperty("mail.smtp.username");
        String password = env.getProperty("mail.smtp.password");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Thông báo: Gói hàng mới trong locker", "UTF-8");

        String htmlContent = "<h3>Xin chào,</h3>"
                + "<p>Một gói hàng mới đã được thêm vào locker của bạn:</p>"
                + "<ul>"
                + "<li><strong>Tên gói hàng:</strong> " + packageName + "</li>"
                + "<li><strong>Thời gian:</strong> " + createdAt + "</li>"
                + "</ul>"
                + "<p>Vui lòng đến nhận gói hàng tại locker sớm nhất có thể.</p>"
                + "<p>Trân trọng,<br/>Ban quản lý chung cư</p>";

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
    }

}
