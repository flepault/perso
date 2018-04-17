package com.fl.bkf.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthenticateCustomerException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7602767453610312871L;

	public AuthenticateCustomerException(String cniID,String LDN) {
		super("Could not authenticate customer with CNIID:"+cniID+" and 2LDN : "+LDN);
	}

}