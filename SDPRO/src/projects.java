import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class projects
{
	
	public void openProject(String saveDirectory, StringBuilder currentProject) throws WorkspaceFolderException
	{

		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		File root = new File(saveDirectory+"\\");
		FileSystemView fsv = new SingleRootFileSystemView(root);
		JFileChooser jfc = new JFileChooser(fsv); //Create a JFileChooser opened to the IDE workspace
	    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //User is only able to select folders
		int returnValue = jfc.showOpenDialog(null);
		jfc.setAcceptAllFileFilterUsed(false);
		if (returnValue == JFileChooser.APPROVE_OPTION) //If the selected Folder is okay
		{
			String temp = jfc.getSelectedFile().getAbsolutePath().toString();
			if(temp.endsWith(saveDirectory))
			{
				throw new WorkspaceFolderException("Cannot select the workspace folder. \nPlease select a project folder");
			}
			else
			{
				currentProject.setLength(0);
				currentProject.append(temp); //Get the project Folder path and store it as a string
			}
		}
	}
	
	public void closeProject(StringBuilder currentProject)
	{
		currentProject.setLength(0);
	}
	
	public void createNewProject(String saveDirectory, StringBuilder currentProject) throws IOException, NoProjectNameException, WorkspaceFolderException
	{
		JFrame frame = new JFrame();
		String temp = JOptionPane.showInputDialog(frame, "Enter Project name:");
		Path path = Paths.get(saveDirectory+"\\"+temp);
		if(!Files.exists(path) && temp.length() != 0)
		{
			currentProject.setLength(0);
			currentProject.append(temp);
			Files.createDirectory(path);
			UIBuilder.projectPropertiesDisplay();
		}
		else if(temp.length() == 0)
		{
			throw new NoProjectNameException("Please enter a project name.");
		}
		else if(Files.exists(path))
		{
			throw new WorkspaceFolderException("Cannot select the workspace folder. \nPlease select a project folder");
		}
	}
}
