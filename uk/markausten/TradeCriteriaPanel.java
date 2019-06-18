package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class TradeCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    /**
     * constructor.
     */
    public TradeCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("trade");

        ui.addVerbosity(command);

        String source = ui.getSource();
        String destination = ui.getDestination();

        if (source != null && !source.isEmpty() && destination != null && !destination.isEmpty())
        {
            Utils.addStringParameter(source, "", command);
            Utils.addStringParameter(destination, "", command);
        }

        return command;
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_TRADE;
    }
}
