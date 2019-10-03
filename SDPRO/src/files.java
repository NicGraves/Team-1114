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
	
	public String openFile(StringBuilder currentProject) throws IOException, NoFileNameException, ProjectNotOpenException, FileDoesNotExistException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+fileName+".java")) && currentProject.length() != 0)
		{
		   FileReader reader = new FileReader(currentProject+"\\"+fileName+".java");
           BufferedReader br = new BufferedReader(reader);
           String line = "";
           String l = "";
           while ((line = br.readLine()) != null) {
        		l += line + "\n" ;
           }
           br.close();
		   text = currentProject+"\\"+fileName+".java";
		   return l;
		}
		else if(fileName.length() == 0 && currentProject.length() != 0)
		{
			throw new NoFileNameException("Please enter a file name.");
		}
		else if(currentProject == null)
		{
			throw new ProjectNotOpenException("Please open a project to create a new file."); 
		}
		else 
		{
			throw new FileDoesNotExistException("A file with that name does not exist in this project.");
		}
	}
	
	public void saveFile(JTextPane ta, StringBuilder currentProject) throws IOException, NoFileToSaveException, ProjectNotOpenException 
	{
		if(currentProject.length() != 0 && text.length() != 0)
		{
			String[] lines = ta.getText().split("\\n");
			BufferedWriter writer = new BufferedWriter(new FileWriter(text));
			for(int i = 0 ; i< lines.length; i++)
			{
				writer.write(lines[i] + "\n");
			}
		    writer.close();
		    JOptionPane.showMessageDialog(frame, "File saved");
		}
		else if(currentProject.length() != 0 && text.length() == 0)
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
	
	public void createNewFile(StringBuilder currentProject) throws ProjectNotOpenException, IOException, NoFileNameException, FileExistsException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && !Files.exists(Paths.get(currentProject+"\\"+fileName+".java"))) //If there is a current project open
		{
			File f = new File(currentProject+"\\"+fileName+".java");
			f.getParentFile().mkdirs(); 
			f.createNewFile();
			text = currentProject+"\\"+fileName+".java";
		}
		else if(fileName.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+fileName+".java")))
		{
			throw new FileExistsException("A file with that name already exists in this project.");
		}
		else if(fileName.length() == 0)
		{
			throw new NoFileNameException("Please enter a file name.");
		}
		else
		{
			throw new ProjectNotOpenException("Please open a project to create a new file.");
		}
	}
	
	public String removeFile(StringBuilder currentProject) throws NoFileNameException, ProjectNotOpenException
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && Files.exists(Paths.get(currentProject+"\\"+fileName+".java")))
		{
		   File file = new File(currentProject+"\\"+fileName+".java");
		   file.delete();
		   JOptionPane.showMessageDialog(frame, "File removed");
		   if(text.equals(currentProject+"\\"+fileName+".java"))
		   {
			   return "";
		   }
		}
		else if(fileName.length() == 0)
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
