package com.fl.bkf.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fl.bkf.ws.resource.Event;
import com.fl.bkf.ws.resource.Party;

@Configuration
public class AuthenticateCustomerService {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public Party getParty(String cniID) {
		return null;
	}
	
	public Event getLastEvent(String MSISDN) {
		return null;
	}
	
	
}
