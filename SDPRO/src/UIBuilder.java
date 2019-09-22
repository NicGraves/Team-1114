import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class UIBuilder 
{

	private static String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
	static StringBuilder currentProject = new StringBuilder(" "); //Saves the path of the current open project
	JMenuBar menuBar = new JMenuBar(); //Create a JMenuBar
	static JPanel projectProperties = new JPanel(new BorderLayout());
	JPanel textEditor = new JPanel(new BorderLayout()); //create a new JPanel with a border layout
	static JTextPane ta = new JTextPane(); //Create a new JTextArea
	static JTree tree = new JTree(); //Create a new JTree
	static JScrollPane spectralFilesScrollPane = new JScrollPane(tree); //Create a new JScrollPlane and add the tree
	private projects p = new projects();
	private files f = new files();
	messageDisplay msgD = new messageDisplay();
	
	public JMenuBar buildMenu()
	{
		JMenu menuItem1 = new JMenu("Project"); //Create a menu item named "Project"
	    JMenu menuItem2 = new JMenu("File"); //Create a menu item named "File"
	    JMenu menuItem3 = new JMenu("Help"); //Create a menu item named "Help"
	    
	    menuBar.add(menuItem1); //Add each menu item to the menu bar in order
	    menuBar.add(menuItem2);
	    menuBar.add(menuItem3);
	    
	    JMenuItem projectOpen = new JMenuItem("Open Project"); //Under the project menu item create another menu item called "Open Project"
	    projectOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
					p.openProject(saveDirectory, currentProject);
				} 
                catch (WorkspaceFolderException e1) 
                {
                	msgD.displayMessage("Cannot select the workspace folder. \nPlease select a project folder.");
				}
                projectPropertiesDisplay();
            }
        });
	    JMenuItem projectClose = new JMenuItem("Close Project"); //Under the project menu item create another menu item called "Close Project"
	    projectClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p.closeProject();
            }
        });
	    JMenuItem projectCreateNew = new JMenuItem("Create New Project"); //Under the project menu item create another menu item called "Create New Project"
	    projectCreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
					p.createNewProject(saveDirectory, currentProject);
				} 
            	catch (IOException e1) 
            	{
            		msgD.displayMessage("Error.");
				} 
            	catch (NoProjectNameException e1) 
            	{
            		msgD.displayMessage("Please enter a project name.");
				} 
            	catch (WorkspaceFolderException e1) 
            	{
            		msgD.displayMessage("Cannot select the workspace folder. \nPlease select a project folder.");
				}
            }
        });
	    
	    menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
	    menuItem1.add(projectClose);
	    menuItem1.add(projectCreateNew);
	    
	    JMenuItem fileOpen = new JMenuItem("Open"); //Under the file menu item create another menu item called "Open"
	    fileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	try 
            	{
					displayText(f.openFile(currentProject));
				} 
            	catch (IOException e1) 
            	{
            		msgD.displayMessage("Error");
				}
            	catch(NoFileNameException e1)
            	{
            		msgD.displayMessage("Please enter file name.");
            	}
            	catch(ProjectNotOpenException e1)
            	{
            		msgD.displayMessage("Please open a project to create a new file");
            	}
            	catch(FileDoesNotExistException e1)
            	{
            		msgD.displayMessage("A file with that name does not exist in this project.");
            	}
            }
        });
	    JMenuItem fileSave = new JMenuItem("Save");
	    fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
					f.saveFile(ta, currentProject);
				} 
            	catch (IOException e1) 
            	{
            		msgD.displayMessage("Error.");
				} 
            	catch (NoFileToSaveException e1) 
            	{
            		msgD.displayMessage("No file to save.");
				} 
            	catch (ProjectNotOpenException e1) 
            	{
            		msgD.displayMessage("Open a project to save the file to.");
				}
            }
        });
	    JMenuItem fileClose = new JMenuItem("Close");
	    fileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	displayText(f.closeFile());
            }
        });
	    JMenuItem fileCreateNew = new JMenuItem("Create New"); 
	    fileCreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
					f.createNewFile(currentProject);
					projectPropertiesDisplay();
				} 
                catch (ProjectNotOpenException e1) 
                {
                	msgD.displayMessage("Please open a project to create a new file.");
				} 
                catch (IOException e1) 
                {
                	msgD.displayMessage("Error.");
				} 
                catch (NoFileNameException e1) 
                {
                	msgD.displayMessage("Please enter a file name.");
				} 
                catch (FileExistsException e1) 
                {
                	msgD.displayMessage("A file with that name already exists in this project.");
				}
            }
        });
	    JMenuItem fileRemove = new JMenuItem("Remove");
	    fileRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
					displayText(f.removeFile(currentProject));
					projectPropertiesDisplay();
				} 
            	catch (NoFileNameException e1) 
            	{
            		msgD.displayMessage("Please enter a file name.");
				} 
            	catch (ProjectNotOpenException e1) 
            	{
            		msgD.displayMessage("Please open a project to delete a file.");
				}
            }
        });
	    
	    menuItem2.add(fileOpen); //Add the new menu items to the "File" menu item
	    menuItem2.add(fileSave);
	    menuItem2.add(fileClose);
	    menuItem2.add(fileCreateNew);
	    menuItem2.add(fileRemove);
	   
	    return menuBar;
	}
	
	public JPanel buildProjectProperties()
	{
		projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
    	projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) ); //Add a border around the window
    	
    	return projectProperties;
	}
	
	public JPanel buildTextEditor()
	{
		StyledDocument doc;
		String redKeywords = "";
		String blueKeywords = "";
		FileReader in;
		
		try
		{
			in = new FileReader("SDPRO\\src\\Keywords.txt");
	      	int character;
	      	while ((character = in.read()) != 10 && character != -1)
	      	{
	      		blueKeywords += (char)character;
	      	}
	      	while ((character = in.read()) != 10 && character != -1)
	      	{
	      		redKeywords += (char)character;
	      	}
	      	in.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		doc = new StyledDocument(blueKeywords, redKeywords);
		ta = new JTextPane(doc);
		
		textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
		
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    
	    textEditor.add(scroll, BorderLayout.CENTER);
	    
		return textEditor;
	}
	
	public JPanel buildConsole()
	{
		JPanel output = new JPanel(new BorderLayout());
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
        
		return output;
	}

	public static void projectPropertiesDisplay()
	{
		projectPropertiesClear();
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
//        JLabel status = new JLabel(currentProject.toString());
//        tree.addTreeSelectionListener(new TreeSelectionListener() {
//            public void valueChanged(TreeSelectionEvent e) {
//                folderNameGetter selectedNode = (folderNameGetter) tree.getLastSelectedPathComponent();
//                status.setText(selectedNode.getAbsolutePath());
//                if (selectedNode.isFile()) {
//                    try {
//                        BufferedReader br = new BufferedReader(new FileReader(selectedNode.getAbsolutePath()));
//                        String line = "";
//                        String l = "";
//                        while ((line = br.readLine()) != null) {
//                        	l += line + "\n" ;
//                        }
//                        t.displayText(l);
//                        br.close();
//                    } catch (Exception exc) {
//                        exc.printStackTrace();
//                    }
//                }
//            }
//        });
//*********************************************************************************************************************************************************************************************************************************
        spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
        projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
        redoProjectProperties();
	}

	public static void projectPropertiesClear()
	{
		tree.setModel(null);
		redoProjectProperties();
	}
	
	public static void redoProjectProperties()
	{
		projectProperties.revalidate();
        projectProperties.repaint();
	}
	
	public static void displayText(String line)
	{
		if(line != null)
		{
			ta.setText(line);
		}
	}
	
	static void setCurrentProject(StringBuilder currentProject)
	{
		UIBuilder.currentProject = currentProject;
	}
	
	static StringBuilder getCurrentProject()
	{
		return currentProject;
	}
	
}
