package com.fl.massengineprocessor.thread;

import java.math.BigInteger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fl.engineprocessor.thread.OutputExecutorThread;
import com.fl.massengineprocessor.ws.skeleton.ObjectFactory;
import com.fl.massengineprocessor.ws.skeleton.UpDateTaskItems;

@Component
@Profile("massengineprocessor")
public class MassEngineOutputExecutorThread  extends OutputExecutorThread{

	private static Logger logger = LogManager.getLogger(MassEngineOutputExecutorThread.class.getName());

	private ObjectFactory factory = new ObjectFactory();
	
	private WebServiceTemplate webServiceTemplate;

	public MassEngineOutputExecutorThread(){
		super();
		initWS();		
	}
	
	private void initWS(){
		
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
	    jaxb2Marshaller.setContextPath("com.fl.massengineprocessor.ws.skeleton");
		
		webServiceTemplate = new WebServiceTemplate();
	    webServiceTemplate.setMarshaller(jaxb2Marshaller);
	    webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
	    webServiceTemplate.setDefaultUri("http://66.70.177.199/soap/services?wsdl");
		
	}
	
	@Override
	protected void processRequest(String request) {
		String[] fields = request.split("\\,");
		
		UpDateTaskItems uti = factory.createUpDateTaskItems();
		uti.setNd(factory.createUpDateTaskItemsNd(fields[0]));
		uti.setStatus(factory.createUpDateTaskItemsStatus("OK"));
		uti.setGmdRequestId(factory.createUpDateTaskItemsGmdRequestId(new BigInteger("-1")));

		logger.debug("Response to APP : "+request);
		webServiceTemplate.marshalSendAndReceive(uti);
		
	}

}
