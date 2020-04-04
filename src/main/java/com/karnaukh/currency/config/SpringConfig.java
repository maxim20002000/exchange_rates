package com.karnaukh.currency.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.karnaukh.currency")
public class SpringConfig {

	@Bean
	public Logger logger() {
		return LogManager.getLogger();
	}
}
