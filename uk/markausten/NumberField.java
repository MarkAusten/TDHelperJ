package uk.markausten;

import javax.swing.*;
import javax.swing.text.PlainDocument;

public class NumberField extends JTextField
{
    NumberField()
    {
        PlainDocument doc = (PlainDocument)getDocument();
        doc.setDocumentFilter(new DigitFilter());
    }
}
