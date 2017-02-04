package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.TimerTask;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;
import com.jcabi.ssh.Shell.Plain;

public class FetchInfoTimer extends TimerTask{
	final long timekeeper = 90 * 1000;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		completeTask();
		try {
			SshConnect();
			processData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//to fetch info in desktop using ssh
		
			/*Plain ssh1;
			try {
				ssh1 = new Shell.Plain(
						new SSHByPassword(
								"rs0.cs.man.ac.uk", 22, "mengz", "mM_20110919")
						);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		String comp_time = "";
		try {
			//to rm the complete file in desktop and update every entry inside
			comp_time = ssh1.exec("java -jar /home/MSC11/mengz/Desktop/tests/ClusterClient.jar 66666");
			if(comp_time.isEmpty()){
				//to start another timer
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(comp_time);
		
		String exit1 = null;
		try {
			exit1 = ssh1.exec("exit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(stdout);
		//System.out.println(exit2);
		System.out.println(exit1);*/
		
	}
	
	public void SshConnect() throws IOException{
		/*String comp_time="";
		Plain ssh1 = new Shell.Plain(
				new SSHByPassword(
						"rs0.cs.man.ac.uk", 22, "mengz", "mM_20110919")
				);
		comp_time = ssh1.exec("java -jar /home/MSC11/mengz/Desktop/tests/ClusterClient.jar 66666");
		System.out.println(comp_time);
		String exit1 = ssh1.exec("exit");
		//System.out.println(stdout);
		//System.out.println(exit2);
		System.out.println(exit1);
		return comp_time;*/
		//to execute scripts to scp file in desktop
		String command = "/opt/test/fetchFile.sh";
		Runtime rt = Runtime.getRuntime();
	    rt.exec(command);
	    System.exit(0);
		
	}
	
	public void processData(){
		//to process the file copied from desktop
        final String file_path = "/opt/test/CompleteJobs.txt";
        final String broker_api = "http://ec2-52-210-129-87.eu-west-1.compute.amazonaws.com :8080/appinst";
		
        File file = new File(file_path);
        BufferedReader br = null;

		try {
			
			if(file.exists() && !file.isDirectory()) { 
				
				String comp_time = "";
				String sCurrentLine;
			    // do something
				ClientResource resource = new ClientResource(broker_api);
			    Form form = new Form();
			
			    br = new BufferedReader(new FileReader(file));
			    long temp_id;

			    while ((sCurrentLine = br.readLine()) != null) {
				    System.out.println(sCurrentLine);
				    temp_id = Long.parseLong(sCurrentLine.split(":")[0]);
				    comp_time = sCurrentLine.split(":")[1];
				    //connect broker to update balance for corresponding contracts
				    
				    form.add("job_id", String.valueOf(temp_id));
				    form.add("complete", comp_time);
				    try {
			            resource.post(form).write(System.out);
					    //resource.get().write(System.out);
		 
				    } catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				    form.clear();
				//output = output + sCurrentLine;
			}
			    String command = "rm /opt/test/CompleteJobs.txt";
				Runtime rt = Runtime.getRuntime();
			    rt.exec(command);
			    System.exit(0);
			    

		} }catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void completeTask() {
        try {
            //assuming it takes 20 mins to complete the task
            Thread.sleep(timekeeper);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
