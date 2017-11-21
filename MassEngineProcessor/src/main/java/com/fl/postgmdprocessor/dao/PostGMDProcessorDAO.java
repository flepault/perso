package com.fl.postgmdprocessor.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import oracle.jdbc.OracleTypes;

@Configurable(preConstruction = true)
public abstract class PostGMDProcessorDAO{

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public abstract List<String> getNewEntries();

	public void updateRequestStatus(List<String> requestList){

		if(requestList!=null && requestList.size()!=0){

			String sql = RequestTemplate.UPDATE_TO_WAITING_STATUS;

			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			namedParameterJdbcTemplate.update(sql, Collections.singletonMap("REQUEST_ID_LIST",requestList));

		}


	}

	public void singlePostGMDProcess(String request){


		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("OnGoingSinglePostGMDProcess");

		jdbcCall.addDeclaredParameter(new SqlParameter("in_request",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_request",Integer.parseInt(request));

		jdbcCall.execute(map);	


	}


}
