package uk.markausten;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel
{
    private JLabel statusTextLeft = new JLabel(" ", SwingConstants.LEFT);
    private JLabel statusTextMiddle = new JLabel(" ", SwingConstants.CENTER);
    private JLabel statusTextRight = new JLabel(" ", SwingConstants.RIGHT);

    public StatusBar()
    {
        addComponentsToPane();
    }

    /**
     * Add the GUI components to the pane.
     */
    void addComponentsToPane()
    {
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));

        setLayout(new GridLayout(1, 3));

        add(statusTextLeft);
        add(statusTextMiddle);
        add(statusTextRight);
    }

    public void setLeftStatus(String message)
    {
        statusTextLeft.setText(message);
    }

    public void setMiddleStatus(String message)
    {
        statusTextMiddle.setText(message);
    }

    public void setRightStatus(String message)
    {
        statusTextRight.setText(message);
    }
}
