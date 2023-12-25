package com.example.booking.service;

import com.example.booking.service.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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

//    @Override
//    @Async
//    public void sendActivationEmail(String recipientEmail, Long userId) throws MessagingException,UnsupportedEncodingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("booking.ma.project@gmail.com", "Booking MA Project");
//        helper.setTo(recipientEmail);
//
//        String subject = "Account activation";
//
//        String activationLink = "http://192.168.1.5:8081/api/user/activation/" + userId;
//        String body = "<p>Hello there,</p>"
//                + "<p>Click on this link to activate your account:</p>" +
//                "<a href='" + activationLink +"'>" + activationLink;
//
//        helper.setSubject(subject);
//
//        helper.setText(body, true);
//
//        mailSender.send(message);
//    }

    @Override
    @Async
    public void sendActivationEmail(String recipientEmail, Long userId) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("booking.ma.project@gmail.com", "Booking MA Project");
        helper.setTo(recipientEmail);

        String subject = "Account Activation";

        String activationLink = "http://192.168.1.5:8081/api/user/activation/" + userId;
        String body = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Account Activation</title>" +
                "</head>" +
                "<body style=\"font-family: Arial, sans-serif; text-align: center;\">" +
                "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px;\">" +
                "        <h2 style=\"color: #333;\">Account Activation</h2>" +
                "        <p>Hello there,</p>" +
                "        <p>Click on the link below to activate your account:</p>" +
                "        <a href=\"" + activationLink + "\" style=\"display: inline-block; padding: 10px 15px; background-color: #808080; color: #fff; text-decoration: none; border-radius: 3px;\">Activate Account</a>" +
                "        <p>If the button above doesn't work, you can also copy and paste the following link into your browser:</p>" +
                "        <p>" + activationLink + "</p>" +
                "    </div>" +
                "</body>" +
                "</html>";

        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }



}
