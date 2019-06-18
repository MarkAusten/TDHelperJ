package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class NavigationCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblJumpRangeLy = new JLabel(Constants.LABEL_CAPTION_JUMP_RANGE);
    private JLabel lblRefuel = new JLabel(Constants.LABEL_CAPTION_REFUEL_JUMPS);
    private JLabel lblAvoid = new JLabel(Constants.LABEL_CAPTION_AVOID);
    private JLabel lblVia = new JLabel(Constants.LABEL_CAPTION_VIA);

    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private NumberField txtJumpRangeLy = new NumberField();
    private IntegerField txtRefuel = new IntegerField();
    private JTextField txtAvoid = new JTextField();
    private JTextField txtVia = new JTextField();

    private JCheckBox chkStations = new JCheckBox(Constants.CHECKBOX_CAPTION_STATIONS);
    private JCheckBox chkFull = new JCheckBox(Constants.CHECKBOX_CAPTION_FULL);

    /**
     * constructor.
     */
    public NavigationCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblAvoid, 0, 0, "EAST");
        addComponentToPanel(panel, lblVia, 0, 1, "EAST");
        addComponentToPanel(panel, txtAvoid, 1, 0, "WEST");
        addComponentToPanel(panel, txtVia, 1, 1, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkStations, 0, 0, "WEST");
//        addComponentToPanel(panel, chkFull, 0, 1, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblJumpRangeLy, 0, 1, "EAST");
        addComponentToPanel(panel, lblRefuel, 0, 2, "EAST");
        addComponentToPanel(panel, txtJumpRangeLy, 1, 1, "EAST");
        addComponentToPanel(panel, txtRefuel, 1, 2, "EAST");

        return panel;
    }

    private JPanel createPanel4()
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
        command.add("nav");

        Utils.addFloatParameter(txtJumpRangeLy.getText(), "--ly-per", command);
        Utils.addStringParameter(ui.getSource(), "", command);
        Utils.addStringParameter(ui.getDestination(), "", command);
        Utils.addStringParameter(txtAvoid.getText(), "--avoid", command);
        Utils.addStringParameter(txtVia.getText(), "--via", command);
        Utils.addBooleanParameter(chkStations.isSelected(), "-S", command);
//        Utils.addBooleanParameter(chkFull.isSelected(), "--full", command);
        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);

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

        gbc.gridx = 6;

        this.add(createPanel4(), gbc);

        Utils.rightAlignTextIn(this);
    }

    void populateGuiFromSettings()
    {
        setFloatSetting(txtJumpRangeLy, TDGUI.settings.navJumpRangeLy);
        setIntegerSetting(txtRefuel, TDGUI.settings.navRefuel);
        setStringSetting(txtAvoid, TDGUI.settings.navAvoid);
        setStringSetting(txtVia, TDGUI.settings.navVia);
        setStringSetting(txtPads, TDGUI.settings.navPads);
        setStringSetting(txtPlanetary, TDGUI.settings.navPlanetary);
        setBooleanSetting(chkStations, TDGUI.settings.navStations);
        setBooleanSetting(chkFull, TDGUI.settings.navFull);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.navJumpRangeLy = Utils.convertStringToFloat(txtJumpRangeLy.getText());
        TDGUI.settings.navRefuel = Utils.convertStringToInt(txtRefuel.getText());
        TDGUI.settings.navAvoid = txtAvoid.getText();
        TDGUI.settings.navVia = txtVia.getText();
        TDGUI.settings.navPads = txtPads.getText();
        TDGUI.settings.navPlanetary = txtPlanetary.getText();
        TDGUI.settings.navStations = chkStations.isSelected();
        TDGUI.settings.navFull = chkFull.isSelected();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_NAVIGATION;
    }

    private void setComponentWidths()
    {
        txtAvoid.setColumns(32);
        txtVia.setColumns(32);

        txtJumpRangeLy.setColumns(6);
        txtRefuel.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);
    }
}
