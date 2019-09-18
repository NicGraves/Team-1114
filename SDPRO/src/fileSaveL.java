import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

public class fileSaveL implements ActionListener
{
	private StringBuilder currentProject;
	private String currentFile;

	public fileSaveL(StringBuilder currentProject, String currentFile) 
	{
		this.currentProject = currentProject;
		this.currentFile = currentFile;
	}

	public void actionPerformed(ActionEvent e) 
	{
		
		
    }
		
	
	
}
