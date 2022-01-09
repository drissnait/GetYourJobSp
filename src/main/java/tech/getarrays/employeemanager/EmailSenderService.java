package tech.getarrays.employeemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    public EmailSenderService(){

    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("driss.naitbelkacem@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

    }

    /*@Autowired
    private EmailSenderService senderService;

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("driss.naitbelkacem@gmail.com","sujet","body");
    }*/

    /*public void sendEmailWithAttachment(String toEmail, String body, String sujet, String attachment) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        MimeMessageHelper.setFrom("driss.naitbelkacem@gmail.com");
    }*/
}
