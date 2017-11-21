package com.fl.postgmdprocessor.dao;

import java.util.List;

public class OnGoingPostGMDProcessorDAO extends PostGMDProcessorDAO{


	@Override	
	public List<String> getNewEntries() {

		List<String> list = jdbcTemplate.queryForList("select request from oci_postgmd_process where status = 2 and rownum <= 1000", String.class);

		updateRequestStatus(list);
		return	list;
	}

}
