import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class fileSaveAsL implements ActionListener 
{
	private JTextArea ta;
	private String currentProject;

	public fileSaveAsL(String currentProject, String saveDirectory, JTextArea ta)
	{
		this.currentProject = currentProject;
		this.ta = ta;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
           final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
           BufferedWriter writer = null; //Create a BufferedWriter
			try 
			{
				if(currentProject != null && !currentProject.isEmpty()) //If there is a current project open
				{
					JFrame fileNameGetter = new JFrame(); //Create a new JFrame
					String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name:"); //Create a dialogue box asking for a file name
					Path path = Paths.get(currentProject); //set the path to the IDE workspace in the correct file project
					if(!Files.exists(path)) //If the specified path does not exist
					{
						Files.createDirectory(path); //Create that file path
					}
					writer = new BufferedWriter(new FileWriter(currentProject+"\\"+fileName+".txt")); //Select the specified folder and add the text file
					writer.write(codeSave); //Write what was on the Text Editor window to the text file
					writer.close(); //Close the file writer
				}
				else //If no project is open then print an error statement 
				{
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Please open a project to save a file");
				}
			} 
			catch (IOException a) 
			{
				a.printStackTrace();
			}
	}	
}
