package com.execise.estore.estore.service.implementations;

import java.util.Locale;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.entity.NotificationEmail;
import com.execise.estore.estore.exception.EStoreException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async 	//sendin the e-mail is observed to consume long time, so just to make it faster, we use this
    //+ don't forget the @EnableAsync above the main application class: EStoreApplication
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("estore@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
            
//            //Debugging:
//            throw new EStoreException("message.MailNotSentEx" , notificationEmail.getRecipient());
            
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new EStoreException("Exception occurred when sending mail to "
            		+notificationEmail.getRecipient());
        
        }
    }

}
