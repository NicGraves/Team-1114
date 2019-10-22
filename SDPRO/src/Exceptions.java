import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Custom Exceptions that print custom error depending on the issue that is encountered
 */

@SuppressWarnings("serial")
class NoFileNameException extends Exception 
{
	public NoFileNameException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class FileExistsException extends Exception 
{
	public FileExistsException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class FileDoesNotExistException extends Exception 
{
	public FileDoesNotExistException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class NoFileToSaveException extends Exception 
{
	public NoFileToSaveException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class NoFileOpen extends Exception 
{
	public NoFileOpen(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class ProjectNotOpenException extends Exception 
{
	public ProjectNotOpenException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class WorkspaceFolderException extends Exception 
{
	public WorkspaceFolderException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class ProjectExistsException extends Exception 
{
	public ProjectExistsException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}

@SuppressWarnings("serial")
class NoProjectNameException extends Exception 
{
	public NoProjectNameException(String errorMessage) 
	{
        super(errorMessage);
        JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, errorMessage);
    }
}