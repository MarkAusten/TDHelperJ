package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BaseCriteriaPanel extends JPanel
{
    public BaseCriteriaPanel()
    {
        super();

        preProcessingHook();
    }

    void addComponentToPanel(
            JPanel panel,
            Component component,
            int column,
            int row,
            String anchor)
    {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.anchor = anchor
                .toLowerCase()
                .equals("east")
                ? GridBagConstraints.EAST
                : GridBagConstraints.WEST;

        panel.add(component, gbc);
    }

    void addDestination(
            String destination,
            List<String> command)
    {
        if (destination != null && !destination.isEmpty())
        {
            Utils.addQuotedStringParameter(destination, "--to", command);
        }
    }

    void addSource(
            String source,
            List<String> command)
    {
        if (source != null && !source.isEmpty())
        {
            Utils.addStringParameter(source, "--from", command);
        }
    }

    public List<String> getCommand(List<String> command)
    {
        return command;
    }

    void populateSettingsFromGui()
    {
    }

    public void postProcessingHook()
    {
    }

    public void preProcessingHook()
    {
    }

    public boolean processCanBeCancelled()
    {
        return true;
    }

    /**
     * @return The required button mode.
     */
    String requiredButtonMode()
    {
        return "";
    }

    void setBooleanSetting(
            JCheckBox component,
            boolean setting)
    {
        component.setSelected(setting);
    }

    void setFloatSetting(
            NumberField component,
            float setting)
    {
        component.setText(Float.toString(setting));
    }

    void setIntegerSetting(
            IntegerField component,
            int setting)
    {
        component.setText(String.format("%,d", setting));
    }

    void setLongSetting(
            IntegerField component,
            long setting)
    {
        component.setText(String.format("%,d", setting));
    }

    void setStringSetting(
            JTextField component,
            String setting)
    {
        component.setText(setting);
    }
}
