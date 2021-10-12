package com.demo.application.checks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.application.model.AccountTransaction;
import com.demo.application.model.CheckResponseDTO;
import com.demo.application.model.Transaction;
import com.demo.application.redis.service.AccountTransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author dheerajkumar
 *
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ApplicationCheckOneTest {

	@Mock
	AccountTransactionService service;

	@InjectMocks
	ApplicationCheckOne check;

	static List<AccountTransaction> accountList = new ArrayList<AccountTransaction>();

	@BeforeAll
	public static void init() {
		accountList.add(new AccountTransaction("account1", "2021-10-12T08:50:30", 300, null, null, null, "37N,43S",null));
		accountList.add(new AccountTransaction("account1", "2021-10-12T11:50:30", 300, null, null, null, "37N,43S",null));
		accountList.add(new AccountTransaction("account1", "2021-10-12T12:50:30", 300, null, null, null, "37N,43S",null));
	}

	/**
	 * checks if more than 25 transactions happened before 2 hours and check fails if before 2 hours
	 * changed the threshold value to 2 in ApplicationCheckOne
	 * original threshold value 25
	 */
	@Test
	public void verifyTestFails() {

		Mockito.when(service.getAll()).thenReturn(Flux.fromIterable(accountList)) ;

		Mono<CheckResponseDTO> result = check.verify(new Transaction(1, "2021-10-12T13:50:30", null, null, 300));

		StepVerifier.create(result).assertNext(c -> {
			assertFalse(c.isResult());
		}).verifyComplete();
	}
	
	/**
	 * checks if any two transactions happened before 2 hours and check fails if before 2 hours
	 * changed the threshold value to 2 in ApplicationCheckOne
	 * original threshold value 25
	 */
	@Test
	public void verifyTestPassed() {
		Mockito.when(service.getAll()).thenReturn(Flux.fromIterable(accountList));

		Mono<CheckResponseDTO> result = check.verify(new Transaction(1, "2021-10-12T14:50:30", null, null, 300));

		StepVerifier.create(result).assertNext(c->{
			assertTrue(c.isResult());
		}).verifyComplete();
	}
	
	

	
	
	
}
