package br.com.adblock.cadastro.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracao {

	@Bean
	ModelMapper obterModelMapper() {
		return new ModelMapper();
	}

//configurações do Rabbitmq//
}