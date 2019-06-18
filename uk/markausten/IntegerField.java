package uk.markausten;

import javax.swing.*;
import javax.swing.text.PlainDocument;

public class IntegerField extends JTextField
{
    IntegerField()
    {
        PlainDocument doc = (PlainDocument)getDocument();
        doc.setDocumentFilter(new IntegerFilter());
    }
}
