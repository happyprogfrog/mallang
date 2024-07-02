package me.progfrog.mallang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MallangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallangApplication.class, args);
	}

}
