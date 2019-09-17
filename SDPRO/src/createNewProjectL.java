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
	private String saveDirectory;
	openProjectL op = new openProjectL(null, null);
	StringBuilder currentProject;
	StringBuilder temp;
	menu m = new menu();

	public createNewProjectL(String saveDirectory, StringBuilder currentProject)
	{
		this.saveDirectory = saveDirectory;
		this.currentProject = currentProject;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		projectProperties p = new projectProperties();
		try 
		{
				JFrame frame = new JFrame();
				temp = currentProject;
				currentProject.setLength(0);
				currentProject.append(JOptionPane.showInputDialog(frame, "Enter Project name:"));
				Path path = Paths.get(saveDirectory+"\\"+currentProject);
				if(!Files.exists(path))
				{
					Files.createDirectory(path);
					String exp = path.toString();
					op.openNew(exp, p.getProjectProperties());
				}
				else if(currentProject.length() == 0)
				{
					JOptionPane.showMessageDialog(frame, "Please enter a project name.");
					currentProject = temp;
					temp.setLength(0);
				}
				else if(Files.exists(path))
				{
					JOptionPane.showMessageDialog(frame, "A project with that name already exists. \nPlease select a new name and try again");
					currentProject = temp;
					temp.setLength(0);
				}
		} 
		catch (IOException s) 
		{
			s.printStackTrace();
		}
	}	
}