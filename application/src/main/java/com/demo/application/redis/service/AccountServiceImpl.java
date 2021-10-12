package com.demo.application.redis.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.demo.application.model.AccountEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AccountServiceImpl implements AccountEventService{
	
	@Autowired
	private ReactiveRedisTemplate<String, AccountEvent> reactiveRedisTemplate;
	

	private String HASH = "event";
	
	private ReactiveHashOperations<String, String, AccountEvent> hashOperations;
	
	@PostConstruct
	private void init() {
		hashOperations=reactiveRedisTemplate.opsForHash();  
	}
	
	@Override
	public Mono<Boolean> save(AccountEvent event) {
		return hashOperations.put(HASH, event.getAccount(), event);
	}

	@Override
	public Flux<AccountEvent> getAll() {
		return hashOperations.values(HASH);
	}

	@Override
	public Mono<AccountEvent> findOne(String account) {
		return hashOperations.get(HASH, account);
	}

}
