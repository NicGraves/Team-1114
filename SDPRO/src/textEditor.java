import java.awt.BorderLayout;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class textEditor 
{
	private JPanel textEditor = new JPanel(new BorderLayout()); //create a new JPanel with a border layout
	private JTextPane ta = new JTextPane(); //Create a new JTextArea
	
	public void buildTextEditor()
	{
		String redKeywords = "";
		String blueKeywords = "";
		FileReader in;
		try
		{
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
		StyledDocument doc = new StyledDocument(blueKeywords, redKeywords);
		ta = new JTextPane(doc);
		textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    textEditor.add(scroll, BorderLayout.CENTER);
	}
	
	protected JPanel getTextEditor()
	{
		return textEditor;
	}
	
	protected JTextPane getTextArea()
	{
		return ta;
	}
}
