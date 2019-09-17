import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class console 
{
	private JPanel output = new JPanel(new BorderLayout());
	
	public void buildConsole()
	{
		String hold = "";
        output.setBorder ( new TitledBorder ( new EtchedBorder (), "Console" ) ); //Create the border
        JTextArea console = new JTextArea(); //Create a new Text Area
        output.setPreferredSize(new Dimension(50,150)); //Set the size of the JPanel
        console.insert(hold,0);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false); //Does not allow the user to edit the output
        JScrollPane Cscroll = new JScrollPane (console); //Create a JScrollPane object
        Cscroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ); //Set the scroll bars to appear when necessary
        output.add(Cscroll); //Add the scroll to the JPanel
	}
	
	protected JPanel getConsole()
	{
		return output;
	}
}
