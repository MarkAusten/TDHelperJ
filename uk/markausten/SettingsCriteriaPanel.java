package uk.markausten;

import javax.swing.*;
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

    private JComboBox cboVerbosity = new JComboBox();

    /**
     * constructor.
     */
    public SettingsCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.clear();

        return command;
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_SETTINGS;
    }

    @Override
    public void preProcessingHook()
    {
        //
    }

    @Override
    public void postProcessingHook()
    {
        super.postProcessingHook();
    }
}
