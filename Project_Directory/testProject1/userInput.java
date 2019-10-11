import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
public class userInput 
{

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.println("Reached0");
		int hours = 0;
		System.out.println("Reached1");
		JFrame fileNameGetter = new JFrame(); //Create a new JFrame
		System.out.println("Reached2");
		System.out.println("Enter a number of hours"); //gets the number of hours the user wants to convert
		System.out.println("Reached3");
		hours = input.nextInt();
		System.out.println("Reached4");
		System.out.println(hours);
		System.out.println("Reached5");
	}
	
}
