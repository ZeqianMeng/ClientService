package service;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class SteerServiceApplication extends Application{
	
	
	  public synchronized Restlet createInboundRoot()
	  {
	    Router router = new Router(getContext());


	    router.attach("/start", StartServiceResource.class);
	    router.attach("/complete", CompleteServiceResource.class);
	    router.attach("/reneg", ReNegotiationResource.class);
	    router.attach("/neg", NegotiationResource.class);
	    //router.attach("/reneg", BrokerNegResource.class);	    
	    //router.attach("/steer", SteerResource.class);
	    //router.attach("/androidCon", ClientConnection.class);

	    return router;
	  }
	}
