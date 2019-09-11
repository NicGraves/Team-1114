import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Main 
{
	protected static String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
	static String projectName = ""; //name of the current open project
	       
	public static void main(String[] args) 
	{
		 //Creating the Frame
		String hold = "";
        JFrame frame = new JFrame("IDE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        //Creating the MenuBar and adding components
        JMenuBar menuBar = new JMenuBar();
        JMenu menuItem1 = new JMenu("Project");
        JMenu menuItem2 = new JMenu("File");
        JMenu menuItem3 = new JMenu("Help");
        menuBar.add(menuItem1);
        menuBar.add(menuItem2);
        menuBar.add(menuItem3);
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenuItem fileSaveAs = new JMenuItem("Save as");
        menuItem2.add(fileOpen);
        menuItem2.add(fileSaveAs);
        JMenuItem projectOpen = new JMenuItem("Open");
        JMenuItem projectCreateNew = new JMenuItem("Create New");
        menuItem1.add(projectOpen);
        menuItem1.add(projectCreateNew);

       
        
        // Text Area "Code"
        JPanel textEditor = new JPanel(new BorderLayout()); //create a new JPanel with a border layout
        textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
        JTextArea ta = new JTextArea(); //Create a new JTextArea
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    textEditor.add(scroll, BorderLayout.CENTER);
	    
	    //Listener for the saveAs button under files
	    fileSaveAs.addActionListener(new ActionListener()
		        {
		             public void actionPerformed(ActionEvent ae)
		             {
		                final String codeSave = ta.getText(); //Get the text that is on the TextEditor window
		                BufferedWriter writer = null; //Create a BufferedWriter
						try 
						{
							JFrame fileNameGetter = new JFrame(); //Create a new JFrame
							String fileName = JOptionPane.showInputDialog(fileNameGetter, "Enter file name:"); //Create a dialogue box asking for a file name
							Path path = Paths.get(saveDirectory+"\\"+projectName); //set the path to the IDE workspace in the correct file project
							if(!Files.exists(path)) //If the specified path does not exist
							{
								Files.createDirectory(path); //Create that file path
							}
							writer = new BufferedWriter(new FileWriter(saveDirectory+"\\"+projectName+"\\"+fileName+".txt")); //Select the specified folder and add the text file
							writer.write(codeSave); //Write what was on the Text Editor window to the text file
							writer.close(); //Close the file writer
						} catch (IOException e) {
							e.printStackTrace();
						}
		                		     
		                		}
		             
		         });
        
	    //Listener for the Open button under Project
	    projectOpen.addActionListener(new ActionListener()
	    		{
					public void actionPerformed(ActionEvent e) 
					{
						JFileChooser jfc = new JFileChooser(saveDirectory+"\\"); //Create a JFileChooser opened to the IDE workspace
					    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //User is only able to select folders
						int returnValue = jfc.showOpenDialog(null);
						jfc.setAcceptAllFileFilterUsed(false);
						if (returnValue == JFileChooser.APPROVE_OPTION) //If the selected Folder is okay
						{
							File selectedFile = jfc.getSelectedFile(); //Save the folder path that they have selected
							System.out.println(selectedFile.getAbsolutePath());
						}
					}
	    	
	    		});

	    projectCreateNew.addActionListener(new ActionListener()
	    		{
			    	public void actionPerformed(ActionEvent e) 
					{
			    		try 
						{
							JFrame frame = new JFrame();
							projectName = JOptionPane.showInputDialog(frame, "Enter Project name:");
							Path path = Paths.get(saveDirectory+"\\"+projectName);
							if(!Files.exists(path))
							{
								Files.createDirectory(path);
							}
							else
							{
								System.out.print("A project with that name already exists");
							}
						} catch (IOException s) {
							s.printStackTrace();
						}
					}
	    		});
	    
        //Creating the console
        JPanel output = new JPanel(new BorderLayout());
        output.setBorder ( new TitledBorder ( new EtchedBorder (), "Console" ) );
        JTextArea console = new JTextArea();
        output.setPreferredSize(new Dimension(50,150));
        console.insert(hold,0);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false);
        JScrollPane Cscroll = new JScrollPane (console);
        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
        output.add(Cscroll);
        
        //side
        String project = "";
        JPanel projectProperties = new JPanel(new BorderLayout());
        projectProperties.setPreferredSize(new Dimension(200, 190));
        JMenuBar PMenu = new JMenuBar();
        projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) );
        JMenu file = new JMenu(project);
        projectProperties.add(PMenu);
        PMenu.add(file);
        JMenuItem test = new JMenuItem("test");
        file.add(test);       
        
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.LINE_START,projectProperties);
        frame.getContentPane().add(BorderLayout.PAGE_END, output);
        frame.getContentPane().add(BorderLayout.PAGE_START, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, textEditor);
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	

	}

}
