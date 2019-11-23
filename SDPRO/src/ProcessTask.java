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
    public Integer call() throws Exception {
	    //InputStream input = new JTextFieldInputStream(UIBuilder.textField);
	    JTextFieldInputStream ts = new JTextFieldInputStream(UIBuilder.textField);
	    UIBuilder.textField.addActionListener(ts);
	    //System.setIn(ts);
    	ProcessBuilder pb = new ProcessBuilder(cmds);
		pb.redirectErrorStream(true);
	    Process p = pb.start();
	    InputStream out = p.getInputStream();
	    OutputStream in = p.getOutputStream();

	    byte[] buffer = new byte[4000];
	    
	    while (isAlive(p)) 
	    {
	    	int no = out.available();
	    	if (no > 0) 
	    	{
		    	//UIBuilder.console.update(UIBuilder.console.getGraphics());
	    		int n = out.read(buffer, 0, Math.min(no, buffer.length));
	    		String line = new String(buffer, 0, n);
	    		UIBuilder.console.append(line);
	    	}
	    	int ni = System.in.available();
	    	if (ni > 0) 
	    	{
	    		int n = System.in.read(buffer, 0, Math.min(ni, buffer.length));
	    		in.write(buffer, 0, n);
	    		in.flush();
	    	}
	    }
	    System.out.println("OUT");
	    
	    return null;
    }
    
	  public static boolean isAlive(Process p) {
		    try {
		      p.exitValue();
		      return false;
		    }
		    catch (IllegalThreadStateException e) {
		      return true;
		    }
		  }

}