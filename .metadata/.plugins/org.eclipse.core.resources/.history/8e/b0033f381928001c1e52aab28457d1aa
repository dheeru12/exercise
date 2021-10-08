package com.demo.application.checks;

import com.demo.application.model.Transaction;

import reactor.core.publisher.Mono;

public interface Check {
	public Mono<Boolean> verify(Transaction transaction);
	public String checkDescription();
}
