/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.io.*;

/**
 *
 * @author Jared - PC
 */
public class Editor 
{
//        protected static JFrame frame = new JFrame("IDE");
//	protected static JMenuBar menuBar = new JMenuBar(); //Create a JMenuBar
//        
//        static StyledDocument doc;
//	protected static JTextPane ta;//Create a new JTextArea
//        
//	protected static JPanel projectProperties = new JPanel(new BorderLayout());
//	protected static JPanel output = new JPanel(new BorderLayout());
//	protected static String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
//	
//	//This may not be necessary
//	static String projectName = ""; //name of the current open project
//        
//    public Editor()
//    {
//        String redKeywords = "";
//        String blueKeywords = "";
//        FileReader in;
//        try
//        {
//            in = new FileReader("Keywords.txt");
//            int character;
//            while ((character = in.read()) != 10 && character != -1)
//            {
//                blueKeywords += (char)character;
//            }
//            while ((character = in.read()) != 10 && character != -1)
//            {
//                redKeywords += (char)character;
//            }
//            in.close();
//        }
//        catch(IOException e)
//        {
//            System.out.println(e);
//            System.exit(-1);
//        }
//        doc = new StyledDocument(blueKeywords, redKeywords);
//        ta = new JTextPane(doc);
//        
//        
//        
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 500);
//        createMenuBar();
//        createProjectProperties();
//        createTextEditor();
//        createConsole();
//        frame.setLocationRelativeTo ( null );
//        frame.setVisible(true);
//    }
//
//	
//	//This function instantiates a menu bar and adds the necessary components to each selection
//	public static void createMenuBar()
//	{
//        JMenu menuItem1 = new JMenu("Project"); //Create a menu item named "Project"
//        JMenu menuItem2 = new JMenu("File"); //Create a menu item named "File"
//        JMenu menuItem3 = new JMenu("Help"); //Create a menu item named "Help"
//        menuBar.add(menuItem1); //Add each menu item to the menu bar in order
//        menuBar.add(menuItem2);
//        menuBar.add(menuItem3);
//        
//        JMenuItem projectOpen = new JMenuItem("Open Project"); //Under the project menu item create another menu item called "Open Project"
//        projectOpen.addActionListener(new openProjectL(saveDirectory, projectProperties));
//        JMenuItem projectClose = new JMenuItem("Close Project"); //Under the project menu item create another menu item called "Close Project"
//        projectClose.addActionListener(new projectCloseL(saveDirectory, projectProperties));
//        JMenuItem projectCreateNew = new JMenuItem("Create New Project"); //Under the project menu item create another menu item called "Create New Project"
//        projectCreateNew.addActionListener(new createNewProjectL(projectName, saveDirectory, projectProperties));
//        menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
//        menuItem1.add(projectClose);
//        menuItem1.add(projectCreateNew);
//        
//        JMenuItem fileOpen = new JMenuItem("Open"); //Under the file menu item create another menu item called "Open"
//        JMenuItem fileSaveAs = new JMenuItem("Save as"); //Under the file menu item create another menu item called "Save As"
//        fileSaveAs.addActionListener(new fileCreateNew(projectName, saveDirectory, ta));
//        menuItem2.add(fileOpen); //Add the new menu items to the "File" menu item
//        menuItem2.add(fileSaveAs);
//        
//        frame.getContentPane().add(BorderLayout.PAGE_START, menuBar);
//	}
//	
//	//This function creates the Project Properties Window
//	public static void createProjectProperties()
//	{
//    	projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
//    	projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) ); //Add a border around the window
//    	frame.getContentPane().add(BorderLayout.LINE_START,projectProperties); //Add the JPanel to the frame
//	}
//	
//	//This function creates the Text Editor Window
//	public static void createTextEditor()
//	{
//        ta.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
//        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextPane
//	scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
//        
//        frame.getContentPane().add(BorderLayout.CENTER, scroll);
//	}
//	
//	//This function creates the Console Window
//	public static void createConsole()
//	{
//		String hold = "";
//        output.setBorder ( new TitledBorder ( new EtchedBorder (), "Console" ) ); //Create the border
//        JTextArea console = new JTextArea(); //Create a new Text Area
//        output.setPreferredSize(new Dimension(50,150)); //Set the size of the JPanel
//        console.insert(hold,0);
//        console.setLineWrap(true);
//        console.setWrapStyleWord(true);
//        console.setEditable(false); //Does not allow the user to edit the output
//        JScrollPane Cscroll = new JScrollPane (console); //Create a JScrollPane object
//        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bars to appear when necessary
//        output.add(Cscroll); //Add the scroll to the JPanel
//        frame.getContentPane().add(BorderLayout.PAGE_END, output); //Add the JPanel to the JFrame
//	}
}