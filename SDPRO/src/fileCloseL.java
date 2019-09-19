import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fileCloseL implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String contentBuilder = "";
		textEditor text = new textEditor(contentBuilder);
		text.displayText(contentBuilder);

	}

}
