package com.demo.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.application.checks.Check;
import com.demo.application.model.Transaction;

import reactor.core.publisher.Flux;

@Service
public class ApplicationService {

	@Autowired
	private List<Check> checks;

	public Flux<String> executeChecks(Transaction transaction) {

		List<String> result = new ArrayList<String>();
		
		checks.stream().forEach(check -> {
			check.verify(transaction).subscribe(element -> {
				if (element)
					result.add(check.checkDescription());
			});
		});
		
		return Flux.fromIterable(result);

	}
}
