import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class fileRemoveL implements ActionListener {
	private StringBuilder currentFile;
	textEditor full = new textEditor();
	static textEditor t = new textEditor();
	openProjectL op = new openProjectL(null, null);
	projectProperties p = new projectProperties(null);
	JFrame frame = new JFrame();

	public fileRemoveL(StringBuilder currentFile)
	{
		this.currentFile = currentFile;
	}
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e)
	{
		  //final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
        JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):"); //Create a dialogue box asking for a file name
		if(fileName.length() != 0 && Files.exists(Paths.get(currentFile.toString()+"\\"+fileName+".java")))
		{
			   try 
			   {
				   File file = new File(currentFile.toString()+"\\"+fileName+".java");
				   file.delete();
				   if(full.text.equals(currentFile.toString()+"\\"+fileName+".java"))
				   {
					   String contentBuilder = "";
					   full.displayText(contentBuilder);
				   }
				   p.displayFiles(currentFile);
				   JOptionPane.showMessageDialog(frame, fileName+".java has been deleted");
			   }
			   catch (Exception exc) 
			   {
				   exc.printStackTrace();
		       }
		}
		else if(fileName.length() == 0)
		{
			JOptionPane.showMessageDialog(frame, "Please enter a file name.");
		}
		else
		{
			JOptionPane.showMessageDialog(frame, "Please open a project to delete a file.");
		}

		
	}

}
