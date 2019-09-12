import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class openProjectL implements ActionListener {
	
	static JTree tree = new JTree(); //Create a new JTree
    static JScrollPane spectralFilesScrollPane = new JScrollPane(tree); //Create a new JScrollPlane and add the tree
	private String saveDirectory;
	protected JPanel projectProperties;
	static String temp;

	public openProjectL(String saveDirectory, JPanel projectProperties)
	{
		this.saveDirectory = saveDirectory;
		this.projectProperties = projectProperties;
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
		temp = jfc.getSelectedFile().getAbsolutePath().toString();
		if(temp.endsWith(saveDirectory))
		{
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Cannot select the workspace folder. \nPlease select a project folder");
		}
		else if (returnValue == JFileChooser.APPROVE_OPTION) //If the selected Folder is okay
		{
			String exportPath = jfc.getSelectedFile().getAbsolutePath(); //Get the project Folder path and store it as a string
            FileTree model = new FileTree(exportPath); //Create a new FileTree using the Selected File
            tree.setModel(model); //Set the model as the FileTree class model   
            spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
            projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
            projectProperties.revalidate();
            projectProperties.repaint();
		}
	}
	
	public void openNew(String path, JPanel projectProperties)
	{
		FileTree model = new FileTree(path); //Create a new FileTree using the Selected File
        tree.setModel(model); //Set the model as the FileTree class model   
        spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
        projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
        projectProperties.revalidate();
        projectProperties.repaint();
	}
}