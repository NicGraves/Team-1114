import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class fileCreateNewL implements ActionListener 
{
	private JTextPane ta;
	StringBuilder currentProject;
	projectProperties p = new projectProperties(currentProject);
	JFrame frame = new JFrame();

	public fileCreateNewL(JTextPane ta, StringBuilder currentProject)
	{
		this.currentProject = currentProject;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
        //final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
        try
        {
        	JFrame fileNameGetter = new JFrame(); //Create a new JFrame
			String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
			if(fileName.length() != 0 && !Files.exists(Paths.get(currentProject.toString()+"\\"+fileName+".java"))) //If there is a current project open
			{
				File f = new File(currentProject+"\\"+fileName+".java");
				f.getParentFile().mkdirs(); 
				f.createNewFile();
				p.displayFiles(currentProject);
			}
			else if(fileName.length() != 0 && Files.exists(Paths.get(currentProject.toString()+"\\"+fileName+".java")))
			{
				JOptionPane.showMessageDialog(frame, "A file with that name already exists in this project");
			}
			else if(fileName.length() == 0)
			{
				JOptionPane.showMessageDialog(frame, "Please enter a file name");
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Please open a project to create a new file");
			}
        }
        catch(IOException e1)
        {
        	e1.printStackTrace();
        }
//		if(currentProject.length() != 0)
//		{
//			JFrame fileNameGetter = new JFrame(); //Create a new JFrame
//			String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.txt is not needed):"); //Create a dialogue box asking for a file name
//			if(fileName.length() != 0 && !Files.exists(Paths.get(currentProject.toString()+"\\"+fileName+".txt"))) //If there is a current project open
//			{
////					writer = new BufferedWriter(new FileWriter(currentProject+"\\"+fileName+".txt")); //Select the specified folder and add the text file
////					writer.write(codeSave); //Write what was on the Text Editor window to the text file
////					writer.close(); //Close the file writer
//				try 
//				{
//					save = new FileWriter(currentProject+"\\"+fileName+".txt");
//				} 
//				catch (IOException e1) 
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				op.openNew(currentProject.toString(), p.getProjectProperties());
//			}
//			else if(fileName.length() != 0 && Files.exists(Paths.get(currentProject.toString()+"\\"+fileName+".txt")))
//			{
//				JOptionPane.showMessageDialog(frame, "A file with that name already exists in this project");
//			}
//			else if(fileName.length() == 0)
//			{
//				JOptionPane.showMessageDialog(frame, "Please enter a file name");
//			}
//		}
//		else
//		{
//			JOptionPane.showMessageDialog(frame, "Please open a project to create a new file");
//		}
	}
}	

