package com.fl.bkf.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fl.bkf.ws.resource.Party;
import com.fl.bkf.ws.resource.ProductInventory;
import com.fl.bkf.ws.service.AuthenticateCustomerService;
import com.fl.bkf.ws.service.CheckCommercialEligibilityService;

@RestController
public class OBFWebServiceController {

	@Autowired
	private CheckCommercialEligibilityService checkCommercialEligibilityService;

	@Autowired
	private AuthenticateCustomerService authenticateCustomerService;

	@RequestMapping("/CheckCommercialEligibility")
	public void checkCommercialEligibility(@RequestParam(value="operation") String operation,
			@RequestParam(value="MSISDN") String MSISDN,
			@RequestParam(value="IMSI") String IMSI) {

		List<ProductInventory> productInventoryList = checkCommercialEligibilityService.getProductInventoryMSISDN(MSISDN);
		if(productInventoryList!=null) {
			for(ProductInventory pi:productInventoryList) {
				//check if the MSISDN is known and active and that the offer is prepaid
				//check there is no barring()
				;
			}
		}

		productInventoryList = checkCommercialEligibilityService.getProductInventoryIMSI(IMSI);

		if(productInventoryList!=null) {
			for(ProductInventory pi:productInventoryList) {
				//check new sim card exist is not used and not blocked()
				;
			}
		}

		return;
	}

	@RequestMapping("/AuthenticateCustomer")
	public void authenticateCustomer(@RequestParam(value="CNIID") String cniID, 	
			@RequestParam(value="2LDN") String LDN) {

		Party party = authenticateCustomerService.getParty(cniID);	


		//check if the customer identification is OK()
		//comparer la pièce ID saisie et la valeur renvoyée par BSCS()

		authenticateCustomerService.getLastEvent(LDN);
		//check last dialed numbers()
		return ;
	}

}
