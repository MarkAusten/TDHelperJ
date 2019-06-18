package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class SellCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblAbove = new JLabel(Constants.LABEL_CAPTION_ABOVE);
    private JLabel lblAvoid = new JLabel(Constants.LABEL_CAPTION_AVOID);
    private JLabel lblBelow = new JLabel(Constants.LABEL_CAPTION_BELOW);
    private JLabel lblCommodity = new JLabel(Constants.LABEL_CAPTION_COMMODITY);
    private JLabel lblNearLy = new JLabel(Constants.LABEL_CAPTION_NEAR_LY);
    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblResults = new JLabel(Constants.LABEL_CAPTION_RESULTS);
    private JLabel lblSort = new JLabel(Constants.LABEL_CAPTION_SORT_RESULTS_BY);

    private IntegerField txtAbove = new IntegerField();
    private JTextField txtAvoid = new JTextField();
    private IntegerField txtBelow = new IntegerField();
    private JTextField txtCommodity = new JTextField();
    private NumberField txtNearLy = new NumberField();
    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private IntegerField txtResults = new IntegerField();

    private JCheckBox chkBlackMarket = new JCheckBox(Constants.CHECKBOX_CAPTION_BLACK_MARKET);

    private JButton btnSelection = new JButton("...");

    private JRadioButton optPrice = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_PRICE);
    private JRadioButton optSupply = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_SUPPLY);

    private ButtonGroup optGroup = new ButtonGroup();

    /**
     * constructor.
     */
    public SellCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGUI();
        populateGuiFromSettings();
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblAvoid, 0, 0, "EAST");
        addComponentToPanel(panel, lblCommodity, 0, 1, "EAST");
        addComponentToPanel(panel, txtAvoid, 1, 0, "WEST");
        addComponentToPanel(panel, txtCommodity, 1, 1, "WEST");
        addComponentToPanel(panel, btnSelection, 4, 1, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkBlackMarket, 0, 1, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblResults, 0, 0, "EAST");
        addComponentToPanel(panel, lblAbove, 0, 2, "EAST");
        addComponentToPanel(panel, lblBelow, 0, 3, "EAST");
        addComponentToPanel(panel, txtResults, 1, 0, "EAST");
        addComponentToPanel(panel, txtAbove, 1, 2, "EAST");
        addComponentToPanel(panel, txtBelow, 1, 3, "EAST");

        return panel;
    }

    private JPanel createPanel4()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblNearLy, 0, 0, "EAST");
        addComponentToPanel(panel, lblPlanetary, 0, 1, "EAST");
        addComponentToPanel(panel, lblPads, 0, 2, "EAST");
        addComponentToPanel(panel, txtNearLy, 1, 0, "EAST");
        addComponentToPanel(panel, txtPlanetary, 1, 1, "EAST");
        addComponentToPanel(panel, txtPads, 1, 2, "EAST");

        return panel;
    }

    private JPanel createPanel5()
    {
        optGroup.add(optPrice);
        optGroup.add(optSupply);

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblSort, 0, 0, "EAST");
        addComponentToPanel(panel, optPrice, 2, 0, "EAST");
        addComponentToPanel(panel, optSupply, 3, 0, "EAST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("sell");

        String source = ui.getSource();

        if (source != null && !source.isEmpty())
        {
            Utils.addStringParameter(source, "--near", command);
            Utils.addFloatParameter(txtNearLy.getText(), "--ly", command);
        }

        Utils.addIntegerParameter(txtAbove.getText(), "--gt", command);
        Utils.addIntegerParameter(txtBelow.getText(), "--lt", command);

        Utils.addBooleanParameter(chkBlackMarket.isSelected(), "--bm", command);

        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);

        Utils.addIntegerParameter(txtResults.getText(), "--limit", command);

        Utils.addStringParameter(txtAvoid.getText(), "--avoid", command);

        if (optPrice.isSelected())
        {
            command.add("-P");
        }

        Utils.addStringParameter(txtCommodity.getText(), "", command);

        ui.addVerbosity(command);

        return command;
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
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

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        this.add(createPanel5(), gbc);

        Utils.rightAlignTextIn(this);
        btnSelection.addActionListener(e -> {
            String selectedItems = txtCommodity.getText();
            ListCommoditiesOnly d = new ListCommoditiesOnly(selectedItems);

            d.setUndecorated(true);
            d.pack();
            d.setLocationRelativeTo(ui);
            d.setVisible(true);

            selectedItems = d.getValue();
            txtCommodity.setText(selectedItems);
        });
    }

    private void populateGuiFromSettings()
    {
        setBooleanSetting(chkBlackMarket, TDGUI.settings.sellBlackMarket);

        setFloatSetting(txtNearLy, TDGUI.settings.sellNearLy);

        setIntegerSetting(txtBelow, TDGUI.settings.sellBelow);
        setIntegerSetting(txtAbove, TDGUI.settings.sellAbove);
        setIntegerSetting(txtResults, TDGUI.settings.sellResults);

        setStringSetting(txtPads, TDGUI.settings.sellPads);
        setStringSetting(txtPlanetary, TDGUI.settings.sellPlanetary);
        setStringSetting(txtAvoid, TDGUI.settings.sellAvoid);
        setStringSetting(txtCommodity, TDGUI.settings.sellCommodity);

        String sort = TDGUI.settings.sellSortBy;

        switch (sort)
        {
            case Constants.STRING_PRICE:
                optPrice.setSelected(true);
                break;

            case Constants.STRING_SUPPLY:
                optSupply.setSelected(true);
                break;
        }
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.sellBlackMarket = chkBlackMarket.isSelected();

        TDGUI.settings.sellNearLy = Utils.convertStringToFloat(txtNearLy.getText());

        TDGUI.settings.sellBelow = Utils.convertStringToInt(txtBelow.getText());
        TDGUI.settings.sellAbove = Utils.convertStringToInt(txtAbove.getText());
        TDGUI.settings.sellResults = Utils.convertStringToInt(txtResults.getText());

        TDGUI.settings.sellPads = txtPads.getText();
        TDGUI.settings.sellPlanetary = txtPlanetary.getText();
        TDGUI.settings.sellAvoid = txtAvoid.getText();
        TDGUI.settings.sellCommodity = txtCommodity.getText();

        if (optPrice.isSelected())
        {
            TDGUI.settings.sellSortBy = Constants.STRING_PRICE;
        }
        else if (optSupply.isSelected())
        {
            TDGUI.settings.sellSortBy = Constants.STRING_SUPPLY;
        }
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_SELL;
    }

    private void setComponentWidths()
    {
        txtAvoid.setColumns(32);
        txtCommodity.setColumns(32);

        txtAbove.setColumns(6);
        txtBelow.setColumns(6);
        txtResults.setColumns(6);

        txtNearLy.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);
    }
}
