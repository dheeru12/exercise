package com.demo.redisapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.redisapi.models.AccountTransaction;
import com.demo.redisapi.service.AccountTransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class AccountTransactionController {
	
	
	@Autowired
	AccountTransactionService transactionService;
	
	@PostMapping("/save")
	public Mono<Boolean> save(@RequestBody AccountTransaction transaction) {
		return transactionService.save(transaction);
	}
	
	@GetMapping("/fetchall")
	public Flux<AccountTransaction> fetchAll(){
		return transactionService.getAll();
	}
	
	
	@GetMapping("/findone/{account}")
	public Mono<AccountTransaction> findOne(@PathVariable String account){
		return transactionService.findOne(account);
	}
	
	@GetMapping("/health")
	public Mono<String> check(){
		return Mono.just("OK");
	}
	
	
}
