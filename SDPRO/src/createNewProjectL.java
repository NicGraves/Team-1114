import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class createNewProjectL implements ActionListener 
{
	private String currentProject;
	private String saveDirectory;
	openProjectL op = new openProjectL(currentProject, null);

	public createNewProjectL(String currentProject, String saveDirectory)
	{
		this.currentProject = currentProject;
		this.saveDirectory = saveDirectory;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		projectProperties p = new projectProperties();
		try 
		{
				JFrame frame = new JFrame();
				currentProject = JOptionPane.showInputDialog(frame, "Enter Project name:");
				Path path = Paths.get(saveDirectory+"\\"+currentProject);
				if(!Files.exists(path))
				{
					Files.createDirectory(path);
					String exp = path.toString();
					op.openNew(exp, p.getProjectProperties());
				}
				else if(currentProject.equals("") || currentProject != null && !currentProject.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Please enter a project name.");
				}
				else if(Files.exists(path))
				{
					JOptionPane.showMessageDialog(frame, "A project with that name already exists. \nPlease select a new name and try again");
				}
		} 
		catch (IOException s) 
		{
			s.printStackTrace();
		}
	}	
}