package com.epam.testing.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * EmailSender util. Sends email to user.
 * Uses email.properties for configuration
 *
 * @author rom4ik
 */
public class EmailSenderUtil {
    private static final Logger LOGGER = LogManager.getLogger(EmailSenderUtil.class);
    private static final String EMAIL_PROPERTIES_PATH = "email.properties";

    /**
     * Don't let anyone instantiate this class.
     */
    private EmailSenderUtil() {}

    /**
     * @param emailToSend represents email address to which message has to be sent
     * @param text message to send
     */
    public static boolean sendEmail(String emailToSend, String subject, String text){
        LOGGER.info("Sending email starts");
        boolean success = true;

        Properties properties = readProperties();
        if(properties == null) {
            success = false;
            LOGGER.warn("Failed to read email.properties");
        } else {
            if(sendMessage(properties, emailToSend, subject, text)) {
                LOGGER.info("Email message sent successfully");
            } else {
                LOGGER.warn("Failed to send email");
                success = false;
            }
        }

        return success;
    }

    private static boolean sendMessage(Properties properties, String emailToSend, String subject, String text) {
        String senderEmail = properties.getProperty("sender.name");
        String senderPassword = properties.getProperty("sender.password");
        boolean result = true;

        LOGGER.info("Sender email: {}", senderEmail);
        LOGGER.info("Recipient email: {}", emailToSend);
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(subject));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailToSend));
            message.setSubject("WebTesting");

            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        try {
            properties.load(EmailSenderUtil.class.getClassLoader().getResourceAsStream(EMAIL_PROPERTIES_PATH));
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
