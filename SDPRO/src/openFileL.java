import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class openFileL implements ActionListener {
	private StringBuilder currentFile;
	openProjectL op = new openProjectL(null, null);
	static textEditor t = new textEditor();
	projectProperties p = new projectProperties(currentFile);
	JFrame frame = new JFrame();

	public openFileL(StringBuilder currentFile)
	{
		this.currentFile = currentFile;
	}
	public void actionPerformed(ActionEvent e)
	{
		  JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && Files.exists(Paths.get(currentFile.toString()+"\\"+fileName+".java")))
		{
				   try {
					   FileReader reader = new FileReader(currentFile.toString()+"\\"+fileName+".java");
		               BufferedReader br = new BufferedReader(reader);
		               String line = "";
		               String l = "";
		               while ((line = br.readLine()) != null) {
		            		l += line + "\n" ;
                       }
                       t.displayText(l);
		               br.close();
		           } catch (Exception exc) {
		               exc.printStackTrace();
		           }
		    textEditor full = new textEditor(currentFile.toString()+"\\"+fileName+".java");
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
