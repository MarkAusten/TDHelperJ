package uk.markausten;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ToggleField extends JTextField
{
    private String allowed;

    ToggleField()
    {
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                checkChange(e);
            }
        });
    }

    public String getAllowed()
    {
        return allowed;
    }

    public void setAllowed(String allowed)
    {
        this.allowed = allowed;
    }

    private void checkChange(KeyEvent e)
    {
        JTextField field = (JTextField) e.getSource();

        String text = field.getText()
                           .toUpperCase();
        StringBuilder sb = new StringBuilder();

        for (char c : allowed.toCharArray())
        {
            if (Utils.countCharacterInString(text, c) == 1)
            {
                sb.append(c);
            }
        }

        field.setText(sb.toString());
    }
}
