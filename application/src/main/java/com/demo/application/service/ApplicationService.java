package com.demo.application.service;

import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import com.demo.application.checks.Check;
import com.demo.application.model.Transaction;

@Service
public class ApplicationService {

	public List<String> executeChecks(Transaction transaction) {

		List<String> result = new ArrayList<String>();
		ConfigurationBuilder builder = new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.demo.application"));

		Reflections reflections = new Reflections(builder);

		reflections.getSubTypesOf(Check.class).parallelStream().forEach(aclass -> {
			try {
				Check check = aclass.getDeclaredConstructor().newInstance();
				check.verify(transaction).subscribe(element -> {
					if (!element)
						result.add(aclass.getName());
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return result;

	}
}
