package com.demo.application.checks;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountTransactionService;

import reactor.core.publisher.Mono;


@Component
public class ApplicationCheck1 implements Check {
	
	
	@Autowired
	AccountTransactionService service;

	private boolean isBeforeTwoHours(String dataTime, String transactionTime) {
		return LocalTime.parse(transactionTime).getHour() - LocalTime.parse(dataTime).getHour() <= 2;
	}

	public Mono<Boolean> verify(Transaction transaction) {
		return service.getAll().filter(d -> isBeforeTwoHours(d.getTime(), transaction.getTime())).count()
				.map(c -> c < 25L);
	}

	@Override
	public String checkDescription() {
		return "Check of anomaly transactions";
	}

}
