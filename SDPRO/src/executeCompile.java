import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class executeCompile 
{	
	/*
	 * Function that gets all the files from the project folder and stores them in an array list for compiling 
	 */
	private ArrayList<String> javacCommandBuilder(StringBuilder currentProject, StringBuilder currentFile)
	{
		File folder = new File(currentProject.toString());
		String[] files = folder.list();
		ArrayList<String> command = new ArrayList<String>(Arrays.asList("javac","-d","Class"));
		
		for(String file : files)
		{
			command.add(currentProject.toString()+"\\"+file);
		}
		return command;
	}
	
	/*
	 * Function that gets the current open file and runs the file
	 */
	private ArrayList<String> javaCommadBuilder(StringBuilder currentFile)
	{
		ArrayList<String> command = new ArrayList<String>(Arrays.asList("java", "-cp","Class"));
		command.add(currentFile.toString().substring(0, currentFile.toString().indexOf(".")));
		return command;
	}
	
	/*
	 * Function that compiles and runs the project
	 */
	public String execute(StringBuilder currentProject, StringBuilder currentFile) throws IOException
	{
		ProcessBuilder processBuilder = new ProcessBuilder(javacCommandBuilder(currentProject, currentFile));
		Process process = processBuilder.start();
		
		if( process.getErrorStream().read() != -1 )
		{
			return(print("Compilation Errors",process.getErrorStream()));
		}
		if( process.exitValue() == 0 )
		{
			process = new ProcessBuilder(javaCommadBuilder(currentFile)).start();
			if( process.getErrorStream().read() != -1 )
			{
				return(print("Errors ",process.getErrorStream()));
			}
			else
			{
				return(print("Output ",process.getInputStream()));
			}
		}
		return "";
	}
	
	/*
	 * Function that prints the necessary error or output statements
	 */
	private static String print(String status, InputStream input) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		String text = "";
		String line = null;
		while((line = in.readLine()) != null )
		{
			text += line + "\n";
		}
		in.close();
		return text;
	}
}
