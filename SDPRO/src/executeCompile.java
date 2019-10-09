import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream; 
import java.io.OutputStream;

import com.sun.corba.se.spi.orbutil.fsm.Input;

public class executeCompile 
{
	
	String output = "";
	
	public String compileExecute(StringBuilder currentProject, StringBuilder currentFile) throws IOException, InterruptedException, ProjectNotOpenException, NoFileOpen
	{
		if(currentProject.length() != 0 && currentFile.length() != 0)
		{
	    	runProcess(getFiles(currentProject));
	    	if(output.equals(""))
	    	{
	    		String runFile = currentFile.toString().substring(0, currentFile.toString().indexOf("."));
				runProcess(runFile);
	    	}
		}
		else if(currentProject.length() == 0)
		{
			throw new ProjectNotOpenException("Please open a Project Folder to run.");
		}
		else if(currentFile.toString().equals(""))
		{
			throw new NoFileOpen("Please open a File to run.");
		}
		
		return output;
	}
	
//	private void printLines(InputStream ins) throws IOException
//	{
//		String line = null;
//	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
//	    while ((line = in.readLine()) != null) 
//	    {
//	    	output += line+"\n";
//	    }
//	}

	private void runProcess(String command) throws IOException, InterruptedException
	{
		output = "";
		ProcessBuilder builder = new ProcessBuilder("java", command);
		builder.redirectError();
		builder.directory(new File("src"));
		Process pro = builder.start();
		InputStreamConsumer ins = new InputStreamConsumer(pro.getErrorStream(), pro.getInputStream(), output);
		ins.start();
		pro.waitFor();
		ins.join();
		output = ins.getOutput();

//		output = "";
//		printLines(pro.getErrorStream());
//		if(output.equals(""))
//		{
//			printLines(pro.getInputStream());
//		}
	}
	
	private String getFiles(StringBuilder currentProject)
	{
		File folder = new File(currentProject.toString());
		String[] files = folder.list();
		String fileNames = "";
		
		for(String file : files)
		{
			fileNames += currentProject.toString()+"\\"+file+" ";
		}
		return fileNames;
	}
	
	public class InputStreamConsumer extends Thread 
	{
		private InputStream es;
        private InputStream is;
        private IOException exp;
        private String output;

        public InputStreamConsumer(InputStream es, InputStream is, String output) 
        {
        	this.es = es;
            this.is = is;
            this.output = output;
        }

        @Override
        public void run()
        {
	    		String line = null;
	    	    BufferedReader in = new BufferedReader(new InputStreamReader(es));
	    	    try 
	    	    {
					while ((line = in.readLine()) != null) 
					{
						output += line+"\n";
					}
				} 
	    	    catch (IOException e) 
	    	    {
					e.printStackTrace();
				}
	    	    if(output.equals(""))
	    	    {
	        		line = null;
	        	    BufferedReader in2 = new BufferedReader(new InputStreamReader(is));
	        	    try 
	        	    {
						while ((line = in2.readLine()) != null) 
						{
							output += line+"\n";
						}
					} 
	        	    catch (IOException e) 
	        	    {
						e.printStackTrace();
					}
        	
	    	    }
	    	    
        }
        public String getOutput() 
        {
            return output;
        }

        public IOException getException() 
        {
            return exp;
        }
    }
}
