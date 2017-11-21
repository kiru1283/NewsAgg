package newsagg.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import newsagg.exceptions.ShareException;

public class ShareArticle {
	
	public boolean shareFeed(String url, String toAddress, String userid) throws ShareException{
		
			final String username = "feedbook.4All";
			final String password = "Sda2proj";
			
			boolean retVal = false;
			
			
			 try {
			      InternetAddress emailAddr = new InternetAddress(toAddress);
			      emailAddr.validate();
			   } catch (AddressException ex) {
				   
				   throw new ShareException("Incorrect Reciever email id !!");
				   
			   }
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
		
			try {
		
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("feedbook.4All@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddress));
				message.setSubject("Interesting Article");
				message.setText("Hi, " 
					+"\n\n Our user "+userid+" has shared an article in "+url+" which might be of interest to you."
					+ "\n Please click the link to read the article."
					+ "\n\n Best Regards, \n FeedBook Team");
		
				Transport.send(message);
		
				retVal = true;
		
			} catch(MessagingException e) {
				
				throw new ShareException("Error occured while sending email.");
			}
			
			return retVal;
		
	}
}
