package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class RaresCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblPads = new JLabel(Constants.LABEL_CAPTION_PADS);
    private JLabel lblSort = new JLabel(Constants.LABEL_CAPTION_SORT_RESULTS_BY);
    private JLabel lblAwayLy = new JLabel(Constants.LABEL_CAPTION_LY_AWAY_FROM);
    private JLabel lblSearchDistanceLy = new JLabel(Constants.LABEL_CAPTION_SEARCH_DISTANCE_LY);
    private JLabel lblRare = new JLabel(Constants.LABEL_CAPTION_RARE);
    private JLabel lblRareType = new JLabel(Constants.LABEL_CAPTION_RARE_TYPE);
    private JLabel lblResults = new JLabel(Constants.LABEL_CAPTION_RESULTS);

    private PadField txtPads = new PadField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private NumberField txtAwayLy = new NumberField();
    private NumberField txtSearchDistanceLy = new NumberField();
    private JTextField txtRare = new JTextField();
    private JTextField txtFrom = new JTextField();
    private IntegerField txtResults = new IntegerField();

    private JRadioButton optDistance = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_DISTANCE);
    private JRadioButton optPrice = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_PRICE);

    private ButtonGroup optSortGroup = new ButtonGroup();

    private JRadioButton optLegal = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_LEGAL);
    private JRadioButton optIllegal = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_ILLEGAL);
    private JRadioButton optAll = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_ALL);

    private ButtonGroup optRareTypeGroup = new ButtonGroup();

    private JCheckBox chkReverse = new JCheckBox(Constants.CHECKBOX_CAPTION_REVERSE);
    private JCheckBox chkQuiet = new JCheckBox(Constants.CHECKBOX_CAPTION_QUIET);

    private JButton btnSelectRares = new JButton("...");
    private JButton btnSelectSystems = new JButton("...");

    /**
     * constructor.
     */
    public RaresCriteriaPanel(MainPanel ui)
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

        addComponentToPanel(panel, lblRare, 0, 0, "EAST");
        addComponentToPanel(panel, txtRare, 1, 0, "WEST");
        addComponentToPanel(panel, btnSelectRares, 4, 0, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkReverse, 0, 0, "WEST");
        addComponentToPanel(panel, chkQuiet, 0, 1, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblSearchDistanceLy, 0, 1, "EAST");
        addComponentToPanel(panel, lblResults, 0, 2, "EAST");
        addComponentToPanel(panel, txtSearchDistanceLy, 1, 1, "EAST");
        addComponentToPanel(panel, txtResults, 1, 2, "EAST");

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

    private JPanel createPanel5()
    {
        optSortGroup.add(optDistance);
        optSortGroup.add(optPrice);

        optRareTypeGroup.add(optLegal);
        optRareTypeGroup.add(optIllegal);
        optRareTypeGroup.add(optAll);

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblRareType, 0, 0, "EAST");
        addComponentToPanel(panel, optLegal, 1, 0, "EAST");
        addComponentToPanel(panel, optIllegal, 2, 0, "EAST");
        addComponentToPanel(panel, optAll, 3, 0, "EAST");

        addComponentToPanel(panel, lblSort, 0, 1, "EAST");
        addComponentToPanel(panel, optDistance, 1, 1, "EAST");
        addComponentToPanel(panel, optPrice, 2, 1, "EAST");

        return panel;
    }

    private JPanel createPanel6()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, txtAwayLy, 0, 0, "EAST");
        addComponentToPanel(panel, lblAwayLy, 1, 0, "EAST");
        addComponentToPanel(panel, txtFrom, 2, 0, "EAST");
        addComponentToPanel(panel, btnSelectSystems, 4, 0, "WEST");

        btnSelectSystems.setEnabled(false);

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("rares");

        Utils.addStringParameter(txtRare.getText(), "", command);
        Utils.addBooleanParameter(chkQuiet.isSelected(), "--q", command);

        String source = ui.getSource();

        if (source != null && !source.isEmpty())
        {
            Utils.addStringParameter(source.split("/")[0], "", command);
            Utils.addFloatParameter(txtSearchDistanceLy.getText(), "--ly", command);
        }

        Utils.addIntegerParameter(txtResults.getText(), "--limit", command);
        Utils.addBooleanParameter(chkReverse.isSelected(), "--reverse", command);

        String away = txtAwayLy.getText();
        String from = txtFrom.getText();

        if (away != null && !away.isEmpty() && from != null && !from.isEmpty())
        {
            Utils.addFloatParameter(away, "--away", command);

            for (String s : from.split(","))
            {
                Utils.addStringParameter(s, "--from", command);
            }
        }

        Utils.addStringParameter(txtPads.getText(), "--pad", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);

        Utils.addStringParameter(txtRare.getText(), "", command);

        if (optLegal.isSelected())
        {
            command.add("--legal");
        }
        else if (optIllegal.isSelected())
        {
            command.add("--illegal");
        }

        if (optPrice.isSelected())
        {
            command.add("-P");
        }

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

        this.add(createPanel6(), gbc);

        gbc.gridy = 2;
        this.add(createPanel5(), gbc);

        Utils.rightAlignTextIn(this);

        btnSelectRares.addActionListener(e -> {
            String selectedItems = txtRare.getText();
            ListRareItems d = new ListRareItems(selectedItems);

            d.setUndecorated(true);
            d.pack();
            d.setLocationRelativeTo(ui);
            d.setVisible(true);

            selectedItems = d.getValue();
            txtRare.setText(selectedItems);
        });
    }

    private void populateGuiFromSettings()
    {
        setFloatSetting(txtAwayLy, TDGUI.settings.raresAwayLy);
        setStringSetting(txtFrom, TDGUI.settings.raresFrom);
        setIntegerSetting(txtResults, TDGUI.settings.raresLimit);
        setStringSetting(txtPads, TDGUI.settings.raresPads);
        setStringSetting(txtPlanetary, TDGUI.settings.raresPlanetary);
        setBooleanSetting(chkQuiet, TDGUI.settings.raresQuiet);
        setStringSetting(txtRare, TDGUI.settings.raresRare);
        setBooleanSetting(chkQuiet, TDGUI.settings.raresReverse);
        setFloatSetting(txtSearchDistanceLy, TDGUI.settings.raresSearchDistanceLy);

        String rareType = TDGUI.settings.raresRareType;

        switch (rareType)
        {
            case Constants.STRING_LEGAL:
                optLegal.setSelected(true);
                break;

            case Constants.STRING_ILLEGAL:
                optIllegal.setSelected(true);
                break;

            default:
                optAll.setSelected(true);
                break;
        }

        String sort = TDGUI.settings.raresSortBy;

        switch (sort)
        {
            case Constants.STRING_PRICE:
                optPrice.setSelected(true);
                break;

            case Constants.STRING_DISTANCE:
                optDistance.setSelected(true);
                break;
        }
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.raresAwayLy = Utils.convertStringToFloat(txtAwayLy.getText());
        TDGUI.settings.raresFrom = txtFrom.getText();
        TDGUI.settings.raresLimit = Utils.convertStringToInt(txtResults.getText());
        TDGUI.settings.raresPads = txtPads.getText();
        TDGUI.settings.raresPlanetary = txtPlanetary.getText();
        TDGUI.settings.raresQuiet = chkQuiet.isSelected();
        TDGUI.settings.raresRare = txtRare.getText();
        TDGUI.settings.raresReverse = chkReverse.isSelected();
        TDGUI.settings.raresSearchDistanceLy = Utils.convertStringToFloat(txtSearchDistanceLy.getText());

        if (optIllegal.isSelected())
        {
            TDGUI.settings.raresRareType = Constants.STRING_ILLEGAL;
        }
        else if (optLegal.isSelected())
        {
            TDGUI.settings.raresRareType = Constants.STRING_LEGAL;
        }

        if (optPrice.isSelected())
        {
            TDGUI.settings.raresSortBy = Constants.STRING_PRICE;
        }
        else if (optDistance.isSelected())
        {
            TDGUI.settings.raresSortBy = Constants.STRING_DISTANCE;
        }
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_RARES;
    }

    private void setComponentWidths()
    {
        txtRare.setColumns(32);

        txtResults.setColumns(6);

        txtAwayLy.setColumns(6);
        txtSearchDistanceLy.setColumns(6);
        txtPlanetary.setColumns(4);
        txtPads.setColumns(4);
        txtFrom.setColumns(20);
    }
}
