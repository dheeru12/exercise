package com.demo.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.application.checks.Check;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;

import reactor.core.publisher.Flux;

@Service
public class ApplicationService {

	@Autowired
	private List<Check> checks;

	public Flux<CheckResponseDTO> executeChecks(Transaction transaction) {
		return Flux.concat(checks.parallelStream().map(check -> {
			return check.verify(transaction);
		}).collect(Collectors.toList()));
	}
}
