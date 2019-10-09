import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class executeCompile 
{
	
	String output = "";
	
	public String compileExecute(StringBuilder currentProject, StringBuilder currentFile) throws IOException, InterruptedException, ProjectNotOpenException, NoFileOpen
	{
		if(currentProject.length() != 0 && currentFile.length() != 0)
		{
	    	runProcess("javac -d Class "+getFiles(currentProject));
	    	if(output.equals(""))
	    	{
	    		String runFile = currentFile.toString().substring(0, currentFile.toString().indexOf("."));
				runProcess("java -cp Class "+runFile);
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
	
	private void printLines(InputStream ins) throws IOException
	{
		String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) 
	    {
	    	output += line+"\n";
	    }
	}

	private void runProcess(String command) throws IOException, InterruptedException
	{
		Process pro = Runtime.getRuntime().exec(command);
		output = "";
		printLines(pro.getErrorStream());
		if(output.equals(""))
		{
			printLines(pro.getInputStream());
		}
		pro.waitFor();
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
}
