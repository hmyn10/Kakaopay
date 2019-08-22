package com.kakaopay.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class KakaopayBankPretestApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaopayBankPretestApplication.class, args);
	}

}
