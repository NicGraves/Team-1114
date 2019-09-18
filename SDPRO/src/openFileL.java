import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class openFileL implements ActionListener {
	private StringBuilder currentFile;
	openProjectL op = new openProjectL(null, null);
	projectProperties p = new projectProperties();
	JFrame frame = new JFrame();

	public openFileL(StringBuilder currentFile)
	{
		this.currentFile = currentFile;
	}
	public void actionPerformed(ActionEvent e)
	{
		  //final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
        BufferedWriter writer = null; //Create a BufferedWriter
        JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && Files.exists(Paths.get(currentFile.toString()+"\\"+fileName+".java")))
		{
			String contentBuilder = "";
		    try (BufferedReader br = new BufferedReader(new FileReader(currentFile.toString()+"\\"+fileName+".java")))
		    {
		        String sCurrentLine;
		        while ((sCurrentLine = br.readLine()) != null)
		        {
		            contentBuilder += sCurrentLine + "\n";
		        }
		    }
		    catch (IOException e1)
		    {
		        e1.printStackTrace();
		    }
		    menu set = new menu();
		    set.setFile(fileName);
		    textEditor full = new textEditor(contentBuilder);
		    full.buildTextEditor();
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
}
