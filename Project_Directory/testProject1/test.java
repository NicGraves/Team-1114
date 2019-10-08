import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
public class test 
{

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		System.out.println("Enter a number of hours"); //gets the number of hours the user wants to convert
		String hours = JOptionPane.showInputDialog(fileNameGetter, "Enter file name (.java is not needed):");
		System.out.println(hours);
	}
	
}
