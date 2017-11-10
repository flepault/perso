package com.fl.massengineprocessor.thread;

import java.math.BigInteger;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fl.massengineprocessor.engine.FileManagment;
import com.fl.massengineprocessor.ws.skeleton.ObjectFactory;
import com.fl.massengineprocessor.ws.skeleton.UpDateTaskItems;

@Component
public class OutputExecutorThread  extends Thread{

	private static Logger logger = Logger.getLogger(OutputExecutorThread.class.getName());

	private volatile Stack<String> queue;

	@Value("${output.thread.sleep.time}")
	public int getOutputThreadSleepTime;
	
	private ObjectFactory factory = new ObjectFactory();
	
	private WebServiceTemplate webServiceTemplate;

	public OutputExecutorThread(){
		super("Output");
		queue = new Stack<String>();
		
		
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
	public void run() {	
		
		initWS();

		while(true){

			FileManagment.loadOutputFile(queue);

			if(queue.isEmpty()){				
				try { Thread.sleep(getOutputThreadSleepTime); }
				catch (InterruptedException e) {}						
			}else{

				while(!queue.isEmpty()){

					String request = removeElement();
					
					String[] fields = request.split("\\,");
					
					UpDateTaskItems uti = factory.createUpDateTaskItems();
					uti.setNd(factory.createUpDateTaskItemsNd(fields[0]));
					uti.setStatus(factory.createUpDateTaskItemsStatus("OK"));
					uti.setGmdRequestId(factory.createUpDateTaskItemsGmdRequestId(new BigInteger("-1")));

					logger.debug("Response to APP : "+request);
					webServiceTemplate.marshalSendAndReceive(uti);
					
					FileManagment.deleteFile(request);

				}
			}
		}


	}

	synchronized private String removeElement(){
		return (String)queue.pop();
	}

	synchronized public void pushElement(String request){
		queue.push(request);
	}

}
