package uk.markausten;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the Searchable interface that searches a List of String objects.
 * This implementation searches only the beginning of the words, and is not be optimized
 * for very large Lists.
 *
 * @author G. Cope
 */
public class DatabaseStringSearchable implements Searchable<String, String>
{
    @Override
    public Collection<String> search(String value)
    {
        List<String> matching = new ArrayList<>();

        if (value.length() > 2)
        {
            Database db = new Database();

            for (Object[] s : db.searchSystem(value))
            {
                matching.add((String)s[0]);
            }
        }

        return matching;
    }
}
