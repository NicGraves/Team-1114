import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class projectCloseL extends openProjectL implements ActionListener
{
	public projectCloseL(String saveDirectory, String currentProject) 
	{
		super(saveDirectory, currentProject);
		currentProject = null;
	}

	public void actionPerformed(ActionEvent e) 
	{
		projectProperties p = new projectProperties();
		openProjectL.tree.setModel(null);
		p.getProjectProperties().revalidate();
        p.getProjectProperties().repaint();
	}
}
