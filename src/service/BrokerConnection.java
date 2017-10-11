package service;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class BrokerConnection {

	//final static String uri = "http://ec2-34-251-126-84.eu-west-1.compute.amazonaws.com:8080/appinst";
	final static String uri = "http://localhost:8080/appinst";

	
	//send selected offer to broker, returned would be Accept (contract contents)
	public static String sendOffer(long offer_id) {
	    
	    //final String uri = "http://52.31.37.191:8080/appinst";
	    //System.out.println("******** inside connectAHE3 ahe uri: " + uri);
	    System.out.println("******** inside connectAHE3 offer id received: " + offer_id);
	    
	    String response = null;
	    ClientResource resource = new ClientResource(uri);

	    Form form = new Form();
	    //form.add("stop", "1");
	    form.add("offer_id", Long.toString(offer_id));
	    //form.add("job_id", job_id);
	    //form.add("appname","ResvApp");
		//form.add("group","ManGroup");
	    try
	    {
	      //resource.post(form).write(System.out);
	    	//response includes contract contents
	    	response = resource.post(form).getText();
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	//send AcceptAck to broker, broker would send job_id:contents to StartServiceResource in SteerService
	public static String sendRenegAcceptAck(long job_id, long contract_id) {

	    System.out.println("******** inside sendRenegAcceptAck offer id received: " + contract_id);
	    System.out.println("******** inside sendRenegAcceptAck job id received: " + job_id);
	    
	    String response = null;
	    ClientResource resource = new ClientResource(uri);
  
	    //to get job_id from SteerServiceServer
	    /*Collection<String> contract_contents = SteerServiceServer.job_data.values();
	    Set<Long> job_ids = SteerServiceServer.job_data.keySet();
		long job_id = 0;
		int position = 0;
		for(String contract:contract_contents){
			if(contract.contains(Long.toString(contract_id))){
				System.out.println(contract);
				job_id = (long) job_ids.toArray()[position];
			}
			position++;
		}*/
	    Form form = new Form();
	    //form.add("stop", "1");
	    form.add("contract_id", Long.toString(contract_id));
	    form.add("job_id", Long.toString(job_id));
	    //form.add("appname","WaterSteering");
		//form.add("username","Sofia");
	    try
	    {
	      //resource.post(form).write(System.out);
	    	response = resource.post(form).getText();
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	public static String sendNegAcceptAck(long contract_id) throws IOException {

	    System.out.println("Client Service sends the AcceptAck for contract: " + contract_id + " to Service Broker");
	    
	    String response = "";
	    ClientResource resource = new ClientResource(uri);
  
	    //to get job_id from SteerServiceServer
	    /*Collection<String> contract_contents = SteerServiceServer.job_data.values();
	    Set<Long> job_ids = SteerServiceServer.job_data.keySet();
		long job_id = 0;
		int position = 0;
		for(String contract:contract_contents){
			if(contract.contains(Long.toString(contract_id))){
				System.out.println(contract);
				job_id = (long) job_ids.toArray()[position];
			}
			position++;
		}*/
	    Form form = new Form();
	    //form.add("stop", "1");
	    form.add("contract_id", Long.toString(contract_id));
	    try
	    {
	      //resource.post(form).write(System.out);
	    	response = resource.post(form).getText();
	    	//resource.post(form);
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	//to send re-neg request to broker, will get offers as returned
	public static String sendRenegQuoteRequest(long contract_id, int core) {

	    System.out.println("******** inside connectAHE3 contract id received: " + contract_id);
	    
	    String response = null;
	    ClientResource resource = new ClientResource(uri);

	    Form form = new Form();
	    form.add("contract_id", Long.toString(contract_id));
	    form.add("start_time", "asap");
        form.add("core",Integer.toString(core));
	    //form.add("stop", "1");
	    /*if(res.equalsIgnoreCase("")){
	        
	        //form.add("job_id", Long.toString(job_id));
	        form.add("start_time", "asap");
	        form.add("core",Integer.toString(core));
	    }
	    else{
	    	form.add("resource", res);    	
	    }*/
		//form.add("group","ManGroup");
	    try
	    {
	      //resource.post(form).write(System.out);
	    	response = resource.post(form).getText();
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	public static String sendNegQuoteRequest(String user, String group, String app, String core, String job, String numjobs, String nefold, String deadline) {

	    System.out.println("Client Service sends the received QuoteRequest to Service Broker.");
	    
	    String response = null;
	    ClientResource resource = new ClientResource(uri);

	    Form form = new Form();
	    if(job.equalsIgnoreCase("")){
	        form.add("username", user);
		    form.add("group", group);
		    form.add("appname",app);
		    form.add("start_time","asap");
		    form.add("core", core);
	    }
	    else{
	    	form.add("username", user);
		    form.add("group", group);
		    form.add("appname", app);
		    form.add("jobtype", job);
		    form.add("numjobs", numjobs);
		    form.add("nefold", nefold);
		    form.add("deadline", deadline);
	    }
	    try
	    {
	      //resource.post(form).write(System.out);
	    	response = resource.post(form).getText();
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	public static String sendNegQuery(long contract_id) {

	    System.out.println("******** inside connectAHE3 to start negotiation");
	    
	    String response = null;
	    ClientResource resource = new ClientResource(uri);

	    Form form = new Form();
	    form.add("query_id", Long.toString(contract_id));
		//form.add("query", "");
	    try
	    {
	      //resource.post(form).write(System.out);
	    	response = resource.post(form).getText();
	    }
	    catch (ResourceException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return response;
	  }
	
	/*public static void main(String[] args) {
		System.out.println(sendNegQuery(6225));
	}*/

}
