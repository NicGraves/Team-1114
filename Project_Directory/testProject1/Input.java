import java.util.Scanner;
class Input {
    public static void main(String[] args) {
    	
    	Scanner input = new Scanner(System.in);
    	System.out.println("Hello World");
    	System.out.println("Enter an name: ");
    	String name = input.nextLine();
    	System.out.println("You entered " + name);
    }
}
