package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class ShipPanel extends BaseCriteriaPanel
{
    private MainPanel ui;
    private JLabel lblCapacity = new JLabel(Constants.LABEL_CAPTION_CAPACITY);
    private JLabel lblCredits = new JLabel(Constants.LABEL_CAPTION_CREDITS);
    private JLabel lblInsurance = new JLabel(Constants.LABEL_CAPTION_INSURANCE);
    private JLabel lblLaden = new JLabel(Constants.LABEL_CAPTION_LADEN);
    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblSelectedShip = new JLabel(Constants.LABEL_CAPTION_SELECTED_SHIP);
    private JLabel lblUnladen = new JLabel(Constants.LABEL_CAPTION_UNLADEN);

    private IntegerField txtCapacity = new IntegerField();
    private IntegerField txtCredits = new IntegerField();
    private IntegerField txtInsurance = new IntegerField();
    private NumberField txtLaden = new NumberField();
    private PadField txtPads = new PadField();
    private JTextField txtSelectedShip = new JTextField();
    private NumberField txtUnladen = new NumberField();

    /**
     * constructor.
     */
    public ShipPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
    }

    private void addComponent(
            Component component,
            GridBagConstraints gbc,
            int row,
            int column)
    {
        gbc.gridx = column;
        gbc.gridy = row;

        gbc.weightx = component instanceof JTextField
                ? 1
                : 0;

        this.add(component, gbc);
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblLaden, 0, 0, "WEST");
        addComponentToPanel(panel, txtLaden, 1, 0, "WEST");

        addComponentToPanel(panel, lblUnladen, 2, 0, "WEST");
        addComponentToPanel(panel, txtUnladen, 3, 0, "WEST");

        addComponentToPanel(panel, lblCapacity, 4, 0, "WEST");
        addComponentToPanel(panel, txtCapacity, 5, 0, "WEST");

        addComponentToPanel(panel, lblPads, 6, 0, "WEST");
        addComponentToPanel(panel, txtPads, 7, 0, "WEST");

        return panel;
    }

    public List<String> getCommand(List<String> command)
    {
        Utils.addStringParameter(txtCredits.getText().replace(",", ""), "--cr", command);
        Utils.addStringParameter(txtCapacity.getText(), "--cap", command);
        Utils.addStringParameter(txtLaden.getText(), "--ly", command);
        Utils.addStringParameter(txtUnladen.getText(), "--empty", command);
        Utils.addStringParameter(txtPads.getText(), "--pad", command);

        return command;
    }

    String getInsurance()
    {
        return txtInsurance.getText().replace(",", "");
    }

    /**
     * Set up the panel.
     */
    private void initGui()
    {
        setColumnWidths();

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        addComponent(lblSelectedShip, gbc, 0, 0);

        gbc.gridwidth = 3;
        addComponent(txtSelectedShip, gbc, 0, 1);

        gbc.gridwidth = 1;
        addComponent(lblCredits, gbc, 0, 4);
        addComponent(txtCredits, gbc, 0, 5);

        addComponent(lblInsurance, gbc, 0, 6);
        addComponent(txtInsurance, gbc, 0, 7);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(createPanel1(), gbc);

        Utils.rightAlignTextIn(this);
    }

    void populateGuiFromSettings()
    {
        setFloatSetting(txtLaden, TDGUI.settings.shipLaden);
        setFloatSetting(txtUnladen, TDGUI.settings.shipUnladen);

        setIntegerSetting(txtInsurance, TDGUI.settings.shipInsurance);
        setLongSetting(txtCredits, TDGUI.settings.commanderCredits);
        setIntegerSetting(txtCapacity, TDGUI.settings.shipCapacity);

        setStringSetting(txtPads, TDGUI.settings.shipPads);
        setStringSetting(txtSelectedShip, TDGUI.settings.shipSelected);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.shipCapacity = Utils.convertStringToInt(txtCapacity.getText());
        TDGUI.settings.commanderCredits = Utils.convertStringToLong(txtCredits.getText());
        TDGUI.settings.shipInsurance = Utils.convertStringToInt(txtInsurance.getText());
        TDGUI.settings.shipLaden = Utils.convertStringToFloat(txtLaden.getText());
        TDGUI.settings.shipUnladen = Utils.convertStringToFloat(txtUnladen.getText());
        TDGUI.settings.shipPads = txtPads.getText();
        TDGUI.settings.shipSelected = txtSelectedShip.getText();
    }

    private void setColumnWidths()
    {
        txtLaden.setColumns(4);
        txtUnladen.setColumns(4);
        txtCapacity.setColumns(4);
        txtPads.setColumns(3);
    }
}
