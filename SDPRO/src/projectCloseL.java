import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class projectCloseL implements ActionListener
{

	public void actionPerformed(ActionEvent e) 
	{
		projectProperties p = new projectProperties();
		openProjectL.tree.setModel(null);
		p.getProjectProperties().revalidate();
        p.getProjectProperties().repaint();
	}
}
