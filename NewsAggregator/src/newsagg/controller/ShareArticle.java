package newsagg.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ShareArticle {
	
	public void shareFeed(String url, String toAddress, String userid) {
		
			final String username = "feedbook.4All";
			final String password = "Sda2proj";
			
			//String toAddress = "kiru.muthu@gmail.com";
			
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
		
				System.out.println("Mail successfully sent to "+toAddress);
		
			} catch(MessagingException e) {
				throw new RuntimeException(e);
			}
		
	}
}