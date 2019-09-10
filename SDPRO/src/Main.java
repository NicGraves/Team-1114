import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Main {

	
	       
	public static void main(String[] args) {
		 //Creating the Frame
		String hold = "";
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

       
        
        // Text Area "Code"
        JPanel textarea = new JPanel();
        JTextArea ta = new JTextArea(17,73);
        textarea.setPreferredSize(new Dimension(200, 190));
        JScrollPane scroll = new JScrollPane ( ta );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    textarea.add(scroll);
	    
	    m22.addActionListener(new ActionListener()
        {
             public void actionPerformed(ActionEvent ae)
             {
                 final String codeSave = ta.getText();
                 BufferedWriter writer = null;
				try {
					String saveDirectory = "Project_Directory";
					JFrame frame = new JFrame();
					String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");
					Path path = Paths.get(saveDirectory);
					if(!Files.exists(path))
					{
						Files.createDirectory(path);
					}
					writer = new BufferedWriter(new FileWriter(saveDirectory+"\\"+fileName+".txt"));
					writer.write(codeSave);
					writer.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
                		     
                		}
             
         });
        
        //Creating the console
        JPanel panel = new JPanel();
        JTextArea console = new JTextArea(8,58);
        panel.setPreferredSize(new Dimension(150, 150));
        console.insert(hold,0);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false);
        JScrollPane Cscroll = new JScrollPane (console);
        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        panel.add(Cscroll);
        
        //side
        String project = "";
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(150, 190));
        JMenuBar PMenu = new JMenuBar();
        side.setBorder ( new TitledBorder ( new EtchedBorder (), "Package" ) );
        JMenu file = new JMenu(project);
        side.add(PMenu);
        PMenu.add(file);
        JMenuItem test = new JMenuItem("test");
        file.add(test);       
        
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.WEST,side);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, textarea);
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	

	}

}
