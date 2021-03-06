package com.demo.application.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private String account;
	private String time;
	private Integer amount;
	private String beneficiary;
	private String channel;
	private String channelKey;
	private String gps;
	private String ipAddress;
	
}
	