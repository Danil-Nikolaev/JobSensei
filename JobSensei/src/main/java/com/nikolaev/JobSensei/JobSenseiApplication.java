package com.nikolaev.JobSensei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class JobSenseiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(JobSenseiApplication.class, args);
	}

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:5500") // Разрешенный источник (ваш фронтенд)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true);
    }

}
