import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ProcessTask implements Callable<Integer> {

    private ArrayList<String> cmds;

    public ProcessTask(ArrayList<String> arrayList) {
        this.cmds = arrayList;
    }

    @Override
    public Integer call() throws Exception 
    {
//	    JTextFieldInputStream ts = new JTextFieldInputStream(UIBuilder.textField);
//	    UIBuilder.textField.addKeyListener(ts);
//	    System.setIn(ts);
    	//Process builder with new command
    	ProcessBuilder pb = new ProcessBuilder(cmds);
		//Start the process
	    Process p = pb.start();
	    //Set the input and output stream
	    InputStream out = p.getInputStream();
	    OutputStream in = p.getOutputStream();

	    //Set a buffer
	    byte[] buffer = new byte[4000];
	    
	    //While the process is still alive
	    while (isAlive(p)) 
	    {
	    	//Get the lines it needs to print out
	    	int no = out.available();
	    	//If there are lines
	    	if (no > 0) 
	    	{
	    		//Append them to the console
	    		int n = out.read(buffer, 0, Math.min(no, buffer.length));
	    		String line = new String(buffer, 0, n);
	    		UIBuilder.console.append(line);
	    	}
	    	
	    	//Read in input
	    	int ni = System.in.available();
	    	//If there is input
	    	if (ni > 0) 
	    	{
	    		//Write in the input
	    		int n = System.in.read(buffer, 0, Math.min(ni, buffer.length));
	    		in.write(buffer, 0, n);
	    		in.flush();
	    	}
	    }
	    p.destroy();
	    return null;
    }

	/*
     * Function that checks to make sure the process is still running
     */
	  public static boolean isAlive(Process p) {
		    try 
		    {
		      p.exitValue();
		      return false;
		    }
		    catch (IllegalThreadStateException e) 
		    {
		      return true;
		    }
		  }

}