import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class fileSaveL implements ActionListener
{
	textEditor full = new textEditor();
	JFrame frame = new JFrame();

	public void actionPerformed(ActionEvent e) 
	{
		String[] lines = full.ta.getText().split("\\n");
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(full.text));
			for(int i = 0 ; i< lines.length; i++)
			{
				writer.write(lines[i] + "\n");
			}
		    writer.close();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(frame, "File saved");
    }
		
	
	
}
