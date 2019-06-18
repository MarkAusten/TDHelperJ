package uk.markausten;

import javax.swing.*;
import java.awt.*;

class OutputPanel extends JPanel
{
    private MainPanel ui;
    private JTextArea txtOutput = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(txtOutput);

    private boolean inProgressBar = false;

    NonBlockingQueue<String> queue = new NonBlockingQueue<>();

    /**
     * constructor.
     */
    OutputPanel(MainPanel ui)
    {
        this.ui = ui;
        initGUI();
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
    {
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        setLayout(new BorderLayout());

        txtOutput.setFont(new Font("monospaced", Font.PLAIN, 12));
        txtOutput.setLineWrap(true);
        txtOutput.setEditable(false);

        scrollPane.setHorizontalScrollBar(null);

        add(scrollPane);
    }

    /**
     * Update the output with any queued messages.
     */
    void updateOutput()
    {
        // Do nothing if there is nothing to show.
        while (!queue.isEmpty())
        {
            // Get the next message and determine how it should be handled.
            String text = queue.get();

            if (text.length() > 0 && text.charAt(0) == '[')
            {
                // The first character is a '[' so this is a progress message.
                if (inProgressBar)
                {
                    // At least one progress message has already been received so overwrite the last line.
                    overwriteLastLine(text);
                }
                else
                {
                    // Flag that the output is now a progress message and append the message to the existing text.
                    inProgressBar = true;

                    appendLine(text);
                }
            }
            else
            {
                // If the previous message was a progress message, then output a new line and flag that the
                // messages are no longer progress messages.
                if (inProgressBar)
                {
                    inProgressBar = false;
                    txtOutput.append("\n");
                }

                // Append the message to the existing output.
                appendLine(text);
            }

            // Ensure that the last message is displayed.
            Utils.scroll(scrollPane, Utils.ScrollDirection.DOWN);
        }
    }

    void clearOutput()
    {
        txtOutput.setText("");
        txtOutput.updateUI();
    }

    /**
     * Overwrite the last line of the output.
     *
     * @param text The message to be displayed.
     */
    private void overwriteLastLine(String text)
    {
        String[] lines = txtOutput.getText().split("\n");
        lines[lines.length - 1] = text;
        txtOutput.setText(String.join("\n", lines));
    }

    /**
     * Append the message to the bottom of the output display.
     *
     * @param text The message to be displayed.
     */
    private void appendLine(String text)
    {
        txtOutput.append(text + "\n");
    }
}
