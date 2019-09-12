import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class projectCloseL extends openProjectL implements ActionListener
{
	
	public projectCloseL(String saveDirectory, JPanel projectProperties) 
	{
		super(saveDirectory, projectProperties);
		this.projectProperties = projectProperties;
	}

	public void actionPerformed(ActionEvent e) 
	{
		openProjectL.tree.setModel(null);
		projectProperties.revalidate();
        projectProperties.repaint();
	}
}
