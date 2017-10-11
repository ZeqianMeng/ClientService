package service;

import java.io.IOException;
import java.util.Random;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class ReNegotiationResource extends ServerResource{
	//static String offers;
	//this deals with to increasing core request from Junyi's code
	@Post
	  public String postResource(Representation entity) throws IOException
	  {
		String response = "";
		String returned = "";
	    Form input = new Form(entity);
	    //String app = input.getFirstValue("appname");
	    long job_id = Long.parseLong(input.getFirstValue("job_id"));
	    //long job_id = SteerServiceServer.job_id;
	   // long job_id = Long.parseLong(input.getFirstValue("job_id"));
	    //long job_id = 4749;
	    //long contract_id = 9591;
	    String offers;
	    long contract_id = Long.parseLong(input.getFirstValue("contract_id"));
	    int core = Integer.parseInt(input.getFirstValue("core"));
		offers = BrokerConnection.sendRenegQuoteRequest(contract_id, core);
	    
		/*if(app.equalsIgnoreCase("CompSteering")){
			String resource = input.getFirstValue("resource");
			//offers = BrokerConnection.sendRenegQuoteRequest(contract_id, 0, resource);
			//or if to take it as negotiation, rather than re-negotiation, to call the same api for negotiation
			offers = BrokerConnection.sendNegQuoteRequest(resource);
		}
		else{
			int core = Integer.parseInt(input.getFirstValue("core"));
			offers = BrokerConnection.sendRenegQuoteRequest(contract_id, core);
			//offers = BrokerConnection.sendRenegQuoteRequest(contract_id, core, "");
		}*/
	    
	    
	    //System.out.println("$$$$$$$$$$ re-neg request with job id: " + job_id);
	    //to fetch contract_id from SteerServiceServer
	    //long contract_id = Long.parseLong(SteerServiceServer.job_data.get(job_id).split(",")[0]);
	    //long contract_id = SteerServiceServer.contract_id;
	    System.out.println("$$$$$$$$$$ re-neg request with contract id: " + contract_id);
	    //to send quoteRequest to broker
	    //offers are returned offers
	    System.out.println("$$$$$$$$$$ re-neg received quotes: " + offers);
	    //should continue re-neg here
	    String[] offers_array = offers.split("\\^");
		int lenght = offers_array.length;
		int max = lenght - 1;
		System.out.println("array length: " + lenght);
		int min = 0;
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    System.out.println("$$$$$$$$$$ re-neg seleted offer index " + randomNum);
	    System.out.println("$$$$$$$$$$ re-neg seleted offer contents " + offers_array[randomNum]);
	    long offer_id = Long.parseLong(offers_array[randomNum].split("=")[0]);
	    //contract_id = offer_id;
	    //send selected offer with offer_id to broker, response from broker should be contract contents
	    String offer_response = BrokerConnection.sendOffer(offer_id);
	    System.out.println("$$$$$$$$$$ re-neg received offer contents " + offer_response);
	    //send AcceptAck to broker. after broker receives AcceptAck, the rest
          //would follow the same as negotiation: broker calls SteerService's start uri
	    if (!offer_response.isEmpty()){
	    	returned = BrokerConnection.sendRenegAcceptAck(job_id, offer_id);
	    	//returned = BrokerConnection.sendRenegAcceptAck(job_id, offer_id);
	    	//returned = response.split("!")[1];
	    	System.out.println("$$$$$$$$$$ re-neg received contents after sending AcceptAck " + response);
	    }
	    System.out.println("$$$$$$$$$$ re-neg received response" + response);
	    return returned;
	    
	  }

}
