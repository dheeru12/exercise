package com.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.application.model.Transaction;
import com.demo.application.service.ApplicationService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ApplicationController {

	@Autowired
	ApplicationService service;

	@PostMapping("/transaction")
	public Flux<String> recieveTransaction(@RequestBody Transaction transaction) {	
		return service.executeChecks(transaction);
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
