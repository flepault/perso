package com.fl.postgmdprocessor.dao;

import java.util.List;

public class DailyPostGMDProcessorDAO extends PostGMDProcessorDAO{

	
	@Override	
	public List<String> getNewEntries() {
		
		return jdbcTemplate.queryForList("select request from oci_postgmd_process where status = 2 and rownum <= 1000",String.class);
				
	}

	
	
}
