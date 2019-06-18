package uk.markausten;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class BaseFilter extends DocumentFilter
{
    /**
     * Determine if the character should remain in the String.
     *
     * @param c The character in question
     * @return true if it's valid, false if it should be removed.
     */
    public boolean accept(final char c)
    {
        return true;
    }

    @Override
    public void insertString(
            FilterBypass fb,
            int offset,
            String text,
            AttributeSet attr) throws BadLocationException
    {
        super.insertString(fb, offset, revise(text).toUpperCase(), attr);
    }

    @Override
    public void replace(
            FilterBypass fb,
            int offset,
            int length,
            String text,
            AttributeSet attrs) throws BadLocationException
    {
        super.replace(fb, offset, length, revise(text), attrs);
    }

    private String revise(String text)
    {
        StringBuilder builder = new StringBuilder(text);
        int index = 0;

        while (index < builder.length())
        {
            if (accept(builder.charAt(index)))
            {
                index++;
            }
            else
            {
                // Don't increment index here, or you'll skip the next character!
                builder.deleteCharAt(index);
            }
        }

        return builder.toString();
    }
}