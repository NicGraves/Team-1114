import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class files 
{
	JFrame frame = new JFrame();
	String text = "";
	
	public String openFile(StringBuilder currentProject, StringBuilder currentFile) throws IOException, NoFileNameException, ProjectNotOpenException, FileDoesNotExistException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		currentFile.setLength(0);
		String temp = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):");
		if(temp != null)
		{
			currentFile.append(temp);
		}
		if(currentFile.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+currentFile+".java")) && currentProject.length() != 0)
		{
		   FileReader reader = new FileReader(currentProject+"\\"+currentFile+".java");
           BufferedReader br = new BufferedReader(reader);
           String line = "";
           String l = "";
           while ((line = br.readLine()) != null) {
        		l += line + "\n" ;
           }
           br.close();
		   text = currentProject+"\\"+currentFile+".java";
		   return l;
		}
		else if(currentFile.length() == 0 && currentProject.length() != 0)
		{
			throw new NoFileNameException("Please enter a file name.");
		}
		else if(currentProject.toString().equals(""))
		{
			throw new ProjectNotOpenException("Please open a Project Folder."); 
		}
		else 
		{
			throw new FileDoesNotExistException("A file with that name does not exist in this project.");
		}
	}
	
	public void saveFile(JTextPane ta, StringBuilder currentProject, StringBuilder currentFile) throws IOException, NoFileToSaveException, ProjectNotOpenException 
	{
		if(currentProject.length() != 0 && currentFile.length() != 0)
		{
			//text = currentProject+"\\"+currentFile+".java";
			if(!currentFile.toString().endsWith(".java"))
			{
				currentFile.append(".java");
			}
			String[] lines = ta.getText().split("\\n");
			BufferedWriter writer = new BufferedWriter(new FileWriter(currentProject+"\\"+currentFile));
			for(int i = 0 ; i< lines.length; i++)
			{
				writer.write(lines[i] + "\n");
			}
		    writer.close();
		    JOptionPane.showMessageDialog(frame, "File saved");
		}
		else if(currentProject.length() != 0 && currentFile.length() == 0)
		{
			throw new NoFileToSaveException("No file to save.");
		}
		else
		{
			throw new ProjectNotOpenException("Open a project to save the file to.");
		}	
	}
	
	public String closeFile()
	{
		return("");
	}
	
	public void createNewFile(StringBuilder currentProject, StringBuilder currentFile) throws ProjectNotOpenException, IOException, NoFileNameException, FileExistsException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		currentFile.setLength(0);
		String temp = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):");
		if(temp != null)
		{
			currentFile.append(temp);
		}
		if(currentFile.length() != 0 && !Files.exists(Paths.get(currentProject+"\\"+currentFile+".java"))) //If there is a current project open
		{
			File f = new File(currentProject+"\\"+currentFile+".java");
			f.getParentFile().mkdirs(); 
			f.createNewFile();
			text = currentProject+"\\"+currentFile+".java";
		}
		else if(currentFile.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+currentFile+".java")))
		{
			throw new FileExistsException("A file with that name already exists in this project.");
		}
		else if(currentFile.length() == 0)
		{
			throw new NoFileNameException("Please enter a file name.");
		}
		else
		{
			throw new ProjectNotOpenException("Please open a project to create a new file.");
		}
	}
	
	public String removeFile(StringBuilder currentProject, StringBuilder currentFile) throws NoFileNameException, ProjectNotOpenException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		currentFile.setLength(0);
		String temp = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):");
		if(temp != null)
		{
			currentFile.append(temp);
		}
		if(currentFile.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+currentFile+".java")))
		{
		   File file = new File(currentProject+"\\"+currentFile+".java");
		   file.delete();
		   JOptionPane.showMessageDialog(frame, "File removed");
		   if(text.equals(currentProject+"\\"+currentFile+".java"))
		   {
			   return "";
		   }
		}
		else if(currentFile.length() == 0)
		{
			throw new NoFileNameException("Please enter a file name.");
		}
		else
		{
			throw new ProjectNotOpenException("Please open a project to delete a file.");
		}
		
		return null;
	}
}
