package br.com.furafila.emailapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.furafila.emailapp.dto.WelcomeEmailDTO;
import br.com.furafila.emailapp.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${furafila.official-email}")
	private String officialEmail;

	@Value("${furafila.welcome-email-subject}")
	private String welcomeSubject;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Value("classpath:/templates/FuraFilaLogo1.png")
	private Resource resource;

	@Override
	public void sendWelcomeEmail(WelcomeEmailDTO welcomeEmailDTO) {

		try {

			Map<String, Object> templateModel = new HashMap<>();
			templateModel.put("corporateName", welcomeEmailDTO.getCorporateName());

			Context thymeleafContext = new Context();
			thymeleafContext.setVariables(templateModel);
			String htmlContent = thymeleafTemplateEngine.process("welcome-email.html", thymeleafContext);

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(officialEmail);
			helper.setTo(welcomeEmailDTO.getEmail());
			helper.setSubject(String.format(welcomeSubject, welcomeEmailDTO.getCorporateName()));
			helper.setText(htmlContent, true);
			helper.addInline("FuraFilaLogo1.png", resource);

			emailSender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
