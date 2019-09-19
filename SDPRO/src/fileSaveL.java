import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class fileSaveL implements ActionListener
{
	textEditor full = new textEditor();
	JFrame frame = new JFrame();
	StringBuilder currentProject;
	
	public fileSaveL(StringBuilder currentProject)
	{
		this.currentProject = currentProject;
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) 
	{
		if(currentProject.length() != 0 && full.text.length() != 0)
		{
			String[] lines = full.ta.getText().split("\\n");
			try 
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter(full.text));
				for(int i = 0 ; i< lines.length; i++)
				{
					writer.write(lines[i] + "\n");
				}
			    writer.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(frame, "File saved");
		}
		else if(currentProject.length() != 0 && full.text.length() == 0)
		{
			JOptionPane.showMessageDialog(frame, "No file to save");
		}
		else
		{
			JOptionPane.showMessageDialog(frame, "Open a project");
		}
		
    }
		
	
	
}
