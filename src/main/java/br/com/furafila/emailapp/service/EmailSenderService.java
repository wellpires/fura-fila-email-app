package br.com.furafila.emailapp.service;

import br.com.furafila.emailapp.dto.WelcomeEmailDTO;

public interface EmailSenderService {

	void sendWelcomeEmail(WelcomeEmailDTO welcomeEmailDTO);

}
