package service;

import java.util.ArrayList;
import java.util.Hashtable;

import org.restlet.Component;
import org.restlet.data.Protocol;


public class SteerServiceServer {
	
	 //public static Hashtable<Long, String> job_data = new Hashtable();
	 //public static Hashtable<Long, String> contract_worker = new Hashtable();
	 public static long job_id;
	 public static long contract_id;


	  public static void main(String[] args) throws Exception
	  {
		//to store workers and their apis here
		  //node_uri.put("node1", "uri1");
		  //node_uri.put("node2", "uri2");
		  //node_uri.put("node3", "uri3");
		  
		//workers.put("amazon.t2.micro", "http://ec2-52-31-37-191.eu-west-1.compute.amazonaws.com:8080/steering");
		//workers.put("amazon.t2.small", "steerer2");
		  SteerServiceServer rest = new SteerServiceServer();
		  rest.createServer(8070);
		  
	    /*Component component = new Component();

	    component.getServers().add(Protocol.HTTP, 8070);
	    component.getClients().add(Protocol.HTTP);

	    component.getDefaultHost().attach("/steerservice", new SteerServiceApplication());
	    
	    //to store workers and their apis here

	    component.start();*/
	  }
	  
	  public void createServer(int port) throws Exception{
		  Component component = new Component();

		    component.getServers().add(Protocol.HTTP, port);
		    component.getClients().add(Protocol.HTTP);

		    component.getDefaultHost().attach("/steerservice", new SteerServiceApplication());
		    
		    //to store workers and their apis here

		    component.start();
		  
	  }

}
