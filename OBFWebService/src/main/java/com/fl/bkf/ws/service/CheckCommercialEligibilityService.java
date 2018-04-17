package com.fl.bkf.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fl.bkf.ws.resource.ProductInventory;

@Configuration
public class CheckCommercialEligibilityService {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public List<ProductInventory> getProductInventoryMSISDN(String MSISDN) {
		return null;
	}
	
	public List<ProductInventory> getProductInventoryIMSI(String IMSI) {
		return null;
	}
	
	
}
