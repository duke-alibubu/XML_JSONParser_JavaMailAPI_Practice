package com.cz3003.pmoreport;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSender {
	public void sendEmail(String receive ,String toSent) {
		String host = "localhost";
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	      // Get system properties
	      Properties props = System.getProperties();

	      // Setup mail server
	      props.setProperty("mail.smtp.host", "smtp.gmail.com");
	      props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.debug", "true");
	      props.put("mail.store.protocol", "pop3");
	      props.put("mail.transport.protocol", "smtp");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(props, 
                  new Authenticator(){
                     protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("shireAutoSend@gmail.com", "shireSend123");
                     }});

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress("shireAutoSend@gmail.com"));

	         // Set To: header field of the header.
	         //message.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));
	         message.setRecipients(Message.RecipientType.TO, 
                     InternetAddress.parse(receive,false));

	         // Set Subject: header field
	         message.setSubject("PMO Report");

	         // Now set the actual message
	         message.setText(toSent);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         System.out.println("Messagin Exception occurred!");
	      }
	}
}
