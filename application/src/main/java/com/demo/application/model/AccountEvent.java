package com.demo.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEvent {
	private String account;
	private String time;
	private String eventType;
	private String eventKey;
	private String eventInfo;
}
