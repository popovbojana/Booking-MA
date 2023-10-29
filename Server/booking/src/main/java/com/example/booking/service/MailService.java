package com.example.booking.service;

import com.example.booking.service.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendActivationEmail(String recipientEmail, Long userId) throws MessagingException,UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("booking.ma.project@gmail.com", "Booking MA Project");
        helper.setTo(recipientEmail);

        String subject = "Account activation";

        String activationLink = "http://localhost:8081/api/user/activation/" + userId;
        String body = "<p>Hello there,</p>"
                + "<p>Click on this link to activate your account:</p>" +
                "<a href='" + activationLink +"'>" + activationLink;

        helper.setSubject(subject);

        helper.setText(body, true);

        mailSender.send(message);
    }

}