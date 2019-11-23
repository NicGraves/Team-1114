import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JTextField;


public class JTextFieldInputStream extends InputStream implements ActionListener 
{
    byte[] contents;
    int pointer = 0;
    JTextField textArea;
    String str = "";
    
    public JTextFieldInputStream(JTextField textArea)
    {
    	this.textArea = textArea;
    }

    //gets triggered every time that "Enter" is pressed on the textfield
    public void actionPerformed(ActionEvent e) 
    {
        str = textArea.getText() + "\n";
        pointer = 0;
        textArea.setText("");
        synchronized (this) 
        {
            //maybe this should only notify() as multiple threads may
            //be waiting for input and they would now race for input
            this.notifyAll();
        }
    }

    @Override
    public int read() 
    {
        //test if the available input has reached its end
        //and the EOS should be returned 
        if(str != null && pointer == str.length())
        {
            str =null;
            //this is supposed to return -1 on "end of stream"
            //but I'm having a hard time locating the constant
            return java.io.StreamTokenizer.TT_EOF;
        }
        //no input available, block until more is available because that's
        //the behavior specified in the Javadocs
        while (str == null || pointer >= str.length()) 
        {
            try 
            {
                //according to the docs read() should block until new input is available
                synchronized (this) 
                {
                    this.wait();
                }
            } 
            catch (InterruptedException ex) 
            {
                ex.printStackTrace();
            }
        }
        //read an additional character, return it and increment the index
        return str.charAt(pointer++);
    }
}