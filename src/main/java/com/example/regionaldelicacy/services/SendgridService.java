package com.example.regionaldelicacy.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.exceptions.EmailSendException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendgridService {

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String requestContent) {
        try {
            Email from = new Email(fromEmail, "Regional Delicacy Shop");
            Email toEmail = new Email(to);

            Content content = new Content("text/plain", requestContent);
            Mail mail = new Mail(from, subject, toEmail, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            log.info("Email sent. Status Code: {}", response.getStatusCode());
            log.info("Email sent. Body: {}", response.getBody());
        } catch (IOException ex) {
            log.error("Failed to send email to {}", to, ex);
            throw new EmailSendException();
        }
    }
}
