/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Jared - PC
 */
@SuppressWarnings("serial")
public class StyledDocument extends DefaultStyledDocument 
{
    private JTextArea display;
    private int numBlueKeywords;
    private int numRedKeywords;
    private final StyleContext style = StyleContext.getDefaultStyleContext();
    private final AttributeSet redColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.RED);
    private final AttributeSet blueColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    private final AttributeSet blackColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    private final String blueKeywords;
    private final String[] redKeywords;
    public StyledDocument(String blue, String red, JTextArea dis)
    {
        display = dis;
        numBlueKeywords = 0;
        numRedKeywords = 0;
        blueKeywords = blue;
        redKeywords = red.split("\\w"); //splits up the text file red keywords on word characters [a-zA-z0-9_]
    }
    //finds the first nonword character in the input text before the index given, used for blue keywords
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
    //finds the last nonword character in the input text after the index given, used for blue keywords
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
    //finds the first non-redkeyword character in the input text before the index given, used for red keywords
    private int firstNonkeyChar (String txt, int index)
    {
       boolean match;
       index--;
       while (index >= 0)
       {
           match = false;
           //we need to check against each keyword to find out if there's a match or not
           for(String s : redKeywords)
           {
               //if there's a match, it means we haven't hit the end yet
               //we must check the red keywords in this way because many of these keywords are regex metacharacters and must be dereferenced with \
               if(txt.charAt(index) == (s.charAt(s.length()-1)))
               {
                   match = true;
               }
           }
           //if we get no matches, we've hit the end and can return
           if (!match)
                return index + 1;
           index--;
       }
       return index;
    }
    //finds the last non-redkeyword character in the input text before the input give, used for red keywords
    private int lastNonkeyChar (String txt, int index)
    {
        boolean match = false;
        while (index < txt.length())
        {
           match = false;
           //we need to check against each keyword to find out if there's a match or not
           for(String s : redKeywords)
           {
               //if there's a match, it means we haven't hit the end yet
               if(txt.charAt(index) == (s.charAt(s.length()-1)))
               {
                   match = true;
               }
           }
           //if we get no matches, we've hit the end and can return
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
        if(beforeIndexBlue < 0) beforeIndexBlue = 0;
        int afterIndexBlue = lastNonwordChar(txt, offset + str.length());
        int indexLeftBlue = beforeIndexBlue;
        int indexRightBlue = beforeIndexBlue;
        
        //Set up indices to find red keywords
        int beforeIndexRed = firstNonkeyChar(txt, offset);
        if(beforeIndexRed < 0) beforeIndexRed = 0;
        int afterIndexRed = lastNonkeyChar(txt, offset + str.length());
        int indexLeftRed = beforeIndexRed;
        int indexRightRed = beforeIndexRed;
        
        String word = txt.substring(beforeIndexBlue, offset) + txt.substring(offset + 1, afterIndexBlue);
        String key = txt.substring(beforeIndexRed, offset) + txt.substring(offset + 1, afterIndexRed);
        
        //start at the index of the first nonword character and go to the last nonword character to check for blue keywords
        while(indexRightBlue <= afterIndexBlue)
        {
            //if we've hit the end or we reach a nonword character, we need to check if the word being typed is a keyword
            if (indexRightBlue == afterIndexBlue || String.valueOf(txt.charAt(indexRightBlue)).matches("\\W"))
            { 
                //if the text we're looking at is a keyword, change it's color
                if (txt.substring(indexLeftBlue, indexRightBlue).matches("(\\s)*(\\W)*(" + blueKeywords + ")"))
                {
                    numBlueKeywords++;
                    setCharacterAttributes(indexLeftBlue, indexRightBlue - indexLeftBlue, blueColor, false);
                }
                else
                {
                    if(word.matches("(\\s)*(\\W)*(" + blueKeywords + ")"))
                    {
                        numBlueKeywords--;
                    }
                    setCharacterAttributes(indexLeftBlue, indexRightBlue - indexLeftBlue, blackColor, false);  
                }
                indexLeftBlue = indexRightBlue;//move to the next word
            }
            indexRightBlue++;//increase the size of the substring we're checking
        }
        
        //now we check red keywords. We start at the index of the first nonkey character and go to the last nonkey character
        boolean match;
        while(indexRightRed <= afterIndexRed)
        {
            match = false;
            //similar to how we checked the first/last nonkey character, we need to find out if there's a match in our substring
            for(String s : redKeywords)
            {
                //if the character we're looking at matches part of a keyword, we have a match and should keep expanding the substring
                //we must also check if we've reached the end first so we don't get an error
                if(indexRightRed == afterIndexRed || txt.charAt(indexRightRed) == s.charAt(s.length()-1))
                {
                    match = true;
                }
            }
            //if we've reached the end or we didn't get a match, that means we've hit the end of the substring
            if(indexRightRed == afterIndexRed || !match)
            {
                //if the text we're looking at is a keyword, change it's color
                for (String redKeyword : redKeywords) 
                {
                    //System.out.println(key);
                    if(key.matches("(\\s)*" + redKeyword) && !str.equals(" ") && !str.matches("\\w"))
                            numRedKeywords--;
                    if (txt.substring(indexLeftRed, indexRightRed).matches("(\\s)*" + redKeyword)) 
                    {
                        if(str.equals(" ") || str.matches("\\w"))
                            numRedKeywords--;
                        numRedKeywords++;
                        setCharacterAttributes(indexLeftRed, indexRightRed - indexLeftRed, redColor, false);
                        break;
                    }
                    //because the length of the substring will only be above 1 for substrings that somewhat match the keywords,
                    //in order to not decolor blue keywords this check must be made
                    if (txt.substring(beforeIndexRed, indexRightRed).length() > 1)
                    {
                        setCharacterAttributes(indexLeftRed, indexRightRed - indexLeftRed, blackColor, false);
                    }        
                }
                beforeIndexRed = indexRightRed;
                indexLeftRed = indexRightRed;//move to the next word
            }
            //logic for if our first character in the string is not a match
            if(match && indexRightRed != afterIndexRed)
            {
                for(String s : redKeywords)
                {
                    //if we find that the character is not a match, inverse the boolean
                    if(txt.charAt(indexLeftRed) == s.charAt(s.length()-1))
                    {
                        match = false;
                    }
                }
                //if the character is not a match, increase our left bound on the substring
                if(match)
                    indexLeftRed++;
            }
            indexRightRed++;//increase the size of the substring we're checking
        }
        
        //Display the number of blue and red keywords
        display.setText("Blue Keywords: " + numBlueKeywords + "     Red Keywords: " + numRedKeywords);
    }
    
    @Override
    public void remove(int offset, int length) throws BadLocationException
    {
        String txt = getText(0, getLength());
        int start = firstNonwordChar(txt, offset);
        int startRed = firstNonkeyChar(txt, offset);
        if(start < 0 && startRed < 0 && offset == 0)
        {
            numRedKeywords = 0;
            numBlueKeywords = 0;
            super.remove(offset, length);
            display.setText("Blue Keywords: " + numBlueKeywords + "     Red Keywords: " + numRedKeywords);

            return;
        }
        if(start < 0)start = 0;
        if(startRed < 0)startRed = 0;
        
        String wordPrevious = txt.substring(start, lastNonwordChar(txt,offset));
        String keyPrevious = txt.substring(startRed, lastNonkeyChar(txt, offset));
        super.remove(offset,length);
        
        txt = getText(0, getLength());
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
        
        //check if the word we're looking at is a red keyword or not. if it is, we need to change it later
        for(i = 0; i < redKeywords.length; i++)
        {
            if(txt.substring(beforeIndexRed, afterIndexRed).matches("(\\s*)" + redKeywords[i]))
                break;
        }

        //check to see if it is a blue keyword or not, then change it if it is
        if (txt.substring(beforeIndex, afterIndex).matches("(\\W)*(" + blueKeywords + ")"))
        {
            numBlueKeywords++;
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blueColor, false);
            if(wordPrevious.matches("(\\W)*(" + blueKeywords + ")"))
            {
                numBlueKeywords--;
            }
        }   
        else
        {
            if(wordPrevious.matches("(\\W)*(" + blueKeywords + ")"))
            {
                numBlueKeywords--;
            }
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blackColor, false);
        }  
        //check to see if we found a red keyword or not, then change it if we did
        if(i != redKeywords.length)
        {
            for(i = 0; i < redKeywords.length; i++)
            {
                if(keyPrevious.matches("(\\s*)" + redKeywords[i]))
                {
                    numRedKeywords--;
                    break;
                }   
            }
            //we change the keyword here so that it can be included in the else if statement, avoiding miscolors
            numRedKeywords++;
            setCharacterAttributes(beforeIndexRed, afterIndexRed - beforeIndexRed, redColor, false);
        }
        else
        {
            for(i = 0; i < redKeywords.length; i++)
            {
                if(keyPrevious.matches("(\\s*)" + redKeywords[i]))
                {
                    numRedKeywords--;
                    break;
                }   
            }
            setCharacterAttributes(beforeIndexRed, afterIndexRed - beforeIndexRed, blackColor, false);
        }
        
        //Display the number of blue and red keywords
        display.setText("Blue Keywords: " + numBlueKeywords + "     Red Keywords: " + numRedKeywords);
    }
    
    public int getNumRedKeywords(){return numRedKeywords;}
    public int getNumBlueKeywords(){return numBlueKeywords;}
}