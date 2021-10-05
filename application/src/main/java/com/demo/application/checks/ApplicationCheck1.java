package com.demo.application.checks;

import java.time.LocalTime;

import com.demo.application.client.ApplicationWebClient;
import com.demo.application.model.Transaction;

import reactor.core.publisher.Mono;

public class ApplicationCheck1 implements Check {

	private boolean isBeforeTwoHours(String dataTime, String transactionTime) {
		return LocalTime.parse(transactionTime).getHour() - LocalTime.parse(dataTime).getHour() <= 2;
	}

	public Mono<Boolean> verify(Transaction transaction) {
		return ApplicationWebClient.getAll().filter(d -> isBeforeTwoHours(d.getTime(), transaction.getTime())).count()
				.map(c -> c < 25L);

	}

}
