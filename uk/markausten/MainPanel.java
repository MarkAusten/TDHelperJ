package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class MainPanel extends JPanel
{
    private BaseCriteriaPanel currentCriteriaPanel;

    private JPanel criteriaPanelHolder = new JPanel();
    private TabPanel tabPanel = new TabPanel(this);
    private ButtonPane buttonPanel = new ButtonPane(this);
    private RunCriteriaPanel runCriteriaPanel;
    private StatusBar statusBar = new StatusBar();
    private ShipPanel shipPanel;
    private OutputPanel outputPanel = new OutputPanel(this);
    private DatabaseCriteriaPanel databaseCriteriaPanel;
    private BuyCriteriaPanel buyCriteriaPanel;
    private SellCriteriaPanel sellCriteriaPanel;
    private RaresCriteriaPanel raresCriteriaPanel;
    private TradeCriteriaPanel tradeCriteriaPanel;
    private MarketCriteriaPanel marketCriteriaPanel;
    private NavigationCriteriaPanel navigationCriteriaPanel;
    private OldDataCriteriaPanel oldDataCriteriaPanel;
    private LocalCriteriaPanel localCriteriaPanel;
    private StationCriteriaPanel stationCriteriaPanel;
    private SettingsCriteriaPanel settingsCriteriaPanel;
    private ShipVendorCriteriaPanel shipVendorCriteriaPanel;
    private CommanderCriteriaPanel commanderCriteriaPanel;

    private Process process;
    private List<String> commandInformation;
    private ThreadedStreamHandler inputStreamHandler;
    private ThreadedStreamHandler errorStreamHandler;

    private Timer timer;
    private Timer elapsedTimer;
    private boolean timerStopFlag = false;
    private int secondsElapsed = 0;

    public MainPanel()
    {
        initGui();
    }

    /**
     * @param panel The criteria panel to be added to the colleection.
     */
    private void addCriteriaPanelToCollection(BaseCriteriaPanel panel)
    {
        if (panel.getParent() != criteriaPanelHolder)
        {
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;

            criteriaPanelHolder.add(panel, gbc);
        }


        currentCriteriaPanel = panel;
    }

    /**
     * @param command   The command list to which the parameter should be added.
     * @param parameter The parameter to be added.
     */
    private void addParameterToCommand(
            List<String> command,
            String parameter)
    {
        if (!parameter.isEmpty())
        {
            command.add(parameter);
        }
    }

    /**
     * Add the required progress and summary parameters, set on the settings pane, to the command parameter list.
     *
     * @param command The command list to which the progress and summary parameters should be added.
     */
    void addSettingParameters(List<String> command)
    {
        boolean progress;
        boolean summary;

        if (settingsCriteriaPanel != null)
        {
            progress = settingsCriteriaPanel.getProgress();
            summary = settingsCriteriaPanel.getSummary();
        }
        else
        {
            progress = TDGUI.settings.settingsProgress;
            summary = TDGUI.settings.settingsSummary;
        }

        addParameterToCommand(command, progress
                ? "--progress"
                : "");
        addParameterToCommand(command, summary
                ? "--summary"
                : "");
    }

    /**
     * Add the required verbosity, set on the settings pane, to the command parameter list.
     *
     * @param command The command list to which the verbosity parameter should be added.
     */
    void addVerbosity(List<String> command)
    {
        addParameterToCommand(command, settingsCriteriaPanel == null
                ? TDGUI.settings.settingsVerbosity
                : settingsCriteriaPanel.getVerbosity());
    }

    /**
     * Cancel the command processing.
     */
    void cancelProcessing()
    {
        try
        {
            inputStreamHandler.interrupt();
            errorStreamHandler.interrupt();

            process.destroy();

            queueString("\n\nCancelled.");

            showCurrentCriteriaPanel();
        }
        catch (Exception e)
        {
            // Do something.
        }
    }

    /**
     * Clear the output panel of any prior messages.
     */
    private void clearOutputPanel()
    {
        outputPanel.clearOutput();
    }

    /**
     * @param commandInformation The command to be displayed.
     */
    private void displayCommand(List<String> commandInformation)
    {
        // Create and queue the command message.
        StringBuilder message = new StringBuilder();

        for (String item : commandInformation)
        {
            message
                    .append(" ")
                    .append(item);
        }

        queueString("Command: " + message
                .toString()
                .substring(6)
                .trim() + "\n");
    }

    /**
     * Flag the timer to stop once all output messages have been displayed.
     */
    private void flagTimerStop()
    {
        timerStopFlag = true;
    }

    /**
     * Show the correct criteria panel.
     */
    private BuyCriteriaPanel getBuyCriteriaPanel()
    {
        if (buyCriteriaPanel == null)
        {
            buyCriteriaPanel = new BuyCriteriaPanel(this);
        }

        return buyCriteriaPanel;
    }

    /**
     * Get the command to be run.
     * \
     *
     * @return The command to be run.
     */
    private List<String> getCommand()
    {
        List<String> command = new ArrayList<>();

        command.add("trade");

        currentCriteriaPanel.getCommand(command);

        if (currentCriteriaPanel instanceof RunCriteriaPanel)
        {
            shipPanel.getCommand(command);
        }

        return command;
    }

    /**
     * Show the correct criteria panel.
     */
    private DatabaseCriteriaPanel getDatabaseCriteriaPanel()
    {
        if (databaseCriteriaPanel == null)
        {
            databaseCriteriaPanel = new DatabaseCriteriaPanel(this);
        }

        return databaseCriteriaPanel;
    }

    /**
     * @return Teh destination system and possibly station.
     */
    String getDestination()
    {
        return buttonPanel.getDestination();
    }

    /**
     * @return The insurance value for the currently selected ship.
     */
    String getInsurance()
    {
        return shipPanel.getInsurance();
    }

    /**
     * Show the correct criteria panel.
     */
    private LocalCriteriaPanel getLocalCriteriaPanel()
    {
        if (localCriteriaPanel == null)
        {
            localCriteriaPanel = new LocalCriteriaPanel(this);
        }

        return localCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private MarketCriteriaPanel getMarketCriteriaPanel()
    {
        if (marketCriteriaPanel == null)
        {
            marketCriteriaPanel = new MarketCriteriaPanel(this);
        }

        return marketCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private NavigationCriteriaPanel getNavigationCriteriaPanel()
    {
        if (navigationCriteriaPanel == null)
        {
            navigationCriteriaPanel = new NavigationCriteriaPanel(this);
        }

        return navigationCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private OldDataCriteriaPanel getOldDataCriteriaPanel()
    {
        if (oldDataCriteriaPanel == null)
        {
            oldDataCriteriaPanel = new OldDataCriteriaPanel(this);
        }

        return oldDataCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private RaresCriteriaPanel getRaresCriteriaPanel()
    {
        if (raresCriteriaPanel == null)
        {
            raresCriteriaPanel = new RaresCriteriaPanel(this);
        }

        return raresCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private RunCriteriaPanel getRunCriteriaPanel()
    {
        if (runCriteriaPanel == null)
        {
            runCriteriaPanel = new RunCriteriaPanel(this);
        }

        return runCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private SellCriteriaPanel getSellCriteriaPanel()
    {
        if (sellCriteriaPanel == null)
        {
            sellCriteriaPanel = new SellCriteriaPanel(this);
        }

        return sellCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private SettingsCriteriaPanel getSettingsCriteriaPanel()
    {
        if (settingsCriteriaPanel == null)
        {
            settingsCriteriaPanel = new SettingsCriteriaPanel(this);
        }

        return settingsCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private CommanderCriteriaPanel getCommanderCriteriaPanel()
    {
        if (commanderCriteriaPanel == null)
        {
            commanderCriteriaPanel = new CommanderCriteriaPanel(this);
        }

        return commanderCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private ShipVendorCriteriaPanel getShipVendorCriteriaPanel()
    {
        if (shipVendorCriteriaPanel == null)
        {
            shipVendorCriteriaPanel = new ShipVendorCriteriaPanel(this);
        }

        return shipVendorCriteriaPanel;
    }

    /**
     * @return the selected system and possibly station.
     */
    String getSource()
    {
        return buttonPanel.getSource();
    }

    /**
     * Show the correct criteria panel.
     */
    private StationCriteriaPanel getStationCriteriaPanel()
    {
        if (stationCriteriaPanel == null)
        {
            stationCriteriaPanel = new StationCriteriaPanel(this);
        }

        return stationCriteriaPanel;
    }

    /**
     * Show the correct criteria panel.
     */
    private TradeCriteriaPanel getTradeCriteriaPanel()
    {
        if (tradeCriteriaPanel == null)
        {
            tradeCriteriaPanel = new TradeCriteriaPanel(this);
        }

        return tradeCriteriaPanel;
    }

    /**
     * Hide the currently displayed criteria panel.
     */
    private void hideCurrentCriteriaPanel()
    {
        if (currentCriteriaPanel != null)
        {
            currentCriteriaPanel.populateSettingsFromGui();
            currentCriteriaPanel.setVisible(false);
        }
    }

    /**
     * Add the GUI components to the pane.
     */
    private void initGui()
    {
        String cmdrName = TDGUI.settings.commanderName;

        if (cmdrName != null && !cmdrName.isEmpty())
        {
            cmdrName = " : Commander " + cmdrName;
        }

        TDGUI.frame.setTitle("TDHelperJ v" + TDGUI.version + cmdrName);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add the tab panel to the left of the pane.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        add(tabPanel, gbc);

        // Add the button panel to the top right of the pane
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridheight = 1;
        add(buttonPanel, gbc);

        // Add the ship panel under the button panel.
        gbc.gridy = 1;

        shipPanel = new ShipPanel(this);
        add(shipPanel, gbc);

        // Add the output panel under the criteria panel.
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 2);
        add(outputPanel, gbc);

        // Add the run criteria panel under the ship panel.
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);

        criteriaPanelHolder.setLayout(new GridBagLayout());
        add(criteriaPanelHolder, gbc);

        // Add the status bar to the bottom of the panel, full width.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space
        gbc.insets = new Insets(0, 0, 5, 0);  //top padding
        gbc.gridwidth = 2;   //2 columns wide
        add(statusBar, gbc);

        showPane(Constants.BUTTON_CAPTION_RUN);

        queueStringImmediate(Constants.STRING_READY);
    }

    /**
     * Push a message onto the output display queue.
     *
     * @param text The message to be displayed.
     */
    void queueString(String text)
    {
        outputPanel.queue.put(text);
    }

    /**
     * Push a message onto the output display queue and dispplay it immediately.
     *
     * @param text The message to be displayed.
     */
    void queueStringImmediate(String text)
    {
        queueString(text);

        outputPanel.updateOutput();
    }

    void refreshShipPanel()
    {
        shipPanel.populateGuiFromSettings();
    }

    /**
     * Run a command that runs via the Trade Dangerous trade command.
     *
     * @throws IOException Soemthing wrong!
     */
    private void runTradeCommand() throws IOException
    {
        // Set the elapsed timer going.
        if (elapsedTimer == null)
        {
            elapsedTimer = new Timer(1000, e -> updateElapsed());
        }

        secondsElapsed = 0;
        elapsedTimer.start();

        // Get a new process and issue the command.
        ProcessBuilder processBuilder = new ProcessBuilder(commandInformation);

        process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        inputStreamHandler = new ThreadedStreamHandler(inputStream, this);
        errorStreamHandler = new ThreadedStreamHandler(errorStream, this);

        inputStreamHandler.start();
        errorStreamHandler.start();

        timerStopFlag = false;

        // Set up a listener to detect when the command has finished.
        ProcessExitDetector ped = new ProcessExitDetector(process);

        ped.addProcessListener(process1 -> {
            // Shut down the timers and tidy up.
            flagTimerStop();
            elapsedTimer.stop();
            buttonPanel.setButtonMode("");
            currentCriteriaPanel.postProcessingHook();
        });

        // start the listener.
        ped.start();

        // Update the output display repeatedly.
        timer = new Timer(500, e -> updateOutput());

        timer.start();
    }

    /**
     * Save the application settings.
     */
    void saveSettings()
    {
        if (settingsCriteriaPanel != null)
        {
            settingsCriteriaPanel.populateSettingsFromGui();
        }

        TDGUI.settings.saveSettings();
    }

    /**
     * Show the correct criteria panel.
     */
    private void showCurrentCriteriaPanel()
    {
        if (currentCriteriaPanel != null)
        {
            currentCriteriaPanel.setVisible(true);
            buttonPanel.setButtonMode(currentCriteriaPanel.requiredButtonMode());
            currentCriteriaPanel.preProcessingHook();
        }
    }

    /**
     * Show the appropriate panel.
     *
     * @param caption The caption of the button calling this method.
     */
    void showPane(String caption)
    {
        hideCurrentCriteriaPanel();

        BaseCriteriaPanel panel = null;

        switch (caption)
        {
            case Constants.BUTTON_CAPTION_DATABASE:
                panel = getDatabaseCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_RUN:
                panel = getRunCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_BUY:
                panel = getBuyCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_SELL:
                panel = getSellCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_RARES:
                panel = getRaresCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_TRADE:
                panel = getTradeCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_MARKET:
                panel = getMarketCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_SHIP_VENDOR:
                panel = getShipVendorCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_NAVIGATION:
                panel = getNavigationCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_OLD_DATA:
                panel = getOldDataCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_LOCAL:
                panel = getLocalCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_STATION:
                panel = getStationCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_SETTINGS:
                panel = getSettingsCriteriaPanel();
                break;

            case Constants.BUTTON_CAPTION_CMDR:
                panel = getCommanderCriteriaPanel();
                break;
        }

        if (panel != null)
        {
            addCriteriaPanelToCollection(panel);
            showCurrentCriteriaPanel();
        }
    }

    /**
     * Show the available ships and prices at the selected station.
     */
    private void showShipsAvailableAtCurrentStation()
    {
        String source = getSource();

        if (source == null || source.indexOf('/') == -1)
        {
            clearOutputPanel();
            queueStringImmediate("A source system and station must be selected.");
        }
        else
        {
            if (source.indexOf('/') > -1)
            {
                ListShips d = new ListShips(source);

                d.setUndecorated(true);
                d.pack();
                d.setLocationRelativeTo(this);
                d.setVisible(true);
            }
        }

        buttonPanel.setButtonMode("");
        currentCriteriaPanel.postProcessingHook();
    }

    /**
     * Start the command processing.
     */
    void startProcessing()
    {
        try
        {
            // Save the current panel settings.
            currentCriteriaPanel.populateSettingsFromGui();
            shipPanel.populateSettingsFromGui();

            TDGUI.settings.saveSettings();

            commandInformation = getCommand();

            // Clear the output display.
            clearOutputPanel();

            displayCommand(commandInformation);

            if (currentCriteriaPanel.processCanBeCancelled())
            {
                buttonPanel.setButtonMode(Constants.BUTTON_CAPTION_CANCEL);
            }
            else
            {
                buttonPanel.setStartButtonEnabled(false);
            }

            if (currentCriteriaPanel
                    .requiredButtonMode()
                    .equals(Constants.REQUIRED_BUTTON_MODE_SHIP_VENDOR))
            {
                showShipsAvailableAtCurrentStation();
            }
            else
            {
                runTradeCommand();
            }
        }
        catch (Exception e)
        {
            LogClass.log.severe(e.getMessage());
        }
    }

    /**
     * Update the elapsed time display.
     */
    private void updateElapsed()
    {
        StringBuilder message = new StringBuilder();

        ++secondsElapsed;

        message
                .append("Elapsed: ")
                .append(Utils.secondsToHHMMSS(secondsElapsed));

        statusBar.setLeftStatus(message.toString());
    }

    /**
     * Update the output with any queued messages.
     */
    private void updateOutput()
    {
        outputPanel.updateOutput();

        // Stop the timer if the flag is set.
        if (timerStopFlag)
        {
            timer.stop();
        }
    }
}
