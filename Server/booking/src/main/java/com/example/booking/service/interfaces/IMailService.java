package com.example.booking.service.interfaces;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IMailService {

    void sendActivationEmail(String recipientEmail, Long userId) throws MessagingException, UnsupportedEncodingException;

}
