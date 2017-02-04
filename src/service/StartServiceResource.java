package service;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class StartServiceResource extends ServerResource{
	//need to distinguish its negotiation start or re-negotiation start
	String contents;
	long job_id;
	@Post
	  public String postResource(Representation entity) throws IOException
	  {
	    Form input = new Form(entity);
	    
	    job_id = Long.parseLong(input.getFirstValue("job_id"));
	    SteerServiceServer.job_id = job_id;
	    //contents for a contract include: worker, steerer endPoint, contract id
	    contents = input.getFirstValue("info");
	    boolean reneg = contents.contains("reneg");
	    System.out.println("$$$$$$$$$$" + job_id + ": " + contents);
	    //SteerServiceServer.job_data.put(job_id, contents);
	    
	    String[] contracts = contents.split(";");
	    //int con_number = contracts.length;
	    
	    if(!reneg)/*{
	    	//re-negotiation start
	    	//worker is the uri+port needed to pass to Junyi's code to start new resources 
	    	//probably better to store worker locally
	    	
	    	String worker;
	    	String api;
	    	int core;
	    	String[] contents;
	    	int length = contracts.length-2;
	    	for(int i=0; i<=length; i++){
	    		String contract = contracts[i];
	    		System.out.println("$$$$$$$$$$ info received in StartService for re-NEG " + contract);
	    		contents = contract.split(",");
	    		worker = contents[2];
	    		core = Integer.parseInt(contents[3]);
	    		//api should be provided by Junyi
	    		//api = SteerServiceServer.workers.get(worker);
	    		//api = contents[1];
	    		
	    		//to call Junyi's api with api+core
	    	}
	    }
	    
	    else*/{
	    	//negotiation start
	    	String[] contract = contracts[0].split(",");
	    	long contract_id = Long.parseLong(contract[0]);
	    	SteerServiceServer.contract_id = contract_id;
	    	//String endPoint = contract[1];
	    	String endPoint = "http://ec2-52-31-37-191.eu-west-1.compute.amazonaws.com:8080/steering";
	    	//to communicate with steerer with endPoint as steerer's uri
	    	String response = "started";
	    	//String response = SteererConnection.steererStart(contract_id, endPoint);
	    	//System.out.println("$$$$$$$$$$ info received in StartService for NEG" + contract_id + ", " + endPoint);
	    	System.out.println("$$$$$$$$$$ info received in SteererConnection for NEG " + response);
	    }
	    
	    
		return "started";
	  }

}
