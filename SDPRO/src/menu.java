import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class menu 
{
	private JMenuBar menuBar = new JMenuBar(); //Create a JMenuBar
	
	public void buildMenu(String saveDirectory)
	{
		String currentProject = null;
		JMenu menuItem1 = new JMenu("Project"); //Create a menu item named "Project"
	    JMenu menuItem2 = new JMenu("File"); //Create a menu item named "File"
	    JMenu menuItem3 = new JMenu("Help"); //Create a menu item named "Help"
	    menuBar.add(menuItem1); //Add each menu item to the menu bar in order
	    menuBar.add(menuItem2);
	    menuBar.add(menuItem3);
	    
	    JMenuItem projectOpen = new JMenuItem("Open Project"); //Under the project menu item create another menu item called "Open Project"
	    projectOpen.addActionListener(new openProjectL(saveDirectory, currentProject));
	    JMenuItem projectClose = new JMenuItem("Close Project"); //Under the project menu item create another menu item called "Close Project"
	    projectClose.addActionListener(new projectCloseL(saveDirectory, currentProject));
	    JMenuItem projectCreateNew = new JMenuItem("Create New Project"); //Under the project menu item create another menu item called "Create New Project"
	    projectCreateNew.addActionListener(new createNewProjectL(currentProject, saveDirectory));
	    menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
	    menuItem1.add(projectClose);
	    menuItem1.add(projectCreateNew);
	    
	    JMenuItem fileOpen = new JMenuItem("Open"); //Under the file menu item create another menu item called "Open"
	    JMenuItem fileSaveAs = new JMenuItem("Save"); //Under the file menu item create another menu item called "Save As"
	    //fileSaveAs.addActionListener(new fileSaveAsL(currentProject, saveDirectory, ta));
	    menuItem2.add(fileOpen); //Add the new menu items to the "File" menu item
	    menuItem2.add(fileSaveAs);
	}
	
	protected JMenuBar getJMenuBar()
	{
		return menuBar;
	}
}
