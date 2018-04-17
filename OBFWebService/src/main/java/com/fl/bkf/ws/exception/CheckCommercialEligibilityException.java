package com.fl.bkf.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CheckCommercialEligibilityException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7602767453610312871L;

	public CheckCommercialEligibilityException(String operation,String MSISDN,String IMSI) {
		super("No Commercial "+operation+" Eligibility for MSISDN:"+MSISDN+" and IMSI : "+IMSI);
	}

}
