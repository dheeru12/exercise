package com.demo.application.redis.service;

import com.demo.application.model.AccountTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTransactionService {
	public Mono<Boolean> save(AccountTransaction transaction);

	public Flux<AccountTransaction> getAll();

	public Mono<AccountTransaction> findOne(String account);
}
