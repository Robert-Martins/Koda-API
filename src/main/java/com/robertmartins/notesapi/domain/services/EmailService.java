package com.robertmartins.notesapi.services;

import com.robertmartins.notesapi.dtos.UserDeviceDto;
import com.robertmartins.notesapi.repositories.EmailRepository;
import com.robertmartins.notesapi.resources.EmailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class EmailService implements EmailResource {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from}")
    String emailFrom;

    @Value("${email.to}")
    String mailTo;

    public void sendNewUserEmail(String emailTo){
            this.sendEmail(
                    EmailModel.builder()
                        .emailFrom(emailFrom)
                        .emailTo(mailTo)
                        .subject("Welcome To Koda")
                        .text("Welcome To Koda")
                        .build()
            );
    }

    public void sendLoginInNewIpEmail(String emailTo, UserDeviceDto userDeviceDto){
            this.sendEmail(
                    EmailModel.builder()
                            .emailFrom(emailFrom)
                            .emailTo(mailTo)
                            .subject("Welcome To Koda2")
                            .text("Welcome To Koda")
                            .build()
            );
    }

    public void sendLoginAttemptInBlockedIpEmail(String emailTo) {
            this.sendEmail(
                    EmailModel.builder()
                            .emailFrom(emailFrom)
                            .emailTo(mailTo)
                            .subject("Welcome To Koda3")
                            .text("Welcome To Koda")
                            .build()
            );
    }

    private void sendEmail(EmailModel email){
        email.setEmailSentDate(LocalDateTime.now());
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            mimeMessageHelper.setFrom(email.getEmailFrom());
            mimeMessageHelper.setTo(email.getEmailTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getText());

            javaMailSender.send(message);
        }
        catch (MailException | MessagingException ex){
            email.setWasEmailSent(false);
        }
        finally {
            email.setWasEmailSent(true);
            email.setUpdatedAt(new Date());
            email.setCreatedAt(new Date());
            emailRepository.save(email);
        }
    }

}
