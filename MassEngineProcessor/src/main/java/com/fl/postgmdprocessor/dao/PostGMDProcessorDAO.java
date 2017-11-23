package com.fl.postgmdprocessor.dao;

import java.util.ArrayList;
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

	public void updateRequestStatus(List<String> list){
		if(list!=null && list.size()!=0){

			String[] fields = list.get(0).split("\\,");
			
			List<String> cleanList = new ArrayList<String>();
			for(String s:list){
				cleanList.add(s.split("\\,")[1]);
			}

			if(fields[0].equals("PROCESS"))
				updateRequestStatusProcess(cleanList);	
			else
				updateRequestStatusOnHold(cleanList);

		}

	}


	private void updateRequestStatusOnHold(List<String> list){

		String sql = RequestTemplate.UPDATE_TO_WAITING_STATUS_ONHOLD;

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, Collections.singletonMap("CO_ID_LIST",list));

	}

	private void updateRequestStatusProcess(List<String> list){

		String sql = RequestTemplate.UPDATE_TO_WAITING_STATUS_PROCESS;

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		namedParameterJdbcTemplate.update(sql, Collections.singletonMap("REQUEST_ID_LIST",list));

	}

	public void singlePostGMD(String request){


		String[] fields = request.split("\\,");

		if(fields[0].equals("PROCESS"))
			singlePostGMDProcess(fields[1]);
		else
			singlePostGMDOnHold(fields[1]);

	}


	private void singlePostGMDProcess(String request){


		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("OnGoingSinglePostGMDProcess");

		jdbcCall.addDeclaredParameter(new SqlParameter("in_request",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_request",Integer.parseInt(request));

		jdbcCall.execute(map);	


	}

	private void singlePostGMDOnHold(String request){


		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("OnGoingSinglePostGMDOnHold");

		jdbcCall.addDeclaredParameter(new SqlParameter("in_coId",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_coId",Integer.parseInt(request));

		jdbcCall.execute(map);	


	}


}
