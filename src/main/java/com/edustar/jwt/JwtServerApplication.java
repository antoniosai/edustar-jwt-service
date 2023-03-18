package com.edustar.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAsync
public class JwtServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtServerApplication.class, args);
	}

}
