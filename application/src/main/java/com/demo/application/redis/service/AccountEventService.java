package com.demo.application.redis.service;

import com.demo.application.model.AccountEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountEventService {
	public Mono<Boolean> save(AccountEvent event);

	public Flux<AccountEvent> getAll();

	public Mono<AccountEvent> findOne(String account);
}
