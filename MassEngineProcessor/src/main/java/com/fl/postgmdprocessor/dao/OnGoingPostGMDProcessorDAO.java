package com.fl.postgmdprocessor.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import oracle.jdbc.OracleTypes;

public class OnGoingPostGMDProcessorDAO extends PostGMDProcessorDAO{
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall jdbcCallRequest;
	private SimpleJdbcCall jdbcCallCoId;
	
	@Override
	public void init() {
		this.jdbcCallRequest = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("OnGoingSinglePostGMDProcess").withoutProcedureColumnMetaDataAccess();
		this.jdbcCallCoId = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("OnGoingSinglePostGMDOnHold").withoutProcedureColumnMetaDataAccess();
	}
	
	public void updateRequestStatus(List<String> list){
		if(list!=null && list.size()!=0){

			String[] fields = list.get(0).split("\\,");
			
			List<String> cleanList = new ArrayList<String>();
			for(String s:list){
				cleanList.add(s.split("\\,")[1]);
			}
			
			if(fields[0].equals("REQUEST"))
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

	private List<String> getNewEntriesProcess() {
		
		List<String> list = jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_PROCESS,String.class);

		updateRequestStatus(list);
		
		return list;

	}

	private List<String> getNewEntriesOnHold() {

		List<String> list = jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_ONHOLD,String.class);
		
		updateRequestStatus(list);
		
		return list;

	}

	@Override
	public List<String> getNewEntries() {

		List<String> list = getNewEntriesOnHold();
		if(list==null || list.size()==0)
			return getNewEntriesProcess();
		return list;		
	}
	
	@Override
	public void singlePostGMD(String str){

		String[] fields = str.split("\\,");

		if(fields[0].equals("REQUEST"))
			singlePostGMDRequest(fields[1]);
		else
			singlePostGMDCoid(fields[1]);

	}
	
	private void singlePostGMDRequest(String str){


		jdbcCallRequest.addDeclaredParameter(new SqlParameter("in_request",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_request",Integer.parseInt(str));

		jdbcCallRequest.execute(map);	


	}
	
	private void singlePostGMDCoid(String str){

		jdbcCallCoId.addDeclaredParameter(new SqlParameter("in_coId",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_coId",Integer.parseInt(str));

		jdbcCallCoId.execute(map);	


	}

}
