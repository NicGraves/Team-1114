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
	protected static String saveDirectory = "Project_Directory";
	protected static String projectName = "test";
	       
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
        JMenuItem fileDrop1 = new JMenuItem("Open");
        JMenuItem fileDrop2 = new JMenuItem("Save as");
        menuItem2.add(fileDrop1);
        menuItem2.add(fileDrop2);
        JMenuItem projectDrop1 = new JMenuItem("Open");
        JMenuItem projectDrop2 = new JMenuItem("Create New");
        menuItem1.add(projectDrop1);
        menuItem1.add(projectDrop2);

       
        
        // Text Area "Code"
        JPanel textEditor = new JPanel(new BorderLayout());
        textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) );
        JTextArea ta = new JTextArea();
        //textEditor.setPreferredSize(new Dimension(200, 190));
        JScrollPane scroll = new JScrollPane ( ta );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
	    textEditor.add(scroll, BorderLayout.CENTER);
	    
	    fileDrop2.addActionListener(new ActionListener()
        {
             public void actionPerformed(ActionEvent ae)
             {
                 final String codeSave = ta.getText();
                 BufferedWriter writer = null;
				try {
					JFrame frame = new JFrame();
					String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
					Path path = Paths.get(saveDirectory+"\\"+projectName);
					if(!Files.exists(path))
					{
						Files.createDirectory(path);
					}
					writer = new BufferedWriter(new FileWriter(saveDirectory+"\\"+projectName+"\\"+fileName+".txt"));
					writer.write(codeSave);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
                		     
                		}
             
         });
        
	    projectDrop1.addActionListener(new ActionListener()
	    		{
					public void actionPerformed(ActionEvent e) 
					{
//						chooser = new JFileChooser(); 
//					    chooser.setCurrentDirectory(new java.io.File("."));
//					    chooser.setDialogTitle(choosertitle);
//					    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//					    //
//					    // disable the "All files" option.
//					    //
//					    chooser.setAcceptAllFileFilterUsed(false);
						JFileChooser jfc = new JFileChooser(saveDirectory+"\\");
					    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnValue = jfc.showOpenDialog(null);
						jfc.setAcceptAllFileFilterUsed(false);
						if (returnValue == JFileChooser.APPROVE_OPTION)
						{
							File selectedFile = jfc.getSelectedFile();
							System.out.println(selectedFile.getAbsolutePath());
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
