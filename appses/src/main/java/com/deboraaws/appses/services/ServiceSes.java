package com.deboraaws.appses.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

public class ServiceSes {
	
	public static void sendMessage(String message) {
		AwsCredentialsProvider credentials = new AwsCredentialsProvider() {

			@Override
			public AwsCredentials resolveCredentials() {
				return new AwsCredentials() {

					@Override
					public String accessKeyId() {
						return System.getenv("AWS_ACCESS_KEY");
					}

					@Override
					public String secretAccessKey() {
						return System.getenv("AWS_SECRET_ACCESS_KEY");
					}
					
				};
			}
			
		};
		
		SesClient sesClient = SesClient.builder()
										.region(Region.US_EAST_1)
										.credentialsProvider(credentials)
										.build();

		String textBody = "Olá, esse é um teste: " + message;
		String htmlBody = "<html>"+
							"<body>"+
								"<h1>Olá, este é um email teste!</h1>"+
								"<p>Enviado pela aplicação java da Debora</p>"+
								"<p>"+message+"</p>"+
							"</body>"+
						  "</html>";
		try{
			configureDispatch(sesClient, "email@email.com", "email@email.com", "email de teste", textBody, htmlBody);
			sesClient.close();
			System.out.println("Email enviado.");
		} catch(IOException | MessagingException e) {
			e.getStackTrace();
		}
	}
	
	
	public static void configureDispatch(SesClient client, String sender, String recipient, String subject, String bodyText, String bodyHtml) throws AddressException, MessagingException, IOException {
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);

		message.setSubject(subject, "UTF-8");
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

		MimeBodyPart text = new MimeBodyPart();
		text.setContent(bodyText, "text/plain; charset=UTF-8");

		MimeBodyPart html = new MimeBodyPart();
		html.setContent(bodyHtml, "text/html; chartset=UTF-8");

		MimeMultipart contentMsg = new MimeMultipart();
		contentMsg.addBodyPart(text);
		contentMsg.addBodyPart(html);

		message.setContent(contentMsg);

		try{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

			byte[] arr = new byte[buf.remaining()];
			buf.get(arr);

			SdkBytes data = SdkBytes.fromByteArray(arr);
			RawMessage rawMessage = RawMessage.builder().data(data).build();
			SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
					.rawMessage(rawMessage)
					.build();
			
			client.sendRawEmail(rawEmailRequest);

		} catch(SesException e){
			System.err.println(e.awsErrorDetails().errorMessage());
		}

	}
	
}
