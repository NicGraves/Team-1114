import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class projectProperties 
{
	private static JPanel projectProperties = new JPanel(new BorderLayout());
	
	public void buildProjectProperties()
	{
		projectProperties.setPreferredSize(new Dimension(200, 190)); //Set the size of the window
    	projectProperties.setBorder ( new TitledBorder ( new EtchedBorder (), "Project" ) ); //Add a border around the window
	}
	
	protected JPanel getProjectProperties()
	{
		return projectProperties;
	}
}
