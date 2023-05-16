package br.com.furafila.emailapp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.furafila.emailapp.message.WelcomeEmailMessage;
import br.com.furafila.emailapp.service.EmailSenderService;

@Component
public class EmailConsumer {
 
	@Autowired
	private EmailSenderService emailSenderService;

	@RabbitListener(queues = { "${furafila.queue.welcome-email}" })
	public void consumerWelcomeEmail(WelcomeEmailMessage welcomeEmailMessage) {

		emailSenderService.sendWelcomeEmail(welcomeEmailMessage.getWelcomeEmailDTO());

	}

}
