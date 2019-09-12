/***************************************************************************************
*    Title: FileNode  
*    Author:  Sébastien Le Callonnec
*    Date: September 11, 2019
*    Code version: version 1.0
*    Availability: http://www.weblogism.com/item/299
*
***************************************************************************************/

@SuppressWarnings("serial")
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