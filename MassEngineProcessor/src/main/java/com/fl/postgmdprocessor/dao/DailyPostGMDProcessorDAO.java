package com.fl.postgmdprocessor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import oracle.jdbc.OracleTypes;

public class DailyPostGMDProcessorDAO extends PostGMDProcessorDAO{
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	private final static Logger logger = LoggerFactory.getLogger(DailyPostGMDProcessorDAO.class);

	private boolean int_customer_data_done = false;
	private boolean wimax_customer_data_done = false;
	private boolean gsm_customer_data_done = false;
	
	private SimpleJdbcCall jdbcCallInt;
	private SimpleJdbcCall jdbcCallWimax;
	private SimpleJdbcCall jdbcCallGsm;
	
	@Override
	public void init() {
		this.jdbcCallInt = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("DailySinglePostGMDInt").withoutProcedureColumnMetaDataAccess();
		this.jdbcCallWimax = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("DailySinglePostGMDWimax").withoutProcedureColumnMetaDataAccess();
		this.jdbcCallGsm = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCI_POSTGMD").withProcedureName("DailySinglePostGMDGsm").withoutProcedureColumnMetaDataAccess();
	}
	

	private List<String> getNewEntriesInt() {

		return jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_INT,String.class);

	}

	private List<String> getNewEntriesWimax() {

		return jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_WIMAX,String.class);

	}

	private List<String> getNewEntriesGsm() {

		return jdbcTemplate.queryForList(RequestTemplate.SELECT_REQUEST_GSM,String.class);

	}

	@Override
	public List<String> getNewEntries() {

		if(!int_customer_data_done){
			List<String> list = getNewEntriesInt();
			if(list==null || list.size()==0){
				int_customer_data_done=true;
				logger.info("All INTERNET customer data updated");
				return getNewEntries();
			}else
				return list;
		}else if(!wimax_customer_data_done){
			List<String> list = getNewEntriesWimax();
			if(list==null || list.size()==0){
				wimax_customer_data_done=true;
				logger.info("All WIMAX customer data updated");
				return getNewEntries();
			}else
				return list;
		}else if(!gsm_customer_data_done){
			List<String> list = getNewEntriesGsm();
			if(list==null || list.size()==0){
				gsm_customer_data_done=true;
				logger.info("All GSM customer data updated");
				return getNewEntries();
			}else
				return list;
		}
		return null;		
	}

	@Override
	public void singlePostGMD(String str){

		SimpleJdbcCall jdbcCall = null;
		
		if(!int_customer_data_done){
			jdbcCall = jdbcCallInt;
		}else if(!wimax_customer_data_done){
			jdbcCall = jdbcCallWimax;
		}else if(!gsm_customer_data_done){
			jdbcCall = jdbcCallGsm;
		} else
			return;

		jdbcCall.addDeclaredParameter(new SqlParameter("in_coId",  OracleTypes.INTEGER));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("in_coId",Integer.parseInt(str));

		jdbcCall.execute(map);	

	}


}
