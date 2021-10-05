package com.demo.application.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.application.dto.AccountTransactionDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class ApplicationWebClient {
	
	@Value("datasource.url")
	private static String url;

	private static WebClient webClient = WebClient.create("http://localhost:8080/transaction");

	public static Mono<AccountTransactionDTO> findOne(String account) {
		return webClient.get().uri("/findone" + account).retrieve().bodyToMono(AccountTransactionDTO.class);
	}
	
	public static Flux<AccountTransactionDTO> getAll(){
		return webClient.get().uri("/fetchall").retrieve().bodyToFlux(AccountTransactionDTO.class);
	}
	

}
