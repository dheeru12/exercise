package com.demo.application.checks;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.application.model.AccountTransaction;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountTransactionService;

import reactor.core.publisher.Mono;

@Component
public class ApplicationCheckThree implements Check {

	@Autowired
	AccountTransactionService service;

	private boolean verifyIfSamePlaces(AccountTransaction accountTransaction, Transaction transaction) {
		LocalDateTime dataDateTime = LocalDateTime.parse(accountTransaction.getTime());
		LocalDateTime transactionDateTime = LocalDateTime.parse(transaction.getTime());
		
		return !accountTransaction.getGps().equals(transaction.getLocation())
				&& !transactionDateTime.minusMinutes(30L).isAfter(dataDateTime);
	}

	@Override
	public Mono<CheckResponseDTO> verify(Transaction transaction) {
		return service.getAll().any(at -> verifyIfSamePlaces(at,transaction)).map( c -> {
			return new CheckResponseDTO(!c,checkDescription());
		});
	}

	@Override
	public String checkDescription() {
		return "Check Geographic viability if simultaneously transactions happen at different places";
	}

}
