package service;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class NegotiationResource extends ServerResource{
	@Post
	  public String postResource(Representation entity) throws IOException
	  {
		Form form = new Form(entity);
		String app = form.getFirstValue("appname");
		//String response = "";
		String returned = "";
		String offers;
		String username = form.getFirstValue("username");
		String group = form.getFirstValue("group");
		String core = form.getFirstValue("core");

		if(app.equalsIgnoreCase("CompSteering")){
			String job_type = form.getFirstValue("jobtype");
			String numjobs = form.getFirstValue("numjobs");
			String nefolds = form.getFirstValue("nefold");
			String deadline = form.getFirstValue("deadline");
			offers = BrokerConnection.sendNegQuoteRequest(username, group, app, "0", job_type, numjobs, nefolds, deadline);
			System.out.println("Client Sercice received a negotiation request with user name: " + username + "; group name: " + group
                		+ "; application name: " + app + "; job type: " + job_type + "; and deadline: " + deadline);
		}
		else{
			//String core = form.getFirstValue("core");
			offers = BrokerConnection.sendNegQuoteRequest(username, group, app, core, "","","","");
		}
		//System.out.println("&&&&&&&&&& neg received quotes: " + offers);
		//to select an offer ramdonly
		String[] offers_array = offers.split("\\^");
		int length = offers_array.length;
		if (offers_array[0].contains("empty offer")){
			return "empty offer.";
		}
		else{
		int max = length - 1;
		System.out.println("array length: " + length);
		int min = 0;
	    Random rand = new Random();
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    
	    // for Cluster case
	    //String[] offer_info = offers_array[randomNum].split("=");
	    //long offer_id = Long.parseLong(offers_array[randomNum].split("=")[0]);
	    
	    //for Junyi'scase
	    
	    //long offer_id = Long.parseLong(offer_info[0]);
	    long offer_id = Long.parseLong(offers_array[randomNum].split("=")[0]);
	    String[] offer_info = offers_array[randomNum].split(";");
	    int offer_nos = offer_info.length;
	    String endpoint = null;
	    if(offer_nos > 1){
	    	for(int i=0; i<offer_nos; i++){
	    		endpoint = offer_info[1] + ";" + endpoint;
	    	}
	    }
	    else{
	    	endpoint = offer_info[0];
	    }
	    System.out.println("Client Service selected the Offer with offer ID: " + offer_id);
	    System.out.println("The endpoint contained in the offer : " + offer_id + " is " + endpoint);
	    //String offer_info = offers_array[randomNum].split("-")[1];
	    //SteerServiceServer.contract_worker.put(offer_id, offer_info);
	    //to send selected offer to broker
	    String offer_response = BrokerConnection.sendOffer(offer_id);
	    System.out.println("Client Service received Accept for offer: " + offer_id + ", with contents: " + offer_response);
	    //send AcceptAck to broker. after broker receives AcceptAck
	    if (!offer_response.isEmpty()){
	    	//String response = BrokerConnection.sendNegAcceptAck(offer_id);
	    	returned = BrokerConnection.sendNegAcceptAck(offer_id);
	    	System.out.println("Client Service sent AcceptAck for offer: " + offer_id);
	    	//String response = BrokerConnection.sendNegQuery(offer_id);
	    	System.out.println("Client Service received contents after sending AcceptAck" + returned);
	    	//returned = response.split("!")[1];
	    	//SteerServiceServer.job_id = Long.parseLong(returned.split("!")[0]);
	    	//System.out.println("Client Service received a job ID from Service Broker: " + SteerServiceServer.job_id);
	    	//System.out.println("Client Service sent a job request to the steering programs with the contract ID and job ID");
	    	//System.out.println("&&&&&&&&&& neg received contents after sending AcceptAck " + returned);
	    }
	    //System.out.println("&&&&&&&&&& neg returned response" + returned);
	    //if(app.equalsIgnoreCase("CompSteering")){
	    	// to call the functions to start job execution in cluster
	    	// to start timer to fetch job complete information
	    	/*TimerTask timerTask = new FetchInfoTimer();
	    	Timer this_timer = new Timer();
	    	this_timer.schedule(timerTask, 0);
	    	try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
	    //}
		return returned;
	  }
	  }

}
