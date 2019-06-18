package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class BuyCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblAvoid = new JLabel(Constants.LABEL_CAPTION_AVOID);
    private JLabel lblCommodity = new JLabel(Constants.LABEL_CAPTION_COMMODITY);
    private JLabel lblNearLy = new JLabel(Constants.LABEL_CAPTION_NEAR_LY);
    private JLabel lblResults = new JLabel(Constants.LABEL_CAPTION_RESULTS);
    private JLabel lblSupply = new JLabel(Constants.LABEL_CAPTION_SUPPLY);
    private JLabel lblAbove = new JLabel(Constants.LABEL_CAPTION_ABOVE);
    private JLabel lblBelow = new JLabel(Constants.LABEL_CAPTION_BELOW);
    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblSort = new JLabel(Constants.LABEL_CAPTION_SORT_RESULTS_BY);

    private JTextField txtAvoid = new JTextField();
    private JTextField txtCommodity = new JTextField();
    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private NumberField txtNearLy = new NumberField();
    private IntegerField txtResults = new IntegerField();
    private IntegerField txtSupply = new IntegerField();
    private IntegerField txtAbove = new IntegerField();
    private IntegerField txtBelow = new IntegerField();

    private JCheckBox chk1Stop = new JCheckBox(Constants.CHECKBOX_CAPTION_ONE_STOP);
    private JCheckBox chkBlackMarket = new JCheckBox(Constants.CHECKBOX_CAPTION_BLACK_MARKET);

    private JButton btnSelection = new JButton("...");

    private JRadioButton optDistance = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_DISTANCE);
    private JRadioButton optPrice = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_PRICE);
    private JRadioButton optSupply = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_SUPPLY);

    private ButtonGroup optGroup = new ButtonGroup();

    /**
     * constructor.
     */
    public BuyCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGUI();
        populateGuiFromSettings();
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("buy");

        String source = ui.getSource();

        if (source != null && !source.isEmpty())
        {
            Utils.addStringParameter(source, "--near", command);
            Utils.addFloatParameter(txtNearLy.getText(), "--ly", command);
        }

        Utils.addIntegerParameter(txtAbove.getText(), "--gt", command);
        Utils.addIntegerParameter(txtBelow.getText(), "--lt", command);
        Utils.addIntegerParameter(txtSupply.getText(), "--supply", command);

        Utils.addBooleanParameter(chk1Stop.isSelected(), "--one-stop", command);
        Utils.addBooleanParameter(chkBlackMarket.isSelected(), "--bm", command);

        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);

        Utils.addIntegerParameter(txtResults.getText(), "--limit", command);

        Utils.addStringParameter(txtAvoid.getText(), "--avoid", command);

        if (optPrice.isSelected())
        {
            command.add("-P");
        }
        else if (optSupply.isSelected())
        {
            command.add("-S");
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
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

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
        gbc.gridheight = 2;
        gbc.gridwidth = 1;

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
            ListCommoditiesAndShips d = new ListCommoditiesAndShips(selectedItems);

            d.setUndecorated(true);
            d.pack();
            d.setLocationRelativeTo(ui);
            d.setVisible(true);

            selectedItems = d.getValue();
            txtCommodity.setText(selectedItems);
        });
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_BUY;
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblAvoid, 0, 0, "EAST");
        addComponentToPanel(panel, lblCommodity, 0, 1, "EAST");
        addComponentToPanel(panel, txtAvoid, 1, 0, "WEST");
        addComponentToPanel(panel, txtCommodity, 1, 1, "WEST");
        addComponentToPanel(panel, btnSelection, 4, 1, "WEST");

        return panel;
    }

    private void setComponentWidths()
    {
        txtAvoid.setColumns(32);
        txtCommodity.setColumns(32);

        txtAbove.setColumns(6);
        txtBelow.setColumns(6);
        txtResults.setColumns(6);
        txtSupply.setColumns(6);

        txtNearLy.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);

//        btnSelection.setPreferredSize(new Dimension(26, 26));
//        btnSelection.setMaximumSize(btnSelection.getPreferredSize());
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chk1Stop, 0, 0, "WEST");
        addComponentToPanel(panel, chkBlackMarket, 0, 1, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        addComponentToPanel(panel, lblResults, 0, 0, "EAST");
        addComponentToPanel(panel, lblSupply, 0, 1, "EAST");
        addComponentToPanel(panel, lblAbove, 0, 2, "EAST");
        addComponentToPanel(panel, lblBelow, 0, 3, "EAST");
        addComponentToPanel(panel, txtResults, 1, 0, "EAST");
        addComponentToPanel(panel, txtSupply, 1, 1, "EAST");
        addComponentToPanel(panel, txtAbove, 1, 2, "EAST");
        addComponentToPanel(panel, txtBelow, 1, 3, "EAST");

        return panel;
    }

    private JPanel createPanel4()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

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
        optGroup.add(optDistance);
        optGroup.add(optPrice);
        optGroup.add(optSupply);

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblSort, 0, 0, "EAST");
        addComponentToPanel(panel, optDistance, 1, 0, "EAST");
        addComponentToPanel(panel, optPrice, 2, 0, "EAST");
        addComponentToPanel(panel, optSupply, 3, 0, "EAST");

        return panel;
    }

    private void populateGuiFromSettings()
    {
        setBooleanSetting(chkBlackMarket, TDGUI.settings.buyBlackMarket);
        setBooleanSetting(chk1Stop, TDGUI.settings.buyOneStop);

        setFloatSetting(txtNearLy, TDGUI.settings.buyNearLy);

        setIntegerSetting(txtBelow, TDGUI.settings.buyBelow);
        setIntegerSetting(txtAbove, TDGUI.settings.buyAbove);
        setIntegerSetting(txtSupply, TDGUI.settings.buySupply);
        setIntegerSetting(txtResults, TDGUI.settings.buyResults);

        setStringSetting(txtPads, TDGUI.settings.buyPads);
        setStringSetting(txtPlanetary, TDGUI.settings.buyPlanetary);
        setStringSetting(txtAvoid, TDGUI.settings.buyAvoid);
        setStringSetting(txtCommodity, TDGUI.settings.buyCommodity);

        String sort = TDGUI.settings.buySortBy;

        switch (sort)
        {
            case Constants.STRING_DISTANCE:
                optDistance.setSelected(true);
                break;

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
        TDGUI.settings.buyBlackMarket = chkBlackMarket.isSelected();
        TDGUI.settings.buyOneStop = chk1Stop.isSelected();

        TDGUI.settings.buyNearLy = Utils.convertStringToFloat(txtNearLy.getText());

        TDGUI.settings.buyBelow = Utils.convertStringToInt(txtBelow.getText());
        TDGUI.settings.buyAbove = Utils.convertStringToInt(txtAbove.getText());
        TDGUI.settings.buySupply = Utils.convertStringToInt(txtSupply.getText());
        TDGUI.settings.buyResults = Utils.convertStringToInt(txtResults.getText());

        TDGUI.settings.buyPads = txtPads.getText();
        TDGUI.settings.buyPlanetary = txtPlanetary.getText();
        TDGUI.settings.buyAvoid = txtAvoid.getText();
        TDGUI.settings.buyCommodity = txtCommodity.getText();

        if (optDistance.isSelected())
        {
            TDGUI.settings.buySortBy = Constants.STRING_DISTANCE;
        }
        else if (optPrice.isSelected())
        {
            TDGUI.settings.buySortBy = Constants.STRING_PRICE;
        }
        else if (optSupply.isSelected())
        {
            TDGUI.settings.buySortBy = Constants.STRING_SUPPLY;
        }
    }
}
