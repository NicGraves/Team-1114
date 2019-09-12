import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class createNewProjectL implements ActionListener 
{
	private String projectName;
	private String saveDirectory;
	private boolean save = true;
	protected JPanel projectProperties;
	openProjectL op = new openProjectL(projectName, null);

	public createNewProjectL(String projectName, String saveDirectory, JPanel projectProperties)
	{
		this.projectName = projectName;
		this.saveDirectory = saveDirectory;
		this.projectProperties = projectProperties;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
			while(save)
			{
				JFrame frame = new JFrame();
				projectName = JOptionPane.showInputDialog(frame, "Enter Project name:");
				Path path = Paths.get(saveDirectory+"\\"+projectName);
				if(!Files.exists(path))
				{
					Files.createDirectory(path);
					String exp = path.toString();
					op.openNew(exp, projectProperties);
					save = false;
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "A project with that name already exists. \nPlease select a new name and try again");
				}
			}
		} 
		catch (IOException s) 
		{
			s.printStackTrace();
		}
	}	
}