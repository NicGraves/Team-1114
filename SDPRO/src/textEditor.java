import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class textEditor 
{
	private JPanel textEditor = new JPanel(new BorderLayout()); //create a new JPanel with a border layout
	
	public void buildTextEditor()
	{
		textEditor.setBorder ( new TitledBorder ( new EtchedBorder (), "Text Editor" ) ); //create a border around the JPanel with the name "Text Editor"
		JTextArea ta = new JTextArea(); //Create a new JTextArea
        JScrollPane scroll = new JScrollPane ( ta ); //Create a new JScrollPane and add the JTextArea
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bar to only appear when necessary
	    textEditor.add(scroll, BorderLayout.CENTER);
	}
	
	protected JPanel getTextEditor()
	{
		return textEditor;
	}
}
