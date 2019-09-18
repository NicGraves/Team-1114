import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class projectProperties 
{
	private static JPanel projectProperties = new JPanel(new BorderLayout());
	static textEditor t = new textEditor();
	static JTree tree = new JTree(); //Create a new JTree
	static JScrollPane spectralFilesScrollPane = new JScrollPane(tree); //Create a new JScrollPlane and add the tree
	StringBuilder currentProject;
	
	public projectProperties (StringBuilder currentProject)
	{
		this.currentProject = currentProject;
	}
	
	public void buildProjectProperties()
	{
		projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
    	projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) ); //Add a border around the window
	}
	
	protected JPanel getProjectProperties()
	{
		return projectProperties;
	}
	
	public void clearFiles()
	{
		tree.setModel(null);
		projectProperties.revalidate();
        projectProperties.repaint();
	}
	
	public void displayFiles(StringBuilder currentProject)
	{
		FileTree model = new FileTree(currentProject.toString()); //Create a new FileTree using the Selected File
        tree.setModel(model); //Set the model as the FileTree class model
        /***************************************************************************************
        *    Title: FileSelectorModel  
        *    Author:  Sébastien Le Callonnec
        *    Date: September 18, 2019
        *    Code version: version 1.0
        *    Availability: https://www.weblogism.com/item/300/use-jtree-to-display-files-in-filesystem-ii
        *    Modified slightly by the team to work with our project
        *
        ***************************************************************************************/
        JLabel status = new JLabel(currentProject.toString());
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                folderNameGetter selectedNode = (folderNameGetter) tree.getLastSelectedPathComponent();
                status.setText(selectedNode.getAbsolutePath());
                if (selectedNode.isFile()) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(selectedNode.getAbsolutePath()));
                        String line = "";
                        String l = "";
                        while ((line = br.readLine()) != null) {
                        	l = line + "\\n" ;
                        }
                        t.displayText(l);
                        br.close();
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
//*********************************************************************************************************************************************************************************************************************************
        spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
        projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
        projectProperties.revalidate();
        projectProperties.repaint();
	}
}
