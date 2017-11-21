package com.fl.postgmdprocessor.dao;


/** .
 * @author efrelep
 *
 */
public class RequestTemplate {
	
	public static String UPDATE_TO_WAITING_STATUS = "update oci_postgmd_process set status = 3 where request in (:REQUEST_ID_LIST)";
		
	public static String SELECT_REQUEST = "select request from oci_postgmd_process where status = 2 and rownum <= 1000";
	
}
