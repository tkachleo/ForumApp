package com.forum.client.util;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.forum.managers.LogManager;
import com.sun.mail.smtp.SMTPTransport;

/**
 * Simple demonstration of using the javax.mail API.
 *
 * Run from the command line. Please edit the implementation
 * to use correct email addresses and host name.
 */
public final class Emailer {

	private final String address = "looe87@gmail.com";
	private final String password = "lt03111987";

	/**
	 * Send a single email.
	 */
	public void sendEmail(String aToEmailAddr, String aSubject, String aBody){


		Properties props = System.getProperties();
		props.put("mail.smtps.host","smtp.gmail.com");
		props.put("mail.smtps.auth","true");

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(address,"Leon"));
			message.addRecipient(
					Message.RecipientType.TO, new InternetAddress(aToEmailAddr)
					);
			message.setSubject(aSubject);
			message.setText(aBody);
			SMTPTransport t =
					(SMTPTransport)session.getTransport("smtps");
			t.connect("smtp.gmail.com",465, address, password);
			t.sendMessage(message, message.getAllRecipients());
			
		}
		catch (MessagingException ex){
			LogManager.print("Cannot send email. " + ex);
			System.err.println("Cannot send email. " + ex);
		} catch (UnsupportedEncodingException e) {
			LogManager.print("Cannot send email. " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Allows the config to be refreshed at runtime, instead of
	 * requiring a restart.
	 */
	public static void refreshConfig() {
		fMailServerConfig.clear();
		fetchConfig();
	}

	// PRIVATE 

	private static Properties fMailServerConfig = new Properties();

	static {
		fetchConfig();
	}

	/**
	 * Open a specific text file containing mail server
	 * parameters, and populate a corresponding Properties object.
	 */
	private static void fetchConfig() {
		Path path = Paths.get("C:\\Temp\\MyMailServer.txt");
		try  {
			InputStream input = Files.newInputStream(path);
			fMailServerConfig.load(input);
		}
		catch (IOException ex){
			System.err.println("Cannot open and load mail server properties file.");
		}
	}
} 