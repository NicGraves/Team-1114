//Retrieved from http://www.weblogism.com/item/299

public class folderNameGetter extends java.io.File 
{

    public folderNameGetter(String directory) 
    {
        super(directory);
    }

    public folderNameGetter(folderNameGetter parent, String child) 
    {
        super(parent, child);
    }

    @Override
    public String toString() 
    {
        return getName();
    }
}