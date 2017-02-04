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
		if(app.equalsIgnoreCase("CompSteering")){
			String job_type = form.getFirstValue("jobtype");
			String numjobs = form.getFirstValue("numjobs");
			String nefolds = form.getFirstValue("nefold");
			String deadline = form.getFirstValue("deadline");
			offers = BrokerConnection.sendNegQuoteRequest(job_type, numjobs, nefolds, deadline);
		}
		else{
			offers = BrokerConnection.sendNegQuoteRequest("","","","");
		}
		System.out.println("&&&&&&&&&& neg received quotes: " + offers);
		//to select an offer ramdonly
		String[] offers_array = offers.split(";");
		int lenght = offers_array.length;
		int max = lenght - 1;
		System.out.println("array length: " + lenght);
		int min = 0;
	    Random rand = new Random();
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    
	    long offer_id = Long.parseLong(offers_array[randomNum].split("=")[0]);
	    //String offer_info = offers_array[randomNum].split("-")[1];
	    //SteerServiceServer.contract_worker.put(offer_id, offer_info);
	    //to send selected offer to broker
	    String offer_response = BrokerConnection.sendOffer(offer_id);
	    System.out.println("&&&&&&&&&& neg received offer contents " + offer_response);
	    //send AcceptAck to broker. after broker receives AcceptAck
	    if (!offer_response.isEmpty()){
	    	//String response = BrokerConnection.sendNegAcceptAck(offer_id);
	    	returned = BrokerConnection.sendNegAcceptAck(offer_id);
	    	//String response = BrokerConnection.sendNegQuery(offer_id);
	    	System.out.println("&&&&&&&&&& neg received contents after sending AcceptAck" + returned);
	    	//returned = response.split("!")[1];
	    	SteerServiceServer.job_id = Long.parseLong(returned.split("!")[0]);
	    	System.out.println("&&&&&&&&&& neg received contents after sending AcceptAck job id " + SteerServiceServer.job_id);
	    	System.out.println("&&&&&&&&&& neg received contents after sending AcceptAck " + returned);
	    }
	    System.out.println("&&&&&&&&&& neg returned response" + returned);
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
