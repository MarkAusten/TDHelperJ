package uk.markausten;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.*;

/**
 * JComboBox with an autocomplete drop down menu. This class is hard-coded for String objects, but can be
 * altered into a generic form to allow for any searchable item.
 *
 * @author G. Cope
 */

class AutoCompleteJComboBox extends JComboBox<String>
{
    static final long serialVersionUID = 4321421L;

    private final Searchable<String, String> searchable;

    /**
     * Constructs a new object based upon the parameter searchable
     *
     * @param s searchable string
     */
    AutoCompleteJComboBox(Searchable<String, String> s)
    {
        super();

        this.searchable = s;

        setEditable(true);

        Component c = getEditor().getEditorComponent();

        if (c instanceof JTextComponent)
        {

            final JTextComponent tc = (JTextComponent) c;

            tc.getDocument().addDocumentListener(new DocumentListener()
            {
                @Override
                public void changedUpdate(DocumentEvent arg0)
                {
                }

                @Override
                public void insertUpdate(DocumentEvent arg0)
                {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0)
                {
                    update();
                }

                void update()
                {
                    //perform separately, as listener conflicts between the editing component
                    //and JComboBox will result in an IllegalStateException due to editing
                    //the component when it is locked.
                    SwingUtilities.invokeLater(() -> {
                        List<String> founds = new ArrayList<>(searchable.search(tc.getText()));
                        Set<String> foundSet = new HashSet<>();

                        for (String s1 : founds)
                        {
                            foundSet.add(s1.toLowerCase());
                        }

                        Collections.sort(founds);//sort alphabetically
                        setEditable(false);
                        removeAllItems();

                        //if founds contains the search text, then only add once.
                        if (!foundSet.contains(tc.getText().toLowerCase()))
                        {
                            addItem(tc.getText());
                        }

                        for (String s1 : founds)
                        {
                            addItem(s1);
                        }

                        setEditable(true);
                        setPopupVisible(true);
                        tc.requestFocus();
                    });
                }
            });

            //When the text component changes, focus is gained
            //and the menu disappears. To account for this, whenever the focus
            //is gained by the JTextComponent and it has searchable values, we show the popup.
            tc.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent arg0)
                {
                    if (tc.getText().length() > 0)
                    {
                        setPopupVisible(true);
                        tc.setCaretPosition(tc.getText().length());
                    }
                }

                @Override
                public void focusLost(FocusEvent arg0)
                {
                }
            });
        }
        else
        {
            throw new IllegalStateException("Editing component is not a JTextComponent!");
        }
    }
}
