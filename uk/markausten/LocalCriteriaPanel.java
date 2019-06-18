package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class LocalCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblMaxLy = new JLabel(Constants.LABEL_CAPTION_MAX_LY);

    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private NumberField txtMaxLy = new NumberField();

    private JCheckBox chkBlackMarket = new JCheckBox(Constants.CHECKBOX_CAPTION_BLACK_MARKET);
    private JCheckBox chkRearm = new JCheckBox(Constants.CHECKBOX_CAPTION_REARM);
    private JCheckBox chkRefuel = new JCheckBox(Constants.CHECKBOX_CAPTION_REFUEL);
    private JCheckBox chkRepair = new JCheckBox(Constants.CHECKBOX_CAPTION_REPAIR);
    private JCheckBox chkOutfitting = new JCheckBox(Constants.CHECKBOX_CAPTION_OUTFITTING);
    private JCheckBox chkStations = new JCheckBox(Constants.CHECKBOX_CAPTION_STATIONS);
    private JCheckBox chkShipyard = new JCheckBox(Constants.CHECKBOX_CAPTION_SHIPYARD);
    private JCheckBox chkTrading = new JCheckBox(Constants.CHECKBOX_CAPTION_TRADING);

    /**
     * constructor.
     */
    public LocalCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkBlackMarket, 0, 0, "WEST");
        addComponentToPanel(panel, chkRearm, 0, 1, "WEST");
        addComponentToPanel(panel, chkRefuel, 0, 2, "WEST");
        addComponentToPanel(panel, chkRepair, 0, 3, "WEST");
        addComponentToPanel(panel, chkOutfitting, 1, 0, "WEST");
        addComponentToPanel(panel, chkStations, 1, 1, "WEST");
        addComponentToPanel(panel, chkShipyard, 1, 2, "WEST");
        addComponentToPanel(panel, chkTrading, 1, 3, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblMaxLy, 0, 0, "EAST");
        addComponentToPanel(panel, lblPlanetary, 0, 1, "EAST");
        addComponentToPanel(panel, lblPads, 0, 2, "EAST");
        addComponentToPanel(panel, txtMaxLy, 1, 0, "EAST");
        addComponentToPanel(panel, txtPlanetary, 1, 1, "EAST");
        addComponentToPanel(panel, txtPads, 1, 2, "EAST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("local");

        Utils.addBooleanParameter(chkBlackMarket.isSelected(), "--bm", command);
        Utils.addBooleanParameter(chkRearm.isSelected(), "--rearm", command);
        Utils.addBooleanParameter(chkRefuel.isSelected(), "--refuel", command);
        Utils.addBooleanParameter(chkRepair.isSelected(), "--repair", command);
        Utils.addBooleanParameter(chkOutfitting.isSelected(), "--outfitting", command);
        Utils.addBooleanParameter(chkShipyard.isSelected(), "--shipyard", command);
        Utils.addBooleanParameter(chkStations.isSelected(), "--stations", command);
        Utils.addBooleanParameter(chkTrading.isSelected(), "--trading", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);
        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addFloatParameter(txtMaxLy.getText(), "--ly", command);

        Utils.addStringParameter(ui.getSource(), "", command);

        ui.addVerbosity(command);

        return command;
    }

    /**
     * Set up the panel.
     */
    private void initGui()
    {
        this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        setLayout(new GridBagLayout());

        setComponentWidths();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        this.add(createPanel1(), gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;

        this.add(createPanel2(), gbc);
        
        Utils.rightAlignTextIn(this);
    }

    void populateGuiFromSettings()
    {
        setBooleanSetting(chkBlackMarket, TDGUI.settings.localBlackMarket);
        setBooleanSetting(chkOutfitting, TDGUI.settings.localOutfitting);
        setBooleanSetting(chkRearm, TDGUI.settings.localRearm);
        setBooleanSetting(chkRefuel, TDGUI.settings.localRefuel);
        setBooleanSetting(chkRepair, TDGUI.settings.localRepair);
        setBooleanSetting(chkShipyard, TDGUI.settings.localShipyard);
        setBooleanSetting(chkStations, TDGUI.settings.localStations);
        setBooleanSetting(chkTrading, TDGUI.settings.localTrading);
        setFloatSetting(txtMaxLy, TDGUI.settings.localMaxLy);
        setStringSetting(txtPads, TDGUI.settings.localPads);
        setStringSetting(txtPlanetary, TDGUI.settings.localPlanetary);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.localBlackMarket = chkBlackMarket.isSelected();
        TDGUI.settings.localMaxLy = Utils.convertStringToFloat(txtMaxLy.getText());
        TDGUI.settings.localOutfitting = chkOutfitting.isSelected();
        TDGUI.settings.localPads = txtPads.getText();
        TDGUI.settings.localPlanetary = txtPlanetary.getText();
        TDGUI.settings.localRearm = chkRearm.isSelected();
        TDGUI.settings.localRefuel = chkRefuel.isSelected();
        TDGUI.settings.localRepair = chkRepair.isSelected();
        TDGUI.settings.localShipyard = chkShipyard.isSelected();
        TDGUI.settings.localStations = chkStations.isSelected();
        TDGUI.settings.localTrading = chkTrading.isSelected();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_LOCAL;
    }

    private void setComponentWidths()
    {
        txtMaxLy.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);
    }
}
