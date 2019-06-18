package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class OldDataCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblNearLy = new JLabel(Constants.LABEL_CAPTION_NEAR_LY);
    private JLabel lblMaxLs = new JLabel(Constants.LABEL_CAPTION_MAX_LS);
    private JLabel lblResults = new JLabel(Constants.LABEL_CAPTION_RESULTS);
    private JLabel lblAge = new JLabel(Constants.LABEL_CAPTION_AGE);

    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private NumberField txtNearLy = new NumberField();
    private IntegerField txtMaxLs = new IntegerField();
    private IntegerField txtResults = new IntegerField();
    private IntegerField txtAge = new IntegerField();

    private JCheckBox chkShortest = new JCheckBox(Constants.CHECKBOX_CAPTION_SHORTEST_ROUTE);

    /**
     * constructor.
     */
    public OldDataCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkShortest, 0, 0, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblNearLy, 0, 0, "EAST");
        addComponentToPanel(panel, lblAge, 0, 1, "EAST");
        addComponentToPanel(panel, lblMaxLs, 0, 2, "EAST");
        addComponentToPanel(panel, lblResults, 0, 3, "EAST");
        addComponentToPanel(panel, txtNearLy, 1, 0, "WEST");
        addComponentToPanel(panel, txtAge, 1, 1, "WEST");
        addComponentToPanel(panel, txtMaxLs, 1, 2, "WEST");
        addComponentToPanel(panel, txtResults, 1, 3, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblPlanetary, 0, 1, "EAST");
        addComponentToPanel(panel, lblPads, 0, 2, "EAST");
        addComponentToPanel(panel, txtPlanetary, 1, 1, "EAST");
        addComponentToPanel(panel, txtPads, 1, 2, "EAST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("olddata");

        Utils.addBooleanParameter(chkShortest.isSelected(), "--route", command);
        Utils.addFloatParameter(txtNearLy.getText(), "--ly", command);
        Utils.addIntegerParameter(txtAge.getText(), "--min-age", command);
        Utils.addIntegerParameter(txtResults.getText(), "--limit", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);
        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addIntegerParameter(txtMaxLs.getText(), "--ls-max", command);
        Utils.addStringParameter(ui.getSource(), "--near", command);

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

        gbc.gridx = 5;

        this.add(createPanel3(), gbc);

        Utils.rightAlignTextIn(this);
    }

    void populateGuiFromSettings()
    {
        setFloatSetting(txtNearLy, TDGUI.settings.oldDataNearLy);
        setIntegerSetting(txtMaxLs, TDGUI.settings.oldDataMaxLs);
        setIntegerSetting(txtResults, TDGUI.settings.oldDataResults);
        setIntegerSetting(txtAge, TDGUI.settings.oldDataAge);
        setStringSetting(txtPads, TDGUI.settings.oldDataPads);
        setStringSetting(txtPlanetary, TDGUI.settings.oldDataPlanetary);
        setBooleanSetting(chkShortest, TDGUI.settings.oldDataShortest);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.oldDataNearLy = Utils.convertStringToFloat(txtNearLy.getText());
        TDGUI.settings.oldDataMaxLs = Utils.convertStringToInt(txtMaxLs.getText());
        TDGUI.settings.oldDataResults = Utils.convertStringToInt(txtResults.getText());
        TDGUI.settings.oldDataAge = Utils.convertStringToInt(txtAge.getText());
        TDGUI.settings.oldDataPads = txtPads.getText();
        TDGUI.settings.oldDataPlanetary = txtPlanetary.getText();
        TDGUI.settings.oldDataShortest = chkShortest.isSelected();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_OLD_DATA;
    }

    private void setComponentWidths()
    {
        txtResults.setColumns(6);
        txtAge.setColumns(6);

        txtNearLy.setColumns(6);
        txtMaxLs.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);
    }
}
