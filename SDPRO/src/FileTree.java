/***************************************************************************************
*    Title: FileSelectorModel  
*    Author:  Sébastien Le Callonnec
*    Date: September 11, 2019
*    Code version: version 1.0
*    Availability: http://www.weblogism.com/item/299
*
***************************************************************************************/

import java.util.Arrays;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileTree implements TreeModel 
{

    private folderNameGetter root;

    /**
     * the constructor defines the root.
     */
    public FileTree(String directory) 
    {
        root = new folderNameGetter(directory);
    }

    public Object getRoot() 
    {
        return root;
    }

    /**
     * returns the <code>parent</code>'s child located at index <code>index</code>.
     */
    public Object getChild(Object parent, int index) 
    {
        folderNameGetter parentNode = (folderNameGetter) parent;
        return new folderNameGetter(parentNode, parentNode.listFiles()[index].getName());
    }

    /**
     * returns the number of child.  If the node is not a directory, or its list of children
     * is null, returns 0.  Otherwise, just return the number of files under the current file.
     */
    public int getChildCount(Object parent) 
    {
        folderNameGetter parentNode = (folderNameGetter) parent;
        if (parent == null || !parentNode.isDirectory() || parentNode.listFiles() == null) 
        {
            return 0;
        }

        return parentNode.listFiles().length;
    }

    /**
     * returns true if {{@link #getChildCount(Object)} is 0.
     */
    public boolean isLeaf(Object node) 
    {
        return (getChildCount(node) == 0);
    }

    /**
     * return the index of the child in the list of files under <code>parent</code>.
     */
    public int getIndexOfChild(Object parent, Object child) 
    {
        folderNameGetter parentNode = (folderNameGetter) parent;
        folderNameGetter childNode = (folderNameGetter) child;

        return Arrays.asList(parentNode.list()).indexOf(childNode.getName());
    }

    // The following methods are not implemented, as we won't need them for this example.

    public void valueForPathChanged(TreePath path, Object newValue) 
    {
    }

    public void addTreeModelListener(TreeModelListener l) 
    {
    }

    public void removeTreeModelListener(TreeModelListener l) 
    {
    }
}