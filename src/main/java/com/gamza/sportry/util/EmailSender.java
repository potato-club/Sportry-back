package com.gamza.sportry.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Component
public class EmailSender {

    private final SesClient sesClient;

    @Value("${cloud.aws.ses.send-mail-to}")
    private String fromEmail;

    public EmailSender(@Value("${cloud.aws.ses.access-key}") String accessKey,
                       @Value("${cloud.aws.ses.secret-key}") String secretKey,
                       @Value("${cloud.aws.ses.region}") String region) {
        this.sesClient = SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(toEmail).build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).charset("UTF-8").build())
                        .body(Body.builder()
                                .text(Content.builder().data(body).charset("UTF-8").build())
                                .build())
                        .build())
                .source(fromEmail)
                .build();

        sesClient.sendEmail(emailRequest);
    }
}
