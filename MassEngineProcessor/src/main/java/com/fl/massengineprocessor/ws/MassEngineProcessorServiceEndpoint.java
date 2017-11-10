package com.fl.massengineprocessor.ws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapBodyException;

import com.fl.massengineprocessor.engine.FileManagment;
import com.fl.massengineprocessor.thread.InputExecutorThread;
import com.fl.massengineprocessor.ws.bean.MassEngineProcessorServiceRequest;
import com.fl.massengineprocessor.ws.bean.MassEngineProcessorServiceResponse;

@Endpoint
public class MassEngineProcessorServiceEndpoint {

	private static Logger logger = Logger.getLogger(MassEngineProcessorServiceEndpoint.class.getName());

	//Attention a ce que ce soit le namespace du xsd
	private static final String NAMESPACE_URI = "http://bean.ws.massengineprocessor.fl.com/";	

	@Autowired
	private InputExecutorThread inputExecutorThread;

	//LocalPart à mettre en Majuscule
	//Nom du service, Object en entrée + Request , Object en sortie + Response
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "MassEngineProcessorServiceRequest")
	public @ResponsePayload MassEngineProcessorServiceResponse massEngineProcessorServiceRequest(@RequestPayload MassEngineProcessorServiceRequest request){

		MassEngineProcessorServiceResponse resp = null;	
		
		logger.info("New SOAP request received, request id : "+request.getId()+", fileName : "+request.getFile().getName() );
		
		try{	
			ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(request.getFile().getBytes()));			

			BufferedReader br = new BufferedReader(new InputStreamReader(bais));
			String line;
			while ((line = br.readLine()) != null) {
				
							
				FileManagment.createFile(line);

				inputExecutorThread.pushElement(line);

			}
			resp = new MassEngineProcessorServiceResponse();
			resp.setId(request.getId());

		}catch(Exception e){
			logger.error(e.getMessage());
			throw new SoapBodyException(e);
		}

		return resp;

	}


}