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
public class StyledDocument extends DefaultStyledDocument 
{
    private JTextArea display;
    private int numBlueKeywords;
    private int numRedKeywords;
    private final StyleContext style = StyleContext.getDefaultStyleContext();
    private final AttributeSet redColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.RED);
    private final AttributeSet blueColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    private final AttributeSet greenColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.decode("#008F11"));
    private final AttributeSet blackColor = style.addAttribute(style.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    private final String blueKeywords;
    private final String[] redKeywords;
    private String redKeyString;
    public StyledDocument(String blue, String red, JTextArea dis)
    {
        display = dis;
        numBlueKeywords = 0;
        numRedKeywords = 0;
        blueKeywords = blue;
        redKeyString = "";
        redKeywords = red.split("\\w"); //splits up the text file red keywords on word characters [a-zA-z0-9_]
        for(String s : redKeywords)
        {
            redKeyString = redKeyString + s + "|";
        }
        redKeyString = redKeyString.substring(0, redKeyString.length() - 1);
    }
    //returns true if the index in inside a string literal declaration
    private boolean isInString(String txt, int index)
    {
        int openIndex = -1;
        int closeIndex = 0;
        for(int i = 0; i < txt.length(); i++)
        {
            //if we find a quote, it's either the open or close quote
            if(txt.charAt(i) == '"')
            {
                if(i > 0 && txt.charAt(i-1) == '\\')
                    continue;
                //if the index of the last open quote is smaller than the last closed quote,
                //we know this is a new open quote and move the index
                if(openIndex < closeIndex)
                    openIndex = i;
                else
                {
                    closeIndex = i;
                    //If we just closed a quote, find out if our index is in the bounds of the quote
                    if(index < closeIndex && index > openIndex)
                        return true;
                }
            }
        }
        //If the index is not within the bounds of any quote, but is after a hanging open quote, consider it in a string
        return index < txt.length() && index > openIndex && closeIndex < openIndex && openIndex != -1;
    }
    //finds the next instance of a quote
    private int nextQuote(String txt, int index)
    {
        for(int x = index; x < txt.length(); x++)
        {
            if(txt.charAt(x) == '"')
                return x;
        }
        return txt.length() - 1;
    }
    //If an end quote is deleted, the quotes need to be fixed
    private void fixQuotes(String txt) throws BadLocationException
    {
        insertString(-1, txt, blackColor);
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
    
    //returns true if the character input is not a key character
    private boolean nonkeyChar (char c)
    {
        for(String s : redKeywords)
        {
            if(c == s.charAt(s.length() - 1))
                return false;
        }
        return true;
    }
    
    //override existing DefaultStyledDocument methods to color the text
    @Override
    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException
    {
        if(offset != -1)
            super.insertString(offset, str, a);
        else
        {
            offset = 0;
            numBlueKeywords = 0;
            numRedKeywords = 0;
        }
            
        
        if(str.equals("\""))
        {
            super.insertString(offset, str, a);
            setCharacterAttributes(offset, 2, greenColor, false);
            return;
        }
        
        String txt = getText(0, getLength()); //get all text in the box

        if(isInString(txt, offset))
        {
            setCharacterAttributes(offset, 1, greenColor, false);
            return;
        }
        
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
        
        String redBefore = txt.substring(beforeIndexRed, offset);
        String redAfter = txt.substring(offset + 1, afterIndexRed);
        String word = txt.substring(beforeIndexBlue, offset) + txt.substring(offset + 1, afterIndexBlue);
        String key = redBefore + redAfter;
        
        //start at the index of the first nonword character and go to the last nonword character to check for blue keywords
        while(indexRightBlue <= afterIndexBlue)
        {
            //if we've hit the end or we reach a nonword character, we need to check if the word being typed is a keyword
            if (indexRightBlue == afterIndexBlue || String.valueOf(txt.charAt(indexRightBlue)).matches("\\W"))
            { 
                //if the text we're looking at is a keyword, change it's color
                if (txt.substring(indexLeftBlue, indexRightBlue).matches("(\\s)*(\\W)*(" + blueKeywords + ")"))
                {
                    if(!isInString(txt, indexLeftBlue+1))
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
        while(indexRightRed <= afterIndexRed)
        {
            //if we hit the end or we find a nonkey character, we need to check if the word being typed is a keyword
            if(indexRightRed == afterIndexRed || nonkeyChar(txt.charAt(indexRightRed)))
            {
                //if it's a keyword, change its color
                if (txt.substring(indexLeftRed, indexRightRed).matches("(\\s)*(\\w)*(" + redKeyString + ")"))    
                {
                    while(nonkeyChar(txt.charAt(indexLeftRed)))
                        indexLeftRed++;
                    if(!isInString(txt, indexLeftRed+1))
                        numRedKeywords++;
                    setCharacterAttributes(indexLeftRed, indexRightRed - indexLeftRed, redColor, false);
                }
                //if not, and the substring isn't just what we just typed, then set it to black
                else if(!txt.substring(indexLeftRed, indexRightRed).equals(str) && str.length() == 1)
                {  
                    setCharacterAttributes(indexLeftRed, indexRightRed - indexLeftRed, blackColor, false);
                }
                indexLeftRed = indexRightRed;
            }
            indexRightRed++;
        }
        //if the word before the character typed was a keyword, it's no longer a keyword. If it is, it's already been incremented
        //and to maintain an accurate count, it should not be incremented.
        if(key.matches("(\\s)*(" + redKeyString + ")"))
        {
            numRedKeywords--;
        }
        int at = 0;
        int index;
        while((index = nextQuote(txt, at)) != txt.length() - 1)
        {
            //setCharacterAttributes(at, (index - at), blackColor, false);
            at = index + 1;
            index = nextQuote(txt,at);
            setCharacterAttributes(at - 1, (index - at + 2), greenColor, false);          
            at = index + 1;
        }
        //Display the number of blue and red keywords
        display.setText("Blue Keywords: " + numBlueKeywords + "     Red Keywords: " + numRedKeywords);
    }
    
    @Override
    public void remove(int offset, int length) throws BadLocationException
    {
        String txt = getText(0, getLength());
        if(txt.charAt(offset) == '"')
        {
            super.remove(offset,length);
            insertString(-1, getText(0, getLength()), blackColor);
            return;
        }
        int start = firstNonwordChar(txt, offset);
        int startRed = firstNonkeyChar(txt, offset);
        if(start < 0 && startRed < 0 && offset == 0 && (length == txt.length()))
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
        if(isInString(txt, offset))
            return;
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
        
        //In addition to the previous word/key, we need to know the right side of what was deleted
        //in case two keywords are being seperated by the character that was deleted
        String rightBlue = txt.substring(offset, afterIndex);
        String rightRed = txt.substring(offset, afterIndexRed);
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
            if(rightBlue.matches(blueKeywords) || wordPrevious.matches("(\\W)*(" + blueKeywords + ")"))
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
            //If both sides match blue keywords, that means we need to decrement twice
            if(wordPrevious.matches(blueKeywords) && rightBlue.matches(blueKeywords))
                numBlueKeywords--;
            setCharacterAttributes(beforeIndex, afterIndex - beforeIndex, blackColor, false);
        }  
        //check to see if we found a red keyword or not, then change it if we did
        if(i != redKeywords.length)
        {
            if(rightRed.matches(redKeyString) || keyPrevious.matches(redKeyString))
                numRedKeywords--;
            /*
            for(i = 0; i < redKeywords.length; i++)
            {
                if(rightRed.matches("(\\s)* + (" + redKeywords[i] + ")") || keyPrevious.matches("(\\s)* + (" + redKeywords[i] + ")"))
                {
                    numRedKeywords--;
                    break;
                }   
            }
            */
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
            //if both sides match red keywords, we need to decrement again
            if(keyPrevious.matches(redKeyString) && rightRed.matches(redKeyString))
                numRedKeywords--;
            setCharacterAttributes(beforeIndexRed, afterIndexRed - beforeIndexRed, blackColor, false);
        }
        
        //Display the number of blue and red keywords
        display.setText("Blue Keywords: " + numBlueKeywords + "     Red Keywords: " + numRedKeywords);
    }
    
    public int getNumRedKeywords(){return numRedKeywords;}
    public int getNumBlueKeywords(){return numBlueKeywords;}
}