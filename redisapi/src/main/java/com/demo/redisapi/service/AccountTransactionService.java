package com.demo.redisapi.service;

import com.demo.redisapi.models.AccountTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AccountTransactionService {
	public Mono<Boolean> save(AccountTransaction transaction);

	public Flux<AccountTransaction> getAll();

	public Mono<AccountTransaction> findOne(String account);
}
