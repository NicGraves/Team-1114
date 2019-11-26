import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.*;

public class Main 
{
	
	public static void main(String[] args) 
	{
		//Build the Text Editor JFrame
		UIBuilder u = new UIBuilder();
		JFrame frame = new JFrame("IDE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame.setSize(xSize,ySize);
		frame.getContentPane().add(BorderLayout.PAGE_START, u.buildMenu());
    	frame.getContentPane().add(BorderLayout.LINE_START, u.buildProjectProperties());
        frame.getContentPane().add(BorderLayout.CENTER, u.buildTextEditor());
        frame.getContentPane().add(BorderLayout.PAGE_END, u.buildConsole());
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	}
}

