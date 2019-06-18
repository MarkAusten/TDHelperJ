package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class StationCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblHint = new JLabel(Constants.LABEL_CAPTION_STATION_HINT);

    /**
     * constructor.
     */
    public StationCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGUI();
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("station");

        ui.addVerbosity(command);

        Utils.addStringParameter(ui.getSource(), "", command);

        return command;
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
    {
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        this.add(lblHint, gbc);
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_STATION;
    }
}
