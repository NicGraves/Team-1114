import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class openProjectL implements ActionListener {
	
	private String saveDirectory;
	private JPanel projectProperties;

	public openProjectL(String saveDirectory, JPanel projectProperties)
	{
		this.saveDirectory = saveDirectory;
		this.projectProperties = projectProperties;
	}

	public void actionPerformed(ActionEvent e)
	{
		JFileChooser jfc = new JFileChooser(saveDirectory+"\\"); //Create a JFileChooser opened to the IDE workspace
	    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //User is only able to select folders
		int returnValue = jfc.showOpenDialog(null);
		jfc.setAcceptAllFileFilterUsed(false);
		if (returnValue == JFileChooser.APPROVE_OPTION) //If the selected Folder is okay
		{
			String exportPath = jfc.getSelectedFile().getAbsolutePath(); //Get the project Folder path and store it as a string
            FileTree model = new FileTree(exportPath); //Create a new FileTree using the Selected File
            JTree tree = new JTree(); //Create a new JTree
            tree.setModel(model); //Set the model as the FileTree class model   
            JScrollPane spectralFilesScrollPane = new JScrollPane(tree); //Create a new JScrollPlane and add the tree
            //  add(BorderLayout.CENTER, spectralFilesScrollPane);
            spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
            projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
            projectProperties.revalidate();
            projectProperties.repaint();
		}
	}
}