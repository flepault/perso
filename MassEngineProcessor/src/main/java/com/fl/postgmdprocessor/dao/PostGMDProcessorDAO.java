package com.fl.postgmdprocessor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PostGMDProcessorDAO{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public void getNewEntries() {
		
		jdbcTemplate.execute("SELECT 1 FROM DUAL");
		
	}
	
}
