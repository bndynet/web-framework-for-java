/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.bndy.wf.config.ApplicationConfig;

@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	ApplicationConfig applicationConfig;

	public void sendMail(String to, String subject, String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setFrom(this.applicationConfig.getMailSender());
		mail.setSubject(subject);
		mail.setText(content);
		javaMailSender.send(mail);
	}
}
