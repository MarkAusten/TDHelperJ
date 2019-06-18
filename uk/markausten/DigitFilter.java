package uk.markausten;

public class DigitFilter extends BaseFilter
{
    /**
     * Determine if the character should remain in the String. You may
     * override this to get any matching criteria you want.
     *
     * @param c The character in question
     * @return true if it's valid, false if it should be removed.
     */
    @Override
    public boolean accept(final char c)
    {
        return Character.isDigit(c) || c == '.' || c == '-' || c == ',';
    }
}