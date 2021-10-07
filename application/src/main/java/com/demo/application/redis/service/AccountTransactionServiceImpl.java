package com.demo.application.redis.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.demo.application.model.AccountTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService{
	
	
	@Autowired
	@Qualifier("reactiveRedisTemplateAccountTransaction")
	private ReactiveRedisTemplate<String, AccountTransaction> reactiveRedisTemplateAccountTransaction;
	
	@Value("${redisdb.hash}")
	private String HASH;
	
	private ReactiveHashOperations<String, String, AccountTransaction> hashOperations;
	
	@PostConstruct
	private void init() {
		hashOperations=reactiveRedisTemplateAccountTransaction.opsForHash();  
	}
	
	
	@Override
	public Mono<Boolean> save(AccountTransaction transaction) {
		return hashOperations.put(HASH, transaction.getAccount(), transaction);
	}

	@Override
	public Flux<AccountTransaction> getAll() {
		return hashOperations.values(HASH);
	}

	@Override
	public Mono<AccountTransaction> findOne(String account) {
		return hashOperations.get(HASH, account);
	}
	
}
