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
		
		try {
		      runProcess("javac Project_Directory\\testProject0\\test.java");
		      runProcess("javac Project_Directory\\testProject0\\test2.java");
		      runProcess("java -cp Project_Directory\\testProject0 test");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	  private static void printLines(String name, InputStream ins) throws Exception {
		    String line = null;
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(ins));
		    while ((line = in.readLine()) != null) {
		        System.out.println(line);
		    }
		  }

		  private static void runProcess(String command) throws Exception {
		    Process pro = Runtime.getRuntime().exec(command);
		    printLines(command + " stdout:", pro.getInputStream());
		    printLines(command + " stderr:", pro.getErrorStream());
		    pro.waitFor();
		  }
}

