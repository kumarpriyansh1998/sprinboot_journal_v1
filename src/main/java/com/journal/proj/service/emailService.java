package com.journal.proj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class emailService {
    @Autowired
    private JavaMailSender javamailsender;
    public void sendEmail(String to,String subject,String body){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javamailsender.send(message);
        }catch (Exception e){
            log.error("Exception handling while sending email"+e);
        }
    }

}

