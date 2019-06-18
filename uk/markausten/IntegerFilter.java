package uk.markausten;

public class IntegerFilter extends BaseFilter
{
    /**
     * Determine if the character should remain in the String.
     *
     * @param c The character in question
     * @return true if it's valid, false if it should be removed.
     */
    public boolean accept(final char c)
    {
        return Character.isDigit(c) || c == '-' || c == ',';
    }
}