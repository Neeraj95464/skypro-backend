package com.ynmio.School.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sentEmail(String toEmail,String subject,String body){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("neerajkumar95464@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setText(body);
        mailMessage.setSubject(subject);

        mailSender.send(mailMessage);
        System.out.println("Mail sent success");
    }
}
