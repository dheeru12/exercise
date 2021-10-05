package com.demo.redisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Hooks;

@SpringBootApplication
public class RedisapiApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();
		SpringApplication.run(RedisapiApplication.class, args);
	}

}
