package com.fl.postgmdprocessor.dao;

import java.util.List;

public class DailyPostGMDProcessorDAO extends PostGMDProcessorDAO{

	
	@Override	
	public List<String> getNewEntries() {
		
		return jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST,String.class);
				
	}

	
	
}
