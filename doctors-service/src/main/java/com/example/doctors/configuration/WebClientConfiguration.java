package com.example.doctors.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfiguration {

	@Bean
	public WebClient webClient(final WebClient.Builder  webclientBuilder) {
		return webclientBuilder.build();
	}

}
