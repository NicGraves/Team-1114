import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

public class menu 
{
	private JMenuBar menuBar = new JMenuBar(); //Create a JMenuBar
	private textEditor t = new textEditor();
	private String currentFile = ""; 
	StringBuilder currentProject = new StringBuilder();
	
	public void setFile(String currentFile) 
	{
		this.currentFile = currentFile;
	}
	public String gitFile() {
		return currentFile;
	}
	
	public void buildMenu(String saveDirectory)
	{
		JMenu menuItem1 = new JMenu("Project"); //Create a menu item named "Project"
	    JMenu menuItem2 = new JMenu("File"); //Create a menu item named "File"
	    JMenu menuItem3 = new JMenu("Help"); //Create a menu item named "Help"
	    menuBar.add(menuItem1); //Add each menu item to the menu bar in order
	    menuBar.add(menuItem2);
	    menuBar.add(menuItem3);
	    
	    JMenuItem projectOpen = new JMenuItem("Open Project"); //Under the project menu item create another menu item called "Open Project"
	    projectOpen.addActionListener(new openProjectL(saveDirectory, currentProject));
	    JMenuItem projectClose = new JMenuItem("Close Project"); //Under the project menu item create another menu item called "Close Project"
	    projectClose.addActionListener(new projectCloseL());
	    JMenuItem projectCreateNew = new JMenuItem("Create New Project"); //Under the project menu item create another menu item called "Create New Project"
	    projectCreateNew.addActionListener(new createNewProjectL(saveDirectory, currentProject));
	    menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
	    menuItem1.add(projectClose);
	    menuItem1.add(projectCreateNew);
	    
	    JMenuItem fileOpen = new JMenuItem("Open"); //Under the file menu item create another menu item called "Open"
	    fileOpen.addActionListener(new openFileL(currentProject));
	    JMenuItem fileCreateNew = new JMenuItem("Create New"); //Under the file menu item create another menu item called "Save As"
	    fileCreateNew.addActionListener(new fileCreateNewL(currentProject));
	    JMenuItem fileSave = new JMenuItem("Save");
	    fileSave.addActionListener(new fileSaveL());
	    menuItem2.add(fileOpen); //Add the new menu items to the "File" menu item
	    menuItem2.add(fileSave);
	    menuItem2.add(fileCreateNew);
	}
	
	protected JMenuBar getJMenuBar()
	{
		return menuBar;
	}
}
