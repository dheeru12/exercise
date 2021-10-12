package com.demo.application.checks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class ApplicationCheckTwoTests {

	@Mock
	AccountTransactionService service;

	@InjectMocks
	ApplicationCheckTwo check;

	static List<AccountTransaction> accountList = new ArrayList<AccountTransaction>();

	@BeforeAll
	public static void init() {
		accountList.add(new AccountTransaction("account1", "2021-10-12T08:50:30", 300, null, null, null, "37N,43S",
				"192.168.10.2"));
		accountList.add(new AccountTransaction("account1", "2021-10-12T11:50:30", 300, null, null, null, "37N,43S",
				"192.168.10.2"));
		accountList.add(new AccountTransaction("account1", "2021-10-12T12:00:30", 300, null, null, null, "37N,43S",
				"192.168.10.2"));
	}

	/**
	 * checks if more than 25 transactions amounting to $300 during any 30 minute
	 * block. threshold value changed to 2
	 */
	@Test
	public void testFails() {
		Mockito.when(service.getAll()).thenReturn(Flux.fromIterable(accountList));

		Mono<CheckResponseDTO> result = check
				.verify(new Transaction(1, "2021-10-12T12:10:30", "192.168.10.2", null, 400));

		StepVerifier.create(result).assertNext(c -> {
			assertFalse(c.isResult());
		}).verifyComplete();

	}

	/**
	 * checks if more than 25 transactions amounting to $300 during any 30 minute
	 * block. threshold value changed to 2
	 */
	@Test
	public void positiveScenario() {
		Mockito.when(service.getAll()).thenReturn(Flux.fromIterable(accountList));

		Mono<CheckResponseDTO> result = check
				.verify(new Transaction(1, "2021-11-12T12:10:30", "192.168.10.2", null, 400));

		StepVerifier.create(result).assertNext(c -> {
			assertTrue(c.isResult());
		}).verifyComplete();
	}

}
