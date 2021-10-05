package com.demo.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.application.model.ResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.service.ApplicationService;

@RestController
@RequestMapping("/api")
public class ApplicationController {

	@Autowired
	ApplicationService service;

	@PostMapping("/transaction")
	public ResponseDTO recieveTransaction(@RequestBody Transaction transaction) {
		List<String> result = service.executeChecks(transaction);
		return new ResponseDTO(result.size(), result);
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
