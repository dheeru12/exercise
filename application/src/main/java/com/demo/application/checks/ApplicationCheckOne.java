package com.demo.application.checks;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountTransactionService;

import reactor.core.publisher.Mono;

@Component
public class ApplicationCheckOne implements Check {

	@Autowired
	AccountTransactionService service;

	private boolean isBeforeTwoHours(String dataTime, String transactionTime) {
		LocalDateTime dataDateTime = LocalDateTime.parse(dataTime);
		LocalDateTime transactionDateTime = LocalDateTime.parse(transactionTime);
		return !transactionDateTime.minusHours(2L).isAfter(dataDateTime);
	}

	public Mono<CheckResponseDTO> verify(Transaction transaction) {
		return service.getAll().filter(d -> {
			return isBeforeTwoHours(d.getTime(), transaction.getTime());
		}).count().map(c -> {
			return new CheckResponseDTO(c < 2L, checkDescription());
		});
	}

	@Override
	public String checkDescription() {
		return "Check of anomaly transactions";
	}

}
