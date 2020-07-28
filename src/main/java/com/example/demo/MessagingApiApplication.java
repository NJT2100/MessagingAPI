package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;

@EnableCassandraAuditing
@SpringBootApplication
public class MessagingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingApiApplication.class, args);
	}

}
