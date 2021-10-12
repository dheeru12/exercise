package com.demo.application.checks;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.application.model.AccountEvent;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountEventService;

import reactor.core.publisher.Mono;

@Component
public class ApplicationCheckFour implements Check{
	
	@Autowired
	AccountEventService service;
	
	private boolean isValid(AccountEvent event,Transaction transaction) {
		LocalDateTime eventTime =LocalDateTime.parse(event.getTime());
		LocalDateTime transactionTime = LocalDateTime.parse(transaction.getTime());
		
		return event.getEventType().equals("PhoneUpdate") 
				&&  !transactionTime.minusMinutes(30L).isAfter(eventTime) ;
	}

	@Override
	public Mono<CheckResponseDTO> verify(Transaction transaction) {
		return service.getAll().any(event -> isValid(event, transaction)).map(result -> {
			return new CheckResponseDTO(!result,checkDescription());
		});
	}

	@Override
	public String checkDescription() {
		return "Checks transactions of less active accounts";
	}
	
}
