package com.fl.postgmdprocessor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;

@Configurable(preConstruction = true)
public abstract class PostGMDProcessorDAO{

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public abstract List<String> getNewEntries();	
	
	public abstract void singlePostGMD(String str);

}
