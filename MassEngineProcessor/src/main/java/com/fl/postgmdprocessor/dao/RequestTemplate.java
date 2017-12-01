package com.fl.postgmdprocessor.dao;


/** .
 * @author efrelep
 *
 */
public class RequestTemplate {
	
	public static String UPDATE_TO_WAITING_STATUS_PROCESS = "update oci_postgmd_process set status = 3 where request in (:REQUEST_ID_LIST)";
		
	public static String UPDATE_TO_WAITING_STATUS_ONHOLD = "update oci_postgmd_on_hold set status = 3 where co_id in (:CO_ID_LIST)";
		
	public static String SELECT_REQUEST_PROCESS = "select 'REQUEST,'||request from oci_postgmd_process where status = 2 and rownum <= 1000";
	
	public static String SELECT_REQUEST_ONHOLD = "select 'CO_ID,'||co_id from oci_postgmd_on_hold where status = 2 and rownum <= 1000";

	public static String SELECT_REQUEST_INT = "select contrat from AVISO_CUSTOMER_DATA where LAST_UPDATE <= sysdate-1";

	public static String SELECT_REQUEST_WIMAX = "select contrat from WIMAX_CUSTOMER_DATA where LAST_UPDATE <= sysdate-1";

	public static String SELECT_REQUEST_GSM = "select co_id from GSM_CUSTOMER_DATA where LAST_UPDATE <= sysdate-8";
	
}
