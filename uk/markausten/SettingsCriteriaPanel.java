package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class SettingsCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JCheckBox chkDisableNetLogs = new JCheckBox(Constants.CHECKBOX_CAPTION_DISABLE_NET_LOGS);
    private JCheckBox chkDisableAutoUpdate = new JCheckBox(Constants.CHECKBOX_CAPTION_DISABLE_AUTO_UPDATE);
    private JCheckBox chkSummary = new JCheckBox(Constants.CHECKBOX_CAPTION_SUMMARY);
    private JCheckBox chkProgress = new JCheckBox(Constants.CHECKBOX_CAPTION_PROGRESS);

    private JLabel lblNetLogsPath = new JLabel(Constants.LABEL_CAPTION_NET_LOGS_PATH);
    private JLabel lblRunParameters = new JLabel(Constants.LABEL_CAPTION_RUN_PARAMETERS);
    private JLabel lblRebuy = new JLabel(Constants.LABEL_CAPTION_REBUY);
    private JLabel lblVerbosity = new JLabel(Constants.LABEL_CAPTION_VERBOSITY);


    private JTextField txtNetLogsPath = new JTextField();
    private JTextField txtRunParameters = new JTextField();
    private NumberField txtRebuy = new NumberField();

    private JComboBox<String> cboVerbosity = new JComboBox<>();

    private JButton btnFolder = new JButton("...");

    /**
     * constructor.
     */
    SettingsCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGui();
        populateGuiFromSettings();
        addListeners();
    }

    /**
     * Add any listeners.
     */
    private void addListeners()
    {
        chkDisableNetLogs.addActionListener(e -> checkSettingsSanity());
    }

    /**
     * Check the validity of the settings.
     */
    private void checkSettingsSanity()
    {
        if (Utils
                .determinOsType()
                .equals("MacOS"))
        {
            chkDisableNetLogs.setEnabled(false);
            txtNetLogsPath.setEnabled(false);
            btnFolder.setEnabled(false);
        }
        else
        {
            boolean netLogsEnabled = !TDGUI.settings.settingsDisableNetLogs;

            txtNetLogsPath.setEnabled(netLogsEnabled);
            btnFolder.setEnabled(netLogsEnabled);
        }
    }

    private JPanel createPanel1()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.WHITE));

        addComponentToPanel(panel, lblNetLogsPath, 0, 0, "EAST");
        addComponentToPanel(panel, lblRunParameters, 0, 1, "EAST");
        addComponentToPanel(panel, txtNetLogsPath, 1, 0, "WEST");
        addComponentToPanel(panel, txtRunParameters, 1, 1, "WEST");
        addComponentToPanel(panel, btnFolder, 4, 0, "WEST");

        return panel;
    }

    private JPanel createPanel2()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, chkDisableAutoUpdate, 0, 0, "WEST");
        addComponentToPanel(panel, chkDisableNetLogs, 0, 1, "WEST");
        addComponentToPanel(panel, chkProgress, 0, 2, "WEST");
        addComponentToPanel(panel, chkSummary, 0, 3, "WEST");

        return panel;
    }

    private JPanel createPanel3()
    {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        addComponentToPanel(panel, lblRebuy, 0, 0, "EAST");
        addComponentToPanel(panel, lblVerbosity, 0, 1, "EAST");
        addComponentToPanel(panel, txtRebuy, 1, 0, "EAST");
        addComponentToPanel(panel, cboVerbosity, 1, 1, "EAST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.clear();

        return command;
    }

    /**
     * @return The correct progress setting.
     */
    boolean getProgress()
    {
        return chkProgress.isSelected();
    }

    /**
     * @return The correct summary setting.
     */
    boolean getSummary()
    {
        return chkSummary.isSelected();
    }

    /**
     * @return Get the selected verbosity.
     */
    String getVerbosity()
    {
        return (String) cboVerbosity.getSelectedItem();
    }

    /**
     * Set up the panel.
     */
    private void initGui()
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

        Utils.rightAlignTextIn(this);

        txtNetLogsPath.setEditable(false);

        btnFolder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            String selectedFolder = txtNetLogsPath.getText();

            if (selectedFolder == null || selectedFolder.isEmpty())
            {
                selectedFolder = ".";
            }

            chooser.setCurrentDirectory(new java.io.File(selectedFolder));

            chooser.setDialogTitle(Constants.FILE_CHOOSER_CAPTION_NET_LOGS);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(TDGUI.frame) == JFileChooser.APPROVE_OPTION)
            {
                selectedFolder = chooser
                        .getSelectedFile()
                        .toString();

                selectedFolder = selectedFolder.replace("/./", "/");

                txtNetLogsPath.setText(selectedFolder);
            }
        });

        cboVerbosity.addItem("");
        cboVerbosity.addItem("-v");
        cboVerbosity.addItem("-vv");
        cboVerbosity.addItem("-vvv");
    }

    void populateGuiFromSettings()
    {
        setStringSetting(txtNetLogsPath, TDGUI.settings.settingsNetLogPath);
        setStringSetting(txtRunParameters, TDGUI.settings.settingsRunParams);
        setFloatSetting(txtRebuy, TDGUI.settings.settingsRebuy);
        setBooleanSetting(chkDisableAutoUpdate, TDGUI.settings.settingsDisableAutoupdate);
        setBooleanSetting(chkDisableNetLogs, TDGUI.settings.settingsDisableNetLogs);
        setBooleanSetting(chkProgress, TDGUI.settings.settingsProgress);
        setBooleanSetting(chkSummary, TDGUI.settings.settingsSummary);

        cboVerbosity.setSelectedItem(TDGUI.settings.settingsVerbosity);
    }

    public void populateSettingsFromGui()
    {
        TDGUI.settings.settingsNetLogPath = txtNetLogsPath.getText();
        TDGUI.settings.settingsRunParams = txtRunParameters.getText();
        TDGUI.settings.settingsRebuy = Utils.convertStringToFloat(txtRebuy.getText());
        TDGUI.settings.settingsDisableAutoupdate = chkDisableAutoUpdate.isSelected();
        TDGUI.settings.settingsDisableNetLogs = chkDisableNetLogs.isSelected();
        TDGUI.settings.settingsProgress = chkProgress.isSelected();
        TDGUI.settings.settingsSummary = chkSummary.isSelected();

        TDGUI.settings.settingsVerbosity = (String) cboVerbosity.getSelectedItem();
    }

    @Override
    public void postProcessingHook()
    {
        checkSettingsSanity();
    }

    @Override
    public void preProcessingHook()
    {
    }

    @Override
    public void preShowHook()
    {
        checkSettingsSanity();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_SETTINGS;
    }

    private void setComponentWidths()
    {
        txtNetLogsPath.setColumns(32);
        txtRunParameters.setColumns(32);

        txtRebuy.setColumns(6);
    }
}
