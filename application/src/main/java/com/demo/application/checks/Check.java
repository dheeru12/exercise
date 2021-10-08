package com.demo.application.checks;

import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;

import reactor.core.publisher.Mono;

public interface Check {
	public Mono<CheckResponseDTO> verify(Transaction transaction);
	public String checkDescription();
}
