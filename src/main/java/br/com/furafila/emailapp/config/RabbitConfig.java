package br.com.furafila.emailapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Value("${furafila.queue.welcome-email}")
	private String welcomeEmailQueueName;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${furafila.queue.exchange.direct.fura-fila-direct-exchange}")
	private String directExchange;

	@Bean
	public Queue queue() {
		return new Queue(welcomeEmailQueueName, false);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(directExchange);
	}

	@Bean
	public Binding welcomeQueueBinding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(welcomeEmailQueueName);
	}

	@Bean
	public MessageConverter consumerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}