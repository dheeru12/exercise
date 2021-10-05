package com.demo.application.checks;

import java.time.LocalTime;

import com.demo.application.client.ApplicationWebClient;
import com.demo.application.dto.AccountTransactionDTO;
import com.demo.application.model.Transaction;

import reactor.core.publisher.Mono;


public class ApplicationCheck2 implements Check{

	private static boolean isValid(AccountTransactionDTO dto, Transaction transaction) {
		return transaction.getIpAddress().equals(transaction.getIpAddress())
				&& LocalTime.parse(transaction.getTime()).getMinute() - LocalTime.parse(dto.getTime()).getMinute() < 30
				&& dto.getAmount() >= 300;
	}

	
	public Mono<Boolean> verify(Transaction transaction) {
		Mono<Boolean> result = ApplicationWebClient.getAll().filter(account -> isValid(account, transaction)).count().map(c -> c<25L);
		return result;
	}

}
