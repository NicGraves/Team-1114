import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;

public class Main 
{
	
	public static void main(String[] args) 
	{
		//Build the Text Editor JFrame
		UIBuilder u = new UIBuilder();
		JFrame frame = new JFrame("IDE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.getContentPane().add(BorderLayout.PAGE_START, u.buildMenu());
    	frame.getContentPane().add(BorderLayout.LINE_START, u.buildProjectProperties());
        frame.getContentPane().add(BorderLayout.CENTER, u.buildTextEditor());
        frame.getContentPane().add(BorderLayout.PAGE_END, u.buildConsole());
        frame.setLocationRelativeTo ( null );
        frame.setVisible(true);
	}
}

