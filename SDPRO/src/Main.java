import java.awt.BorderLayout;
import javax.swing.*;

public class Main 
{
	
	public static void main(String[] args) 
	{
		UIBuilder u = new UIBuilder();
		JFrame frame = new JFrame("IDE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.getContentPane().add(BorderLayout.PAGE_START, u.buildMenu());
    	frame.getContentPane().add(BorderLayout.LINE_START, u.buildProjectProperties()); //Add the JPanel to the frame
        frame.getContentPane().add(BorderLayout.CENTER, u.buildTextEditor());
        frame.getContentPane().add(BorderLayout.PAGE_END, u.buildConsole()); //Add the JPanel to the JFrame
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	}
}