package com.fl.postgmdprocessor.dao;

import java.util.List;

public class OnGoingPostGMDProcessorDAO extends PostGMDProcessorDAO{


	@Override	
	public List<String> getNewEntries() {

		List<String> list = jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST, String.class);

		updateRequestStatus(list);
		return	list;
	}

}
