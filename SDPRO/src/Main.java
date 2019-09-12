import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class Main 
{
	protected static JFrame frame = new JFrame("IDE");
	protected static JMenuBar menuBar = new JMenuBar(); //Create a JMenuBar
	protected static JPanel textEditor = new JPanel(new BorderLayout()); //create a new JPanel with a border layout
	protected static JTextArea ta = new JTextArea(); //Create a new JTextArea
	protected static JPanel projectProperties = new JPanel(new BorderLayout());
	protected static JPanel output = new JPanel(new BorderLayout());
	protected static String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
	
	//This may not be necessary
	static String projectName = ""; //name of the current open project
	
	//This function instantiates a menu bar and adds the necessary components to each selection
	public static void createMenuBar()
	{
        JMenu menuItem1 = new JMenu("Project"); //Create a menu item named "Project"
        JMenu menuItem2 = new JMenu("File"); //Create a menu item named "File"
        JMenu menuItem3 = new JMenu("Help"); //Create a menu item named "Help"
        menuBar.add(menuItem1); //Add each menu item to the menu bar in order
        menuBar.add(menuItem2);
        menuBar.add(menuItem3);
        JMenuItem projectOpen = new JMenuItem("Open"); //Under the project menu item create another menu item called "Open"
        projectOpen.addActionListener(new openProjectL(saveDirectory, projectProperties));
        JMenuItem projectCreateNew = new JMenuItem("Create New"); //Under the project menu item create another menu item called "Create New"
        projectCreateNew.addActionListener(new createNewProjectL(projectName, saveDirectory));
        menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
        menuItem1.add(projectCreateNew);
        JMenuItem fileOpen = new JMenuItem("Open"); //Under the file menu item create another menu item called "Open"
        JMenuItem fileSaveAs = new JMenuItem("Save as"); //Under the file menu item create another menu item called "Save As"
        fileSaveAs.addActionListener(new fileSaveAsL(projectName, saveDirectory, ta));
        menuItem2.add(fileOpen); //Add the new menu items to the "File" menu item
        menuItem2.add(fileSaveAs);
        frame.getContentPane().add(BorderLayout.PAGE_START, menuBar);
	}
	
	//This function creates the Project Properties Window
	public static void createProjectProperties()
	{
    	projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
    	projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) ); //Add a border around the window
    	frame.getContentPane().add(BorderLayout.LINE_START,projectProperties); //Add the JPanel to the frame
	}
	
	//This function creates the Text Editor Window
	public static void createTextEditor()
	{
        textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    textEditor.add(scroll, BorderLayout.CENTER);
        frame.getContentPane().add(BorderLayout.CENTER, textEditor);
	}
	
	//This function creates the Console Window
	public static void createConsole()
	{
		String hold = "";
        output.setBorder ( new TitledBorder ( new EtchedBorder (), "Console" ) ); //Create the border
        JTextArea console = new JTextArea(); //Create a new Text Area
        output.setPreferredSize(new Dimension(50,150)); //Set the size of the JPanel
        console.insert(hold,0);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false); //Does not allow the user to edit the output
        JScrollPane Cscroll = new JScrollPane (console); //Create a JScrollPane object
        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bars to appear when necessary
        output.add(Cscroll); //Add the scroll to the JPanel
        frame.getContentPane().add(BorderLayout.PAGE_END, output); //Add the JPanel to the JFrame
	}
	
	public static void main(String[] args) 
	{
		 //Creating the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        createMenuBar();
        createProjectProperties();
        createTextEditor();
        createConsole();
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	}

}
