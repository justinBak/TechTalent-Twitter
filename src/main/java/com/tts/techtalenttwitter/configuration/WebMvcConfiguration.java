package com.tts.techtalenttwitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//If you mark a class with a configuration annotation...
//One of the things it means is that you can add instructions
//on hot to make injectable (autowired) objects to Spring Boot
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	//The @Bean annotation tells Spring Boot how to create objects when they are needed.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder =
				new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
}
