package com.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.application.model.AccountTransaction;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountTransactionService;
import com.demo.application.service.ApplicationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApplicationController {

	@Autowired
	ApplicationService service;
	
	@Autowired
	AccountTransactionService transactionService;
	
	@PostMapping("/save")
	public Mono<Boolean> save(@RequestBody AccountTransaction transaction){
		return transactionService.save(transaction);
	}

	@PostMapping("/transaction")
	public Flux<CheckResponseDTO> recieveTransaction(@RequestBody Transaction transaction) {	
		return service.executeChecks(transaction);
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
