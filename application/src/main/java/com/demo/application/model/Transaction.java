package com.demo.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	private Integer id;
	private String time;
	private String ipAddress;
	private String location;
	private Integer amount;
}
