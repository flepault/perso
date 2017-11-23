package com.fl.postgmdprocessor.dao;

import java.util.List;

public class DailyPostGMDProcessorDAO extends PostGMDProcessorDAO{

	private List<String> getNewEntriesProcess() {

		List<String> listProcess = jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_PROCESS,String.class);

		return listProcess;

	}

	private List<String> getNewEntriesOnHold() {

		List<String> listProcess = jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_ONHOLD,String.class);

		return listProcess;

	}

	@Override
	public List<String> getNewEntries() {

		List<String> list = getNewEntriesOnHold();
		if(list==null || list.size()==0)
			return getNewEntriesProcess();
		return list;		
	}



}
