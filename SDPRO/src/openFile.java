import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openFile {

	public static void open(String currentFile ) {
		  String contentBuilder = "";
		    try (BufferedReader br = new BufferedReader(new FileReader(currentFile)))
		    {
		 
		        String sCurrentLine;
		        while ((sCurrentLine = br.readLine()) != null)
		        {
		            contentBuilder += sCurrentLine + "\n";
		        }
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace();
		    }
		    textEditor full = new textEditor();
		    full.buildFullTextEditor(contentBuilder);
	}
}
