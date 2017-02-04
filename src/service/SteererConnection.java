package service;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class SteererConnection {
	//start job execution in steerer after negotiation
	public static String steererStart(long contract_id, String uri) {
		String start_uri = uri + "/start";
		String response = null;
		ClientResource resource = new ClientResource(start_uri);

	    Form form = new Form();
	    //form.add("stop", "1");
	    form.add("contract_id", Long.toString(contract_id));
	    //form.add("job_id", job_id);
	    //form.add("appname","ResvApp");
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

}
