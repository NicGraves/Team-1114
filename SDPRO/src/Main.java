
import java.awt.BorderLayout;
import javax.swing.*;

public class Main 
{
	protected static String saveDirectory = "Project_Directory"; //Name of the IDE workspace where all projects get saved
	protected static StringBuilder currentProject; //Saves the path of the current open project
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("IDE");
		menu m = new menu();
		projectProperties p = new projectProperties(currentProject);
		textEditor t = new textEditor();
		console c = new console();
		//Creating the Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        m.buildMenu(saveDirectory);
        p.buildProjectProperties();
        t.buildTextEditor();
        c.buildConsole();
	    frame.getContentPane().add(BorderLayout.PAGE_START, m.getJMenuBar());
    	frame.getContentPane().add(BorderLayout.LINE_START, p.getProjectProperties()); //Add the JPanel to the frame
        frame.getContentPane().add(BorderLayout.CENTER, t.getTextEditor());
        frame.getContentPane().add(BorderLayout.PAGE_END, c.getConsole()); //Add the JPanel to the JFrame
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	}
}