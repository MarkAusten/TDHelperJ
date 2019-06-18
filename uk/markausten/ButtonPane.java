package uk.markausten;

import javax.swing.*;
import java.awt.*;

class ButtonPane extends JPanel
{
    private MainPanel ui;
    private JButton btnStart = new JButton(Constants.BUTTON_CAPTION_START);
    private JLabel lblSource = new JLabel(Constants.LABEL_CAPTION_SOURCE);
    private AutoCompleteJComboBox cboSource = new AutoCompleteJComboBox(new DatabaseStringSearchable());
    private JLabel lblDestination = new JLabel(Constants.LABEL_CAPTION_DESTINATION);
    private AutoCompleteJComboBox cboDestination = new AutoCompleteJComboBox(new DatabaseStringSearchable());

    ButtonPane(MainPanel ui)
    {
        this.ui = ui;

        initGui();
        populateDropDownLists();
    }

    /**
     * Populate the drop down lists with the marked systems/stations and recently visited.
     */
    private void populateDropDownLists()
    {
        String markedSystems = TDGUI.settings.markedSystems;

        if (markedSystems != null && !markedSystems.isEmpty())
        {
            cboDestination.addItem("");
            cboSource.addItem("");

            for(String item: markedSystems.split(","))
            {
                cboDestination.addItem(item);
                cboSource.addItem(item);
            }
        }
    }

    /**
     * Add the GUI components to the pane.
     */
    private void initGui()
    {
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        add(lblSource, BorderLayout.LINE_START);
        add(cboSource, BorderLayout.LINE_START);

        add(lblDestination, BorderLayout.LINE_START);
        add(cboDestination, BorderLayout.LINE_START);

        add(btnStart, BorderLayout.SOUTH);

        Dimension preferredDimension = new Dimension(300, 26);

        cboSource.setPreferredSize(preferredDimension);
        cboSource.setMaximumSize(cboSource.getPreferredSize());

        cboDestination.setPreferredSize(preferredDimension);
        cboDestination.setMaximumSize(cboDestination.getPreferredSize());

        btnStart.addActionListener(e -> {
            switch (btnStart.getText())
            {
                case Constants.BUTTON_CAPTION_START:
                    ui.startProcessing();
                    break;

                case Constants.BUTTON_CAPTION_CANCEL:
                    ui.cancelProcessing();
                    break;

                case Constants.BUTTON_CAPTION_SAVE_SETTINGS:
                    ui.saveSettings();
                    break;
            }
        });

        btnStart.setPreferredSize(new Dimension(100, 26));
        btnStart.setMaximumSize(btnStart.getPreferredSize());
    }

    /**
     * @return The destination system/station
     */
    String getDestination()
    {
        return removeMarkedStationCharacters(cboDestination);
    }

    /**
     * @return The destination system/station
     */
    String getSource()
    {
        return removeMarkedStationCharacters(cboSource);
    }

    /**
     * @param combobox The combo box with the system/station.
     * @return The selected system/station, if any, with any marked station identifiers removed.
     */
    private String removeMarkedStationCharacters(AutoCompleteJComboBox combobox)
    {
        String item = (String)combobox.getSelectedItem();

        if (item != null && ! item.isEmpty())
        {
            item = item.replace("!!", "");
        }

        return item;
    }

    /**
     * @param mode Set the enabled mode of the start button.
     */
    void setStartButtonEnabled(boolean mode)
    {
        btnStart.setEnabled(mode);
    }

    /**
     * Set the button state according to the current operating mode.
     *
     * @param mode The current operating mode.
     */
    void setButtonMode(String mode)
    {
        switch (mode.toLowerCase())
        {
            case Constants.REQUIRED_BUTTON_MODE_CANCEL:
                SwingUtilities.invokeLater(() -> btnStart.setText(Constants.BUTTON_CAPTION_CANCEL));

                break;

            case Constants.REQUIRED_BUTTON_MODE_SETTINGS:
                SwingUtilities.invokeLater(() -> btnStart.setText(Constants.BUTTON_CAPTION_SAVE_SETTINGS));

                break;

            default:
                SwingUtilities.invokeLater(() -> {
                    btnStart.setText(Constants.BUTTON_CAPTION_START);
                    setStartButtonEnabled(true);
                });

                break;
        }
    }
}
