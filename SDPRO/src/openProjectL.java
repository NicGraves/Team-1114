import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class openProjectL implements ActionListener 
{	
	static JTree tree = new JTree(); //Create a new JTree
    static JScrollPane spectralFilesScrollPane = new JScrollPane(tree); //Create a new JScrollPlane and add the tree
    static String temp;
	private String saveDirectory;
	StringBuilder currentProject;
	menu m = new menu();
	JTextPane ta; //Create a new JTextArea
	projectProperties p = new projectProperties(currentProject);

	public openProjectL(String saveDirectory, StringBuilder currentProject)
	{
		this.saveDirectory = saveDirectory;
		this.currentProject = currentProject;
	}

	public void actionPerformed(ActionEvent e)
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
			temp = jfc.getSelectedFile().getAbsolutePath().toString();
			if(temp.endsWith(saveDirectory))
			{
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, "Cannot select the workspace folder. \nPlease select a project folder");
			}
			else
			{
				currentProject.setLength(0);
				currentProject.append(jfc.getSelectedFile().getAbsolutePath()); //Get the project Folder path and store it as a string
				p.displayFiles(currentProject);
			}
		}
	}
}