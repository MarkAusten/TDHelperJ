package uk.markausten;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class Utils
{
    static List<String> addBooleanParameter(
            boolean value,
            String name,
            List<String> command)
    {
        if (value)
        {
            command.add(name);
        }

        return command;
    }

    /**
     * Installs a listener to receive notification when the text of any
     * {@code JTextComponent} is changed. Internally, it installs a
     * {@link DocumentListener} on the text component's {@link Document},
     * and a {@link PropertyChangeListener} on the text component to detect
     * if the {@code Document} itself is replaced.
     *
     * @param text           any text component, such as a {@link JTextField}
     *                       or {@link JTextArea}
     * @param changeListener a listener to receieve {@link ChangeEvent}s
     *                       when the text is changed; the source object for the events
     *                       will be the text component
     * @throws NullPointerException if either parameter is null
     */
    public static void addChangeListener(
            JTextComponent text,
            ChangeListener changeListener)
    {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);

        DocumentListener dl = new DocumentListener()
        {
            private int lastChange = 0;
            private int lastNotifiedChange = 0;

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange)
                    {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                changedUpdate(e);
            }
        };

        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document) e.getOldValue();
            Document d2 = (Document) e.getNewValue();

            if (d1 != null)
            {
                d1.removeDocumentListener(dl);
            }

            if (d2 != null)
            {
                d2.addDocumentListener(dl);
            }

            dl.changedUpdate(null);
        });

        Document d = text.getDocument();

        if (d != null)
        {
            d.addDocumentListener(dl);
        }
    }

    static List<String> addFloatParameter(
            String value,
            String name,
            List<String> command)
    {
        float newValue = convertStringToFloat(value);

        if (newValue > 0f)
        {
            command.add(name + "=" + value);
        }

        return command;
    }

    static List<String> addIntegerParameter(
            String value,
            String name,
            List<String> command)
    {
        value = value.replace(",", "");

        int newValue = convertStringToInt(value);

        if (newValue > 0)
        {
            command.add(name + "=" + value);
        }

        return command;
    }

    static List<String> addLongParameter(
            String value,
            String name,
            List<String> command)
    {
        value = value.replace(",", "");

        long newValue = convertStringToLong(value);

        if (newValue > 0)
        {
            command.add(name + "=" + value);
        }

        return command;
    }

    static List<String> addQuotedStringParameter(
            String value,
            String name,
            List<String> command)
    {
        if (!value.isEmpty())
        {
            if (name.isEmpty() || name.isBlank())
            {
                command.add("\"" + value + "\"");
            }
            else
            {
                command.add(name + "=\"" + value + "\"");
            }
        }

        return command;
    }

    static List<String> addStringParameter(
            String value,
            String name,
            List<String> command)
    {
        if (!value.isEmpty())
        {
            if (name.isEmpty() || name.isBlank())
            {
                command.add(value);
            }
            else
            {
                command.add(name + "=" + value);
            }
        }

        return command;
    }

    static void checkDefaultNetLogPath()
    {
        File testForFolder = new File(Constants.STRING_LOG_Folder_1);

        boolean found = testForFolder.exists();

        if (!found)
        {
            // Locate the user's folder.
            String path = FileSystemView
                    .getFileSystemView()
                    .getDefaultDirectory()
                    .getPath();

            path = path.substring(0, path.lastIndexOf(File.separator));

            testForFolder = new File(path + "\\AppData\\Local\\Frontier_Developments\\Products\\elite-dangerous-64");
            found = testForFolder.exists();
        }

        if (!found)
        {
            testForFolder = new File(Constants.STRING_LOG_Folder_2);

            found = testForFolder.exists();
        }

        if (!found)
        {
            testForFolder = new File(Constants.STRING_LOG_Folder_3);

            found = testForFolder.exists();
        }

        if (found)
        {
            String folder = testForFolder.getPath();
            File testForFile = new File(folder + "\\AppConfig.xml");

            if (testForFile.exists())
            {
                TDGUI.settings.settingsNetLogPath = folder;
            }
        }
    }

    static float convertStringToFloat(String value)
    {
        float result = 0f;

        try
        {
            result = Float.parseFloat(value);
        }
        catch (NumberFormatException e)
        {
            LogClass.log.severe("NumberFormatException: " + e.getMessage());
        }

        return result;
    }

    static int convertStringToInt(String value)
    {
        int result = 0;

        if (value != null && !value.isEmpty())
        {
            try
            {
                result = Integer.parseInt(value.replace(",", ""));
            }
            catch (NumberFormatException e)
            {
                LogClass.log.severe("NumberFormatException: " + e.getMessage());
            }
        }

        return result;
    }

    static long convertStringToLong(String value)
    {
        long result = 0;

        if (value != null && !value.isEmpty())
        {
            try
            {
                result = Long.parseLong(value.replace(",", ""));
            }
            catch (NumberFormatException e)
            {
                LogClass.log.severe("NumberFormatException: " + e.getMessage());
            }
        }
        return result;
    }

    static long countCharacterInString(
            String text,
            char c)
    {
        return text
                .chars()
                .filter(ch -> ch == c)
                .count();
    }

    /**
     * @return The current OS type.
     */
    static String determinOsType()
    {
        String result = "";

        String os = System
                .getProperty("os.name")
                .toLowerCase();

        if (os.contains("win"))
        {
            result = "Windows";
        }
        else if (os.contains("mac os x"))
        {
            result = "MacOS";
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux"))
        {
            result = "*Nix";
        }

        return result;
    }

    static String downloadString(URL url)
    {
        StringBuilder text = new StringBuilder();
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                text.append(inputLine);
            }

            in.close();
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }

        return text.toString();
    }

    static void enableAllCheckboxes(
            Container parent,
            boolean state)
    {
        for (Component c : parent.getComponents())
        {
            if (c instanceof JCheckBox)
            {
                ((JCheckBox) c).setEnabled(state);
            }
            else if (c instanceof Container)
            {
                enableAllCheckboxes((Container) c, state);
            }
        }

    }

    /**
     * @param shipType The internal ship type as specified by FDev.
     * @return The ship json object.
     */
    static JSONObject getBaseShipData(String shipType)
    {
        JSONObject ship = null;

        try
        {
            // Get a reference to the base ships resource file.
            InputStream resource = TDGUI.class.getResourceAsStream("/base_ships.json");

            if (resource != null)
            {
                // Look up the internal ship type in the  resource and get the game ship type.
                JSONObject json = (JSONObject) (new JSONParser().parse(new InputStreamReader(resource)));
                ship = Utils.getJsonObject(json, "ships." + shipType);
            }
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }

        return ship;
    }

    static JSONObject getJsonObject(
            JSONObject json,
            String key)
    {
        JSONObject result = json;

        for (String k : key.split("\\."))
        {
            result = (JSONObject) result.get(k);

            if (result == null)
            {
                break;
            }
        }

        return result;
    }

    /**
     * Right align the numeric text boxes in the container.
     *
     * @param parent The container to be iterated.
     */
    static void rightAlignTextIn(Container parent)
    {
        for (Component c : parent.getComponents())
        {
            if (c instanceof IntegerField || c instanceof NumberField)
            {
                ((JTextField) c).setHorizontalAlignment(SwingConstants.RIGHT);
            }
            else if (c instanceof Container)
            {
                rightAlignTextIn((Container) c);
            }
        }
    }

    static String runProcess(List<String> cmd)
    {
        StringBuilder output = new StringBuilder();

        try
        {
            ProcessBuilder pb = new ProcessBuilder(cmd);

            Process proc = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String s;

            while ((s = stdInput.readLine()) != null)
            {
                output
                        .append(s)
                        .append("\n");
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null)
            {
                output
                        .append(s)
                        .append("\n");
            }
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }

        return output.toString();
    }

    /**
     * Scrolls a {@code scrollPane} all the way up or down.
     *
     * @param scrollPane the scrollPane that we want to scroll up or down
     * @param direction  we scroll up if this is {@link ScrollDirection#UP},
     *                   or down if it's {@link ScrollDirection#DOWN}
     */
    static void scroll(
            JScrollPane scrollPane,
            ScrollDirection direction)
    {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

        // If we want to scroll to the top, set this value to the minimum,
        // else to the maximum
        int topOrBottom = direction == ScrollDirection.UP
                ? verticalBar.getMinimum()
                : verticalBar.getMaximum();

        AdjustmentListener scroller = new AdjustmentListener()
        {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(topOrBottom);
                // We have to remove the listener, otherwise the
                // user would be unable to scroll afterwards
                verticalBar.removeAdjustmentListener(this);
            }
        };

        verticalBar.addAdjustmentListener(scroller);
    }

    static String secondsToHHMMSS(int seconds)
    {
        int secondsLeft = seconds % 3600 % 60;
        int minutes = seconds % 3600 / 60;
        int hours = seconds / 3600;

        return String.format("%02d:%02d:%02d", hours, minutes, secondsLeft);
    }

    static void selectAllCheckboxes(
            Container parent,
            boolean state)
    {
        for (Component c : parent.getComponents())
        {
            if (c instanceof JCheckBox)
            {
                ((JCheckBox) c).setSelected(state);
            }
            else if (c instanceof Container)
            {
                selectAllCheckboxes((Container) c, state);
            }
        }

    }

    static String sortCharactersInString(String text)
    {
        char[] chars = text.toCharArray();

        Arrays.sort(chars);

        return new String(chars);
    }

    /**
     * The enumerator for the Scroll Direction.
     */
    public enum ScrollDirection
    {
        UP, DOWN
    }
}

class TSH extends Thread
{
    StringBuilder sb;
    private InputStream inputStream;
    private volatile String line;

    TSH(
            InputStream inputStream,
            StringBuilder sb)
    {
        this.inputStream = inputStream;
        this.sb = sb;
    }

    public void run()
    {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            while ((line = bufferedReader.readLine()) != null)
            {
                if (line != null)
                {
                    sb.append(line);
                }
            }
        }
        catch (Throwable ioe)
        {
            ioe.printStackTrace();
        }
    }

}