/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private String blueKeywords, redKeystring;
    private String[] redKeywords;
    public StyledDocument(String blue, String red)
    {
        redKeystring = "";
        blueKeywords = blue;
        redKeywords = red.split("\\w");
        for(int x = 0; x < redKeywords.length; x++)
            redKeystring += redKeywords[x];
    }
    //finds the first nonword character in the input text before the index given
    private int firstNonwordChar (String txt, int index)
    {
       index--;
       while (index >= 0)
       {
           if (String.valueOf(txt.charAt(index)).matches("\\W"))
               return index+1;
           index--;
       }
       return index;
    }
    //finds the last nonword character in the input text after the index given
    private int lastNonwordChar(String txt, int index)
    {
        while (index < txt.length())
        {
            if (String.valueOf(txt.charAt(index)).matches("\\W"))
                return index;
            index++;
        }
        return index;
    }
    private int firstNonkeyChar (String txt, int index)
    {
       boolean match;
       index--;
       while (index >= 0)
       {
           match = false;
           for(String s : redKeywords)
           {
               if(txt.charAt(index) == (s.charAt(s.length()-1)))
               {
                   match = true;
               }
           }
           if (!match)
                return index + 1;
           index--;
       }
       return index;
    }
    private int lastNonkeyChar (String txt, int index)
    {
        boolean match = false;
        while (index < txt.length())
        {
           match = false;
           for(String s : redKeywords)
           {
               if(txt.charAt(index) == (s.charAt(s.length()-1)))
               {
                   match = true;
               }
           }
           if (!match)
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
        //Set up indices to find blue keywords
        int beforeIndexBlue = firstNonwordChar(txt, offset);
        if(beforeIndexBlue < 0)
            beforeIndexBlue = 0;
        int afterIndexBlue = lastNonwordChar(txt, offset + str.length());
        int indexLeftBlue = beforeIndexBlue;
        int indexRightBlue = beforeIndexBlue;
        //Set up indices to find red keywords
        int beforeIndexRed = firstNonkeyChar(txt, offset);
        if(beforeIndexRed < 0)
            beforeIndexRed = 0;
        int afterIndexRed = lastNonkeyChar(txt, offset + str.length());
        int indexLeftRed = beforeIndexRed;
        int indexRightRed = beforeIndexRed;
        
        //start at the index of the last nonword character and go to the first nonword character
        while(indexRightBlue <= afterIndexBlue)
        {        
            if (indexRightBlue == afterIndexBlue || String.valueOf(txt.charAt(indexRightBlue)).matches("\\W"))
            { 
                //System.out.println(txt.substring(indexLeftBlue, indexRightBlue));
                //if the text we're looking at is a keyword, change it's color
                if (txt.substring(indexLeftBlue, indexRightBlue).matches("(\\s)*(" + blueKeywords + ")"))
                {
                    setCharacterAttributes(indexLeftBlue, indexRightBlue - indexLeftBlue, blueColor, false);
                }
                else
                {
                    setCharacterAttributes(indexLeftBlue, indexRightBlue - indexLeftBlue, blackColor, false);  
                }
                indexLeftBlue = indexRightBlue;//move to the next word
            }
            indexRightBlue++;
        }
        boolean match;
        while(indexRightRed <= afterIndexRed)
        {
            match = false;
            for(String s : redKeywords)
            {
                if(indexRightRed == afterIndexRed || txt.charAt(indexRightRed) == s.charAt(s.length()-1))
                {
                    match = true;
                }
            }
            if(indexRightRed == afterIndexRed || !match)
            {
                //System.out.println(txt.substring(indexLeftRed, indexRightRed));
                //if the text we're looking at is a keyword, change it's color
                for (String redKeyword : redKeywords) 
                {
                    if (txt.substring(indexLeftRed, indexRightRed).matches("(\\s)*" + redKeyword)) 
                    {
                        setCharacterAttributes(indexLeftRed, indexRightRed - indexLeftRed, redColor, false);
                        break;
                    }                   
                }
                indexLeftRed = indexRightRed;//move to the next word
            }
            indexRightRed++;
        }
    }
    
    @Override
    public void remove(int offset, int length) throws BadLocationException
    {
        super.remove(offset,length);
        
        String txt = getText(0, getLength());
        //Set up blue indices
        int beforeIndex = firstNonwordChar(txt, offset);
        if (beforeIndex < 0)
            beforeIndex = 0;
        int afterIndex = lastNonwordChar(txt, offset);
        //Set up red indices
        int beforeIndexRed = firstNonkeyChar(txt, offset);
        if(beforeIndexRed < 0)
            beforeIndexRed = 0;
        int afterIndexRed = lastNonkeyChar(txt, offset);
        int i;
        for(i = 0; i < redKeywords.length; i++)
        {
            if(txt.substring(beforeIndexRed, afterIndexRed).matches("(\\s*)" + redKeywords[i]))
                break;
        }
        if (txt.substring(beforeIndex, afterIndex).matches("(\\W)*(" + blueKeywords + ")"))
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blueColor, false);
        else if(i != redKeywords.length)
            {
                setCharacterAttributes(beforeIndexRed, afterIndexRed - beforeIndexRed, redColor, false);
            }
        else
        {
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blackColor, false);
            setCharacterAttributes(beforeIndexRed, afterIndexRed - beforeIndexRed, blackColor, false);
        }
    }
}