package com.fl.massengineprocessor.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import oracle.jdbc.OracleTypes;


public class MassEngineProcessorDAO{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	
//	ocit_tools.AvisoChangeTM ( pionCoId in integer,
//            pionTMCode in integer,
//
//            pionSPCode in integer,
//
//            pionPVCombiId in integer,
//
//            piosUsername in varchar2,
//
//            pionRequestId out integer );

	public Integer changeTM(Integer tmcode,Integer spcode,Integer pvCombiId,String userName){		

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("SPERTO").withCatalogName("OCIT_TOOLS").withProcedureName("AVISOCHANGETM");

		jdbcCall.addDeclaredParameter(new SqlParameter("pionTMCode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlParameter("pionSPCode", OracleTypes.INTEGER));	
		jdbcCall.addDeclaredParameter(new SqlParameter("pionPVCombiId", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlParameter("piosUsername", OracleTypes.VARCHAR));	
		jdbcCall.addDeclaredParameter(new SqlOutParameter("pionRequestId", OracleTypes.INTEGER));	
				
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pionTMCode",tmcode);
		map.put("pionSPCode", spcode);
		map.put("pionPVCombiId", pvCombiId);
		map.put("piosUsername", userName);
		
		Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(map);	
		
		return (Integer)simpleJdbcCallResult.get("pionRequestId");	

	}
	
}
