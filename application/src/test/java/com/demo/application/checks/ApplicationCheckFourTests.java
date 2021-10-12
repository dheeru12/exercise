package com.demo.application.checks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.application.model.AccountEvent;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountEventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ApplicationCheckFourTests {
	
	@Mock
	AccountEventService service;
	
	@InjectMocks
	ApplicationCheckFour check;
	
	static List<AccountEvent> events = new ArrayList<AccountEvent>();
	
	@BeforeAll
	public static void init() {
		events.add(new AccountEvent("account1", "2021-10-12T12:10:30", "PhoneUpdate", "key","info"));
		events.add(new AccountEvent("account1", "2021-10-12T14:10:30", "AddressUpdate", "key","info"));	
	}
	
	@Test
	public void negativeScenario() {
		when(service.getAll()).thenReturn(Flux.fromIterable(events));
		
		Mono<CheckResponseDTO> result =  check.verify(new Transaction(1, "2021-10-12T12:11:30", null, null, null));
		
		StepVerifier.create(result).assertNext(c -> {
			assertFalse(c.isResult());
		}).verifyComplete();
		
	}
	
	@Test
	public void positiveScenario() {
		when(service.getAll()).thenReturn(Flux.fromIterable(events));
		
		Mono<CheckResponseDTO> result =  check.verify(new Transaction(1, "2021-10-12T13:11:30", null, null, null));
		
		StepVerifier.create(result).assertNext(c -> {
			assertTrue(c.isResult());
		}).verifyComplete();
		
	}
	
}
