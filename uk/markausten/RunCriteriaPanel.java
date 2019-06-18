package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class RunCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblStock = new JLabel(Constants.LABEL_CAPTION_STOCK);
    private JLabel lblDemand = new JLabel(Constants.LABEL_CAPTION_DEMAND);
    private JLabel lblCargoLimit = new JLabel(Constants.LABEL_CAPTION_CARGO_LIMIT);
    private JLabel lblMinGpt = new JLabel(Constants.LABEL_CAPTION_MIN_GPT);
    private JLabel lblMaxGpt = new JLabel(Constants.LABEL_CAPTION_MAX_GPT);
    private JLabel lblMargin = new JLabel(Constants.LABEL_CAPTION_MARGIN);
    private JLabel lblHops = new JLabel(Constants.LABEL_CAPTION_HOPS);
    private JLabel lblJumps = new JLabel(Constants.LABEL_CAPTION_JUMPS);
    private JLabel lblAge = new JLabel(Constants.LABEL_CAPTION_AGE);
    private JLabel lblMaxLs = new JLabel(Constants.LABEL_CAPTION_MAX_LS);
    private JLabel lblLsPenalty = new JLabel(Constants.LABEL_CAPTION_LS_PENALTY);
    private JLabel lblPruneScore = new JLabel(Constants.LABEL_CAPTION_PRUNE_SCORE);
    private JLabel lblPruneHops = new JLabel(Constants.LABEL_CAPTION_PRUNE_HOPS);
    private JLabel lblAvoid = new JLabel(Constants.LABEL_CAPTION_AVOID);
    private JLabel lblVia = new JLabel(Constants.LABEL_CAPTION_VIA);
    private JLabel lblPlanetary = new JLabel(Constants.LABEL_CAPTION_PLANETARY);
    private JLabel lblLoopInt = new JLabel(Constants.LABEL_CAPTION_LOOP_INT);
    private JLabel lblStartJumps = new JLabel(Constants.LABEL_CAPTION_START_JUMPS);
    private JLabel lblEndJumps = new JLabel(Constants.LABEL_CAPTION_END_JUMPS);
    private JLabel lblRoutes = new JLabel(Constants.LABEL_CAPTION_ROUTES);

    private IntegerField txtAge = new IntegerField();
    private IntegerField txtCargoLimit = new IntegerField();
    private IntegerField txtDemand = new IntegerField();
    private IntegerField txtEndJumps = new IntegerField();
    private IntegerField txtHops = new IntegerField();
    private IntegerField txtJumps = new IntegerField();
    private IntegerField txtLoopInt = new IntegerField();
    private IntegerField txtMaxGpt = new IntegerField();
    private IntegerField txtMaxLs = new IntegerField();
    private IntegerField txtMinGpt = new IntegerField();
    private IntegerField txtPruneHops = new IntegerField();
    private IntegerField txtPruneScore = new IntegerField();
    private IntegerField txtRoutes = new IntegerField();
    private IntegerField txtStartJumps = new IntegerField();
    private IntegerField txtStock = new IntegerField();

    private NumberField txtLsPenalty = new NumberField();
    private NumberField txtMargin = new NumberField();

    private JTextField txtAvoid = new JTextField();
    private PlanetaryField txtPlanetary = new PlanetaryField();
    private JTextField txtVia = new JTextField();

    private JCheckBox chkBlackMarket = new JCheckBox(Constants.CHECKBOX_CAPTION_BLACK_MARKET);
    private JCheckBox chkDirect = new JCheckBox(Constants.CHECKBOX_CAPTION_DIRECT);
    private JCheckBox chkInsurance = new JCheckBox(Constants.CHECKBOX_CAPTION_INSURANCE);
    private JCheckBox chkJumps = new JCheckBox(Constants.CHECKBOX_CAPTION_JUMPS);
    private JCheckBox chkLoop = new JCheckBox(Constants.CHECKBOX_CAPTION_LOOP);
    private JCheckBox chkShorten = new JCheckBox(Constants.CHECKBOX_CAPTION_SHORTEN);
    private JCheckBox chkTowards = new JCheckBox(Constants.CHECKBOX_CAPTION_TOWARDS);
    private JCheckBox chkUnique = new JCheckBox(Constants.CHECKBOX_CAPTION_UNIQUE);

    /**
     * constructor.
     */
    public RunCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
    }

    private JPanel createRow1Col1Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblStock, 0, 0, "EAST");
        addComponentToPanel(panel, lblDemand, 0, 1, "EAST");
        addComponentToPanel(panel, lblCargoLimit, 0, 2, "EAST");
        addComponentToPanel(panel, txtStock, 1, 0, "WEST");
        addComponentToPanel(panel, txtDemand, 1, 1, "WEST");
        addComponentToPanel(panel, txtCargoLimit, 1, 2, "WEST");

        return panel;
    }

    private JPanel createRow1Col2Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblMinGpt, 0, 0, "EAST");
        addComponentToPanel(panel, lblMaxGpt, 0, 1, "EAST");
        addComponentToPanel(panel, lblMargin, 0, 2, "EAST");
        addComponentToPanel(panel, txtMinGpt, 1, 0, "WEST");
        addComponentToPanel(panel, txtMaxGpt, 1, 1, "WEST");
        addComponentToPanel(panel, txtMargin, 1, 2, "WEST");

        return panel;
    }

    private JPanel createRow1Col3Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblHops, 0, 0, "EAST");
        addComponentToPanel(panel, lblJumps, 0, 1, "EAST");
        addComponentToPanel(panel, txtHops, 1, 0, "WEST");
        addComponentToPanel(panel, txtJumps, 1, 1, "WEST");

        return panel;
    }

    private JPanel createRow1Col4Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblAge, 0, 0, "EAST");
        addComponentToPanel(panel, lblMaxLs, 0, 1, "EAST");
        addComponentToPanel(panel, lblLsPenalty, 0, 2, "EAST");
        addComponentToPanel(panel, lblPruneScore, 0, 3, "EAST");
        addComponentToPanel(panel, lblPruneHops, 0, 4, "EAST");
        addComponentToPanel(panel, txtAge, 1, 0, "EAST");
        addComponentToPanel(panel, txtMaxLs, 1, 1, "EAST");
        addComponentToPanel(panel, txtLsPenalty, 1, 2, "EAST");
        addComponentToPanel(panel, txtPruneScore, 1, 3, "EAST");
        addComponentToPanel(panel, txtPruneHops, 1, 4, "EAST");

        return panel;
    }

    private JPanel createRow1Col5Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblLoopInt, 0, 0, "EAST");
        addComponentToPanel(panel, lblStartJumps, 0, 1, "EAST");
        addComponentToPanel(panel, lblEndJumps, 0, 2, "EAST");
        addComponentToPanel(panel, lblRoutes, 0, 3, "EAST");
        addComponentToPanel(panel, lblPlanetary, 0, 4, "EAST");
        addComponentToPanel(panel, txtLoopInt, 1, 0, "WEST");
        addComponentToPanel(panel, txtStartJumps, 1, 1, "WEST");
        addComponentToPanel(panel, txtEndJumps, 1, 2, "WEST");
        addComponentToPanel(panel, txtRoutes, 1, 3, "WEST");
        addComponentToPanel(panel, txtPlanetary, 1, 4, "WEST");

        return panel;
    }

    private JPanel createRow1Col6Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkLoop, 0, 0, "WEST");
        addComponentToPanel(panel, chkBlackMarket, 0, 1, "WEST");
        addComponentToPanel(panel, chkDirect, 0, 2, "WEST");
        addComponentToPanel(panel, chkJumps, 0, 3, "WEST");
        addComponentToPanel(panel, chkInsurance, 0, 4, "WEST");
        addComponentToPanel(panel, chkShorten, 1, 0, "WEST");
        addComponentToPanel(panel, chkTowards, 1, 1, "WEST");
        addComponentToPanel(panel, chkUnique, 1, 2, "WEST");

        return panel;
    }

    private JPanel createRow2Col1Panel()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblAvoid, 0, 0, "EAST");
        addComponentToPanel(panel, lblVia, 0, 1, "EAST");
        addComponentToPanel(panel, txtAvoid, 1, 0, "WEST");
        addComponentToPanel(panel, txtVia, 1, 1, "WEST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("run");

        Utils.addIntegerParameter(txtAge.getText(), "--age", command);
        Utils.addStringParameter(txtAvoid.getText(), "--avoid", command);
        Utils.addIntegerParameter(txtDemand.getText(), "--demand", command);
        Utils.addIntegerParameter(txtJumps.getText(), "--jumps", command);
        Utils.addStringParameter(txtLsPenalty.getText(), "--lsp", command);
        Utils.addStringParameter(txtMargin.getText(), "--margin", command);
        Utils.addIntegerParameter(txtMaxLs.getText(), "--ls-max", command);
        Utils.addStringParameter(txtPlanetary.getText(), "--planetary", command);
        Utils.addIntegerParameter(txtPruneHops.getText(), "--prune-hops", command);
        Utils.addIntegerParameter(txtPruneScore.getText(), "--prune-score", command);
        Utils.addIntegerParameter(txtStock.getText(), "--supply", command);
        Utils.addStringParameter(txtVia.getText(), "--via", command);

        Utils.addBooleanParameter(chkBlackMarket.isSelected(), "--black-market", command);
        Utils.addBooleanParameter(chkDirect.isSelected(), "--direct", command);
        Utils.addBooleanParameter(chkJumps.isSelected(), "--show-jumps", command);
        Utils.addBooleanParameter(chkLoop.isSelected(), "--loop", command);
        Utils.addBooleanParameter(chkShorten.isSelected(), "--shorten", command);
        Utils.addBooleanParameter(chkTowards.isSelected(), "--towards", command);
        Utils.addBooleanParameter(chkUnique.isSelected(), "--unique", command);

        if (chkInsurance.isSelected())
        {
            Utils.addStringParameter(ui.getInsurance(), "--insurance", command);
        }

        addSource(ui.getSource(), command);
        addDestination(ui.getDestination(), command);

        ui.addVerbosity(command);
        ui.addSettingParameters(command);

        return command;
    }

    /**
     * Set up the panel.
     */
    private void initGui()
    {
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        setComponentWidths();

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel panel1 = createRow1Col1Panel();

        this.add(panel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        JPanel panel2 = createRow1Col2Panel();
        panel2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        this.add(panel2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        JPanel panel3 = createRow1Col3Panel();

        this.add(panel3, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 2;

        JPanel panel4 = createRow1Col4Panel();
        panel4.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        this.add(panel4, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;

        JPanel panel5 = createRow1Col5Panel();
        panel5.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        this.add(panel5, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;

        JPanel panel6 = createRow1Col6Panel();

        this.add(panel6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;

        JPanel panel7 = createRow2Col1Panel();
        panel7.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));

        this.add(panel7, gbc);

        Utils.rightAlignTextIn(this);
    }

    void populateGuiFromSettings()
    {
        setBooleanSetting(chkBlackMarket, TDGUI.settings.runBlackMarket);
        setBooleanSetting(chkDirect, TDGUI.settings.runDirect);
        setBooleanSetting(chkInsurance, TDGUI.settings.runInsurance);
        setBooleanSetting(chkJumps, TDGUI.settings.runShowJumps);
        setBooleanSetting(chkLoop, TDGUI.settings.runLoop);
        setBooleanSetting(chkShorten, TDGUI.settings.runShorten);
        setBooleanSetting(chkTowards, TDGUI.settings.runTowards);
        setBooleanSetting(chkUnique, TDGUI.settings.runUnique);

        setFloatSetting(txtLsPenalty, TDGUI.settings.runLsPenalty);
        setFloatSetting(txtMargin, TDGUI.settings.runMargin);

        setIntegerSetting(txtAge, TDGUI.settings.runAge);
        setIntegerSetting(txtCargoLimit, TDGUI.settings.runCargoLimit);
        setIntegerSetting(txtDemand, TDGUI.settings.runDemand);
        setIntegerSetting(txtEndJumps, TDGUI.settings.runEndJumps);
        setIntegerSetting(txtHops, TDGUI.settings.runHops);
        setIntegerSetting(txtJumps, TDGUI.settings.runJumps);
        setIntegerSetting(txtLoopInt, TDGUI.settings.runLoopInt);
        setIntegerSetting(txtMaxGpt, TDGUI.settings.runMaxGpt);
        setIntegerSetting(txtMaxLs, TDGUI.settings.runMaxLs);
        setIntegerSetting(txtMinGpt, TDGUI.settings.runMinGpt);
        setIntegerSetting(txtPruneHops, TDGUI.settings.runPruneHops);
        setIntegerSetting(txtPruneScore, TDGUI.settings.runPruneScore);
        setIntegerSetting(txtRoutes, TDGUI.settings.runRoutes);
        setIntegerSetting(txtStartJumps, TDGUI.settings.runStartJumps);
        setIntegerSetting(txtStock, TDGUI.settings.runStock);

        setStringSetting(txtAvoid, TDGUI.settings.runAvoid);
        setStringSetting(txtVia, TDGUI.settings.runVia);

        txtPlanetary.setText(TDGUI.settings.runPlanetary);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.runBlackMarket = chkBlackMarket.isSelected();
        TDGUI.settings.runDirect = chkDirect.isSelected();
        TDGUI.settings.runInsurance = chkInsurance.isSelected();
        TDGUI.settings.runShowJumps = chkJumps.isSelected();
        TDGUI.settings.runLoop = chkLoop.isSelected();
        TDGUI.settings.runShorten = chkShorten.isSelected();
        TDGUI.settings.runTowards = chkTowards.isSelected();
        TDGUI.settings.runUnique = chkUnique.isSelected();

        TDGUI.settings.runLsPenalty = Utils.convertStringToFloat(txtLsPenalty.getText());
        TDGUI.settings.runMargin = Utils.convertStringToFloat(txtMargin.getText());

        TDGUI.settings.runAge = Utils.convertStringToInt(txtAge.getText());
        TDGUI.settings.runCargoLimit = Utils.convertStringToInt(txtCargoLimit.getText());
        TDGUI.settings.runDemand = Utils.convertStringToInt(txtDemand.getText());
        TDGUI.settings.runEndJumps = Utils.convertStringToInt(txtEndJumps.getText());
        TDGUI.settings.runHops = Utils.convertStringToInt(txtHops.getText());
        TDGUI.settings.runJumps = Utils.convertStringToInt(txtJumps.getText());
        TDGUI.settings.runLoopInt = Utils.convertStringToInt(txtLoopInt.getText());
        TDGUI.settings.runMaxGpt = Utils.convertStringToInt(txtMaxGpt.getText());
        TDGUI.settings.runMaxLs = Utils.convertStringToInt(txtMaxLs.getText());
        TDGUI.settings.runMinGpt = Utils.convertStringToInt(txtMinGpt.getText());
        TDGUI.settings.runPruneHops = Utils.convertStringToInt(txtPruneHops.getText());
        TDGUI.settings.runPruneScore = Utils.convertStringToInt(txtPruneScore.getText());
        TDGUI.settings.runRoutes = Utils.convertStringToInt(txtRoutes.getText());
        TDGUI.settings.runStartJumps = Utils.convertStringToInt(txtStartJumps.getText());
        TDGUI.settings.runStock = Utils.convertStringToInt(txtStock.getText());

        TDGUI.settings.runAvoid = txtAvoid.getText();
        TDGUI.settings.runVia = txtVia.getText();

        TDGUI.settings.runPlanetary = txtPlanetary.getText();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_RUN;
    }

    private void setComponentWidths()
    {
        txtStock.setColumns(10);
        txtDemand.setColumns(10);
        txtCargoLimit.setColumns(10);

        txtMinGpt.setColumns(6);
        txtMaxGpt.setColumns(6);
        txtMargin.setColumns(6);

        txtHops.setColumns(4);
        txtJumps.setColumns(4);

        txtAge.setColumns(6);
        txtMaxLs.setColumns(6);
        txtLsPenalty.setColumns(6);
        txtPruneScore.setColumns(6);
        txtPruneHops.setColumns(6);

        txtAvoid.setColumns(32);
        txtVia.setColumns(32);

        txtLoopInt.setColumns(3);
        txtStartJumps.setColumns(3);
        txtEndJumps.setColumns(3);
        txtRoutes.setColumns(3);

        txtPlanetary.setColumns(3);
    }
}
