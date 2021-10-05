package com.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionDTO {
	private String account;
	private Integer amount;
	private String time;
	private String beneficiary;
	private String channel;
	private String channelKey;
	private String gps;
}
