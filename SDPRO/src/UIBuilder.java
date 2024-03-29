import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class UIBuilder
{
	//static BlockingQueue blockingQueue = new LinkedBlockingDeque();
    private StyledDocument doc;
	private String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
	StringBuilder currentProject = new StringBuilder(""); //Saves the path of the current open project
	StringBuilder currentFile = new StringBuilder(""); //Saves the name of the current open file
	
	//Instantiates all the necessary windows on the text editor
    static JTextArea console = new JTextArea();
    static JTextField textField;
	JMenuBar menuBar = new JMenuBar(); 
	JPanel textEditor = new JPanel(new BorderLayout());
	JTextArea keywords = new JTextArea();
	JPanel projectProperties = new JPanel(new BorderLayout());
	JTextPane ta = new JTextPane();
	static JTree tree = new JTree();
	static JScrollPane spectralFilesScrollPane = new JScrollPane(tree);
	
	//Instantiates all the necessary objects
	private projects p = new projects();
	private files f = new files();
	//private messageDisplay msgD = new messageDisplay();
	
	//Instantiates the border titles of the project properties window and the text editor window to their default titles
	TitledBorder projectTitle = BorderFactory.createTitledBorder( new EtchedBorder (), "Project Properties");
	static TitledBorder fileTitle = BorderFactory.createTitledBorder( new EtchedBorder (), "Editor");
	
	/*
	 * This function creates the JMenu bar that can be found along the top of the text editor.
	 * It creates each JMenu and populates them with the necessary JMenuItems which each have a 
	 * action listener to call the correct function once they are pressed.
	 */
	public JMenuBar buildMenu()
	{
		JMenu menuItem1 = new JMenu("Project"); //Create a menu named "Project"
	    JMenu menuItem2 = new JMenu("File"); //Create a menu named "File"
	    //JMenu menuItem3 = new JMenu("Help"); //Create a menu named "Help"
	    JMenu menuItem4 = new JMenu("Execute"); //Create a menu named "Execute"
	    
	    //Add each menu to the menu bar
	    menuBar.add(menuItem1); 
	    menuBar.add(menuItem2);
	   // menuBar.add(menuItem3);
	    menuBar.add(menuItem4);
	    
	    //Create a new menu item called "Open Project"
	    JMenuItem projectOpen = new JMenuItem("Open Project"); 
	    //Create an action listener for that menu item
	    projectOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
            		displayText(f.closeFile()); //Clear the current text open in the editor
            		currentFile.setLength(0); //Clear the name of the currently opened project
	            	resetFileTitle(); //Reset the text editor window title to the default 
					p.openProject(saveDirectory, currentProject); //Calls the openProject function
	                projectPropertiesDisplay(); //Reset the project properties window to display the open projects files
					resetProjectTitle(); //Reset the projectPoperties title to the name of the open project
				} 
                catch (WorkspaceFolderException e1) 
                {}
            }
        });
	    
	    //Create a new menu item called "Close Project"
	    JMenuItem projectClose = new JMenuItem("Close Project");
	    //Create an action listener for that menu item
	    projectClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
        		displayText(f.closeFile()); //Clear the current text open in the editor
        		currentFile.setLength(0); //Clear the name of the currently opened project
            	resetFileTitle(); //Reset the text editor window title to the default
                p.closeProject(currentProject);
				resetProjectTitle();
                projectPropertiesDisplay(); //Reset the project properties window to display the open projects files 
            }
        });
	    
	    //Create a new menu item called "Create New Project"
	    JMenuItem projectCreateNew = new JMenuItem("Create New Project"); 
	    //Create an action listener for that menu item
	    projectCreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
            		displayText(f.closeFile()); //Clear the current text open in the editor
            		currentFile.setLength(0); //Clear the name of the currently opened project
	            	resetFileTitle(); //Reset the text editor window title to the default
	                p.closeProject(currentProject);
					p.createNewProject(saveDirectory, currentProject); //Call the create new project function 
					resetProjectTitle(); //Reset the project properties title to the newly created project name
	                projectPropertiesDisplay(); //Reset the project properties window to display the open projects files
				} 
            	catch (IOException e1) 
            	{} 
            	catch (NoProjectNameException e1) 
            	{} 
            	catch (WorkspaceFolderException e1) 
            	{}
            }
        });
	    
	    //Add the project menu items
	    menuItem1.add(projectOpen); //Add the new menu items to the "Project" menu item
	    menuItem1.add(projectClose);
	    menuItem1.add(projectCreateNew);
	    
	    //Create a new menu item called "Save"
	    JMenuItem fileSave = new JMenuItem("Save");
	    //Create an action listener for that menu item
	    fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
					f.saveFile(ta, currentProject, currentFile); //Call the saveFile function
				} 
            	catch (IOException e1) 
            	{} 
            	catch (NoFileToSaveException e1) 
            	{} 
            	catch (ProjectNotOpenException e1) 
            	{}
            }
        });

	    //Create a new menu item called "Close"
	    JMenuItem fileClose = new JMenuItem("Close");
	    //Create an action listener for that menu item
	    fileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
        		displayText(f.closeFile()); //Clear the current text open in the editor
        		currentFile.setLength(0); //Clear the name of the currently opened project
            	resetFileTitle(); //Reset the text editor window title to the default 
            }
        });
	    
	    //Create a new menu item called "Create New"
	    JMenuItem fileCreateNew = new JMenuItem("Create New"); 
	    //Create an action listener for that menu item
	    fileCreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
            		currentFile.setLength(0); //Clear the name of the currently opened project
					f.createNewFile(currentProject, currentFile); //Call the create a new file function
					projectPropertiesDisplay(); //Reset the project properties display
					resetFileTitle(); //Reset the text editor window title to the newly created file
				} 
                catch (ProjectNotOpenException e1) 
                {} 
                catch (IOException e1) 
                {} 
                catch (NoFileNameException e1) 
                {} 
                catch (FileExistsException e1) 
                {}
            }
        });
	    
	    //Create a new menu item called "Remove"
	    JMenuItem fileRemove = new JMenuItem("Remove");
	    //Create an action listener for that menu item
	    fileRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	try 
            	{
					displayText(f.removeFile(currentProject, currentFile)); //Clear the text on the text editor window
					projectPropertiesDisplay(); //Reset the project properties display window
            		currentFile.setLength(0); //Clear the name of the currently opened project
					resetFileTitle(); //Reset the text editor window title
				} 
            	catch (NoFileNameException e1) 
            	{} 
            	catch (ProjectNotOpenException e1) 
            	{}
            }
        });
	    
	    //Add the File menu items
	    menuItem2.add(fileSave);
	    menuItem2.add(fileClose);
	    menuItem2.add(fileCreateNew);
	    menuItem2.add(fileRemove);
	    
	    //This is the action listener for the "Execute" menu button
	    menuItem4.addMouseListener(new MouseListener() 
	    {
	    	@Override
	        public void mouseReleased(MouseEvent e) {}

	        @Override
	        public void mousePressed(MouseEvent e) 
	        {
	            try
	    		{
	            	f.saveFile(ta, currentProject, currentFile); //Save the current open file
	            	cosoleText();
	    		}
	    		catch(ProjectNotOpenException e1)
	    		{}
            	catch (NoFileToSaveException e1) 
            	{} 
	            catch (IOException e1) 
	            {} 
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {}
	        @Override
	        public void mouseEntered(MouseEvent e) {}
	        @Override
	        public void mouseClicked(MouseEvent e) {}
	    });
	   
	    return menuBar;
	}
	
	/*
	 * This function creates the project properties window
	*/
	public JPanel buildProjectProperties()
	{
		projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
    	projectProperties.setBorder (projectTitle); //Add a border around the window
    	
    	return projectProperties;
	}
	
	/*
	 * This function builds the Text Editor window. It also reads what the user is typing or what is being read from a save file
	 * in real time to highlight any necessary keywords.
	 */
	public JPanel buildTextEditor()
	{
		String redKeywords = ""; //Variable that will store all the keywords that need to be highlighted in red
		String blueKeywords = ""; //Variable that will store all the keywords that need to be highlighted in blue
		FileReader in; //Instantiate a file reader
		
		//Get all the necessary keywords from the Keywords document
		try
		{
                        //Leave this in for Jared :) 
                        //in = new FileReader("Keywords.txt");
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
		doc = new StyledDocument(blueKeywords, redKeywords, keywords);
		ta = new JTextPane(doc);
                //Set settings for the Keywords panel
                keywords.setPreferredSize(new Dimension(200, 25));
                keywords.setBorder(new EtchedBorder());
                keywords.setEditable(false);
                
		textEditor.setBorder (fileTitle); //create a border around the JPanel with the name "Text Editor"
		
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    
	    textEditor.add(scroll, BorderLayout.CENTER);
	    textEditor.add(keywords, BorderLayout.PAGE_END);
		return textEditor;
	}
	
	/*
	 * This function builds the console for the output
	 */
	public JPanel buildConsole()
	{
		JPanel output = new JPanel(new BorderLayout());
		String hold = "";
		
        output.setBorder (new TitledBorder(new EtchedBorder(),"Console")); //Create the border
        output.setPreferredSize(new Dimension(50,150)); //Set the size of the JPanel
        console.insert(hold,0);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false); //Does not allow the user to edit the output
        JScrollPane Cscroll = new JScrollPane (console); //Create a JScrollPane object
        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bars to appear when necessary
        output.add(Cscroll); //Add the scroll to the JPanel
        textField = new JTextField();
        output.add(BorderLayout.PAGE_END, textField);
        
		return output;
	}
	
	/*
	 * This function builds the project properties display.
	 * Also allows users to double click on a file to open the file in the text editor
	 */
	public void projectPropertiesDisplay()
	{
		tree.setModel(null);
		redoProjectProperties();
		FileTree model = new FileTree(currentProject.toString()); //Create a new FileTree using the Selected File
        tree.setModel(model); //Set the model as the FileTree class model
        /***************************************************************************************
        *    Title: FileSelectorModel  
        *    Author:  S�bastien Le Callonnec
        *    Date: September 18, 2019
        *    Code version: version 1.0
        *    Availability: https://www.weblogism.com/item/300/use-jtree-to-display-files-in-filesystem-ii
        *    Modified slightly by the team to work with our project
        ***************************************************************************************/
        JLabel status = new JLabel(currentProject.toString());
        tree.addTreeSelectionListener(new TreeSelectionListener() 
        {
            public void valueChanged(TreeSelectionEvent e) 
            {
                folderNameGetter selectedNode = (folderNameGetter) tree.getLastSelectedPathComponent();
                if(selectedNode != null)
                {
                	status.setText(selectedNode.getAbsolutePath());
                    if (selectedNode.isFile()) 
                    {
                        try 
                        {
                        	currentFile.setLength(0);
                        	currentFile.append(selectedNode);
                        	resetFileTitle();
                            BufferedReader br = new BufferedReader(new FileReader(selectedNode.getAbsolutePath()));
                            String line = "";
                            String l = "";
                            while ((line = br.readLine()) != null) 
                            {
                            	l += line + "\n" ;
                            }
                            displayText(l);
                            br.close();
                        } 
                        catch (Exception exc) 
                        {
                            exc.printStackTrace();
                        }
                    }
                }
            }
        });
//*********************************************************************************************************************************************************************************************************************************
        spectralFilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Show scroll bars when necessary
        projectProperties.add(spectralFilesScrollPane); //Add to projectProperties JPanel
        redoProjectProperties();
	}
	
	/*
	 * This function refreshes the project properties window when... A new project is opened or a new file is created or removed
	 */
	public void redoProjectProperties()
	{
		projectProperties.revalidate();
        projectProperties.repaint();
	}
	
	/*
	 * Displayed the open file on the text editor
	 */
	public void displayText(String line)
	{
		if(line != null)
		{
			ta.setText(line);
		}
	}
	
	/*
	 * Display the output on the console
	 */
	public void cosoleText(String line)
	{
		console.setText(line);
	}
	/*
	 * Function that gets all the files from the project folder and stores them in an array list for compiling 
	 */
	private ArrayList<String> javacCommandBuilder(StringBuilder currentProject, StringBuilder currentFile)
	{
		File folder = new File(currentProject.toString());
		String[] files = folder.list();
		ArrayList<String> command = new ArrayList<String>(Arrays.asList("javac", "-d", "Class"));
		
		for(String file : files)
		{
			command.add(currentProject.toString()+"\\"+file);
		}
		return command;
	}
	
	/*
	 * Function that gets the current open file and runs the file
	 */
	private ArrayList<String> javaCommadBuilder(StringBuilder currentFile)
	{
		ArrayList<String> command = new ArrayList<String>(Arrays.asList("java", "-cp", "Class", "CCLRun"));
		command.add(currentFile.toString().substring(0, currentFile.toString().indexOf(".")));
		return command;
	}
	/*
	 * Function that rewrites the console text
	 */
	
	public void cosoleText() throws IOException
	{
		console.setText(""); //Clear the current text on the console
		//Create  anew Process builder with the command to compile all the projects in the current open directory
		ProcessBuilder processBuilder = new ProcessBuilder(javacCommandBuilder(currentProject, currentFile)); 
		Process process = processBuilder.start(); //Start the process
		//Create and start Processes for the CCL Loader and CCLRun functions
		Process processCCLoader = new ProcessBuilder(new String[] {"javac", "-cp", "Class","-d", "Class", "SDPRO\\src\\CompilingClassLoader.java"} ).start();
		Process processCCL = new ProcessBuilder(new String[] {"javac", "-cp", "Class","-d", "Class", "SDPRO\\src\\CCLRun.java"} ).start();
		
		//If the process fails on compile
		if(process.getErrorStream().read() != -1)
		{
			//Get the process Error message
			InputStream in = process.getErrorStream();
			//Read the error message and paste it to the console
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while (in.read() != -1)
			{
				line = br.readLine();
				console.append(line + "\n");
			}
		}
		//If the CCLpader process fails to compile
		if(processCCLoader.getErrorStream().read() != -1)
		{
			//Get the process Error message
			InputStream in = processCCLoader.getErrorStream();
			//Read the error message and paste it to the console
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while (in.read() != -1)
			{
				line = br.readLine();
				console.append(line + "\n");
			}
		}
		//If the CCLRun fails to compile
		if(processCCL.getErrorStream().read() != -1)
		{
			//Get the process Error message
			InputStream in = processCCL.getErrorStream();
			//Read the error message and paste it to the console
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while (in.read() != -1)
			{
				line = br.readLine();
				console.append(line + "\n");
			}
		}
		//If the process compiles
		if(process.exitValue() == 0)
		{
			//Create a new thread
			ExecutorService service = Executors.newFixedThreadPool(1);
			//Run the java process in a thread
			service.submit(new ProcessTask(javaCommadBuilder(currentFile)));
		}
	}
	
	/*
	 * Reset the title of the project properties window when a new project 
	 */
	public void resetProjectTitle()
	{
		if(currentProject.length() == 0)
		{
			projectTitle.setTitle("Project Properties");
		}
		else
		{
			projectTitle.setTitle(currentProject.toString().substring(currentProject.toString().lastIndexOf('\\') + 1));
		}
		projectProperties.repaint();
	}
	
	/*
	 * Reset the title of the project properties window when a new project 
	 */
	public void resetFileTitle()
	{
		if(currentFile.length() == 0)
		{
			fileTitle.setTitle("Editor");
		}
		else
		{
			fileTitle.setTitle(currentFile.toString());
		}
		textEditor.repaint();
	}
}
