import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class messageDisplay 
{
	static JFrame frame = new JFrame();
	
	public void displayMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	public static String optionPane()
	{
		return JOptionPane.showInputDialog(frame, "Enter Project name:");
	}
}
