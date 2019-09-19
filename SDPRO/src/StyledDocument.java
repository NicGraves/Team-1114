/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectPackage;

import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Jared - PC
 */
public class StyledDocument extends DefaultStyledDocument 
{
    private final StyleContext style = StyleContext.getDefaultStyleContext();
    private final AttributeSet redColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.RED);
    private final AttributeSet blueColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    private final AttributeSet blackColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    private String blueKeywords;
    private String[] redKeywords;
    public StyledDocument(String blue, String red)
    {
        blueKeywords = blue;
        redKeywords = red.split("\\w");
    }
    //finds the last nonword character in the input text starting at the index given
    private int lastNonwordChar (String txt, int index)
    {
       index--;
       while (index >= 0)
       {
           if (String.valueOf(txt.charAt(index)).matches("\\W"))
               return index;
           index--;
       }
       return index;
    }
    //finds the first nonword character in the input text starting at the index given
    private int firstNonwordChar(String txt, int index)
    {
        while (index < txt.length())
        {
            if (String.valueOf(txt.charAt(index)).matches("\\W"))
                return index;
            index++;
        }
        return index;
    }
    
    //override existing DefaultStyledDocument methods to color the text
    @Override
    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException
    {
        super.insertString(offset, str, a);
        
        String txt = getText(0, getLength()); //get all text in the box
        int beforeIndex = lastNonwordChar(txt, offset);
        if(beforeIndex < 0)
            beforeIndex = 0;
        int afterIndex = firstNonwordChar(txt, offset + str.length());
        int indexLeft = beforeIndex;
        int indexRight = beforeIndex;
        
        //start at the index of the last nonword character and go to the first nonword character
        while(indexRight <= afterIndex)
        {            
            System.out.println(txt.substring(indexLeft, indexRight));
            if (indexRight == afterIndex || String.valueOf(txt.charAt(indexRight)).matches("\\W"))
            {
                
                //if the text we're looking at is a keyword, change it's color
                if (txt.substring(indexLeft, indexRight).matches("(\\s)*(" + blueKeywords + ")"))
                {
                    setCharacterAttributes(indexLeft, indexRight - indexLeft, blueColor, false);
                    indexLeft = indexRight;
                }
                else if(!txt.substring(indexLeft, indexRight).matches("(\\w)"))
                {
                    for (int x = 0; x < redKeywords.length; x++)
                    {
                        if (txt.substring(indexLeft, indexRight).matches("(\\s)*("+redKeywords[x]+")"))
                        {
                            setCharacterAttributes(indexLeft, indexRight - indexLeft, redColor, false);
                            break;
                        }
                    }
                }
                else
                {
                    setCharacterAttributes(indexLeft, indexRight - indexLeft, blackColor, false);
                    indexLeft = indexRight;//move to the next word
                }
                
            }
            indexRight++;
        }
    }
    
    @Override
    public void remove(int offset, int length) throws BadLocationException
    {
        super.remove(offset,length);
        
        String txt = getText(0, getLength());
        int beforeIndex = lastNonwordChar(txt, offset);
        if (beforeIndex < 0)
            beforeIndex = 0;
        int afterIndex = firstNonwordChar(txt, offset);
        
        if (txt.substring(beforeIndex, afterIndex).matches("(\\W)*(" + blueKeywords + ")"))
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blueColor, false);
        else if(!txt.substring(beforeIndex, afterIndex).matches("(\\s)*(\\w)*"))
                {
                    for (int x = 0; x < redKeywords.length; x++)
                    {
                        if (txt.substring(beforeIndex, afterIndex).matches(redKeywords[x]))
                        {
                            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, redColor, false);
                            break;
                        }
                    }
                }
        else
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blackColor, false);
    }
}