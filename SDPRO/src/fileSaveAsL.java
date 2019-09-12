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
	
	private String projectName;
	private String saveDirectory;
	private JTextArea ta;

	public fileSaveAsL(String projectName, String saveDirectory, JTextArea ta)
	{
		this.projectName = projectName;
		this.saveDirectory = saveDirectory;
		this.ta = ta;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
           final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
           BufferedWriter writer = null; //Create a BufferedWriter
			try 
			{
				JFrame fileNameGetter = new JFrame(); //Create a new JFrame
				String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name:"); //Create a dialogue box asking for a file name
				Path path = Paths.get(saveDirectory+"\\"+projectName); //set the path to the IDE workspace in the correct file project
				if(!Files.exists(path)) //If the specified path does not exist
				{
					Files.createDirectory(path); //Create that file path
				}
				writer = new BufferedWriter(new FileWriter(saveDirectory+"\\"+projectName+"\\"+fileName+".txt")); //Select the specified folder and add the text file
				writer.write(codeSave); //Write what was on the Text Editor window to the text file
				writer.close(); //Close the file writer
			} 
			catch (IOException a) 
			{
				a.printStackTrace();
			}
	}	
}
