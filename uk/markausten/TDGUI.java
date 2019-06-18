package uk.markausten;

import javax.swing.*;
import java.awt.*;

public class TDGUI
{
        //Create and set up the window.
    public static JFrame frame = new JFrame();
    public static Settings settings = new Settings();

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        MainPanel panel = new MainPanel();

        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));

        // Monitor the frame for size and position changes.
        FrameMonitor.registerFrame(frame, TDGUI.class.getName(), frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());

        frame.setVisible(true);
    }
}
