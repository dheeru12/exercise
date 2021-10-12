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
public class ApplicationCheckTwo implements Check{
	
	@Autowired
	AccountTransactionService service;
	

	private boolean isValid(AccountTransaction dto, Transaction transaction) {
		
		LocalDateTime dataDateTime = LocalDateTime.parse(dto.getTime());
		LocalDateTime transactionDateTime = LocalDateTime.parse(transaction.getTime());
		
		return transaction.getIpAddress().equals(dto.getIpAddress())
				&& !transactionDateTime.minusMinutes(30L).isAfter(dataDateTime)
				&& dto.getAmount() >= 300;
	}

	
	public Mono<CheckResponseDTO> verify(Transaction transaction) {
		Mono<CheckResponseDTO> result = service.getAll().filter(account -> isValid(account, transaction)).count().map(c -> {
			return new CheckResponseDTO(c<2L,checkDescription());
		});
		return result;
	}


	@Override
	public String checkDescription() {
		return "Checks if multiple transactions take place from the same IP Address";
	}

}
