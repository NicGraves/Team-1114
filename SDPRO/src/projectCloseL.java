import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class projectCloseL implements ActionListener
{
	projectProperties p = new projectProperties(null);

	public void actionPerformed(ActionEvent e) 
	{
		p.clearFiles();
		String contentBuilder = "";
		textEditor text = new textEditor(contentBuilder);
		text.displayText(contentBuilder);
	}
}
