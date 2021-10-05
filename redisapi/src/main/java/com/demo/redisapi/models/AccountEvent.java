package com.demo.redisapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvent {
	private String account;
	private String eventType;
	private String eventKey;
	private String eventInfo;
	
}
