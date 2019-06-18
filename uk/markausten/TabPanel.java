package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class TabPanel extends JPanel
{
    private MainPanel ui;

    private JButton btnBuy;
    private JButton btnDatabase;
    private JButton btnLocal;
    private JButton btnMarket;
    private JButton btnNavigation;
    private JButton btnOldData;
    private JButton btnRares;
    private JButton btnRun;
    private JButton btnSell;
    private JButton btnSettings;
    private JButton btnShips;
    private JButton btnShipVendor;
    private JButton btnStation;
    private JButton btnTrade;
    private JButton btnCmdr;

    private int row = 0;

    /**
     * constructor.
     */
    TabPanel(MainPanel ui)
    {
        this.ui = ui;
        initGUI();
    }

    /**
     * Add the specified button to the panel.
     *
     * @param button The button to add
     * @param gbc    The grid bag constraintes object.
     */
    private void addButton(
            JButton button,
            GridBagConstraints gbc)
    {
        gbc.gridy = row++;
        this.add(button, gbc);
    }

    /**
     * Add the button listeners.
     */
    private void addListeners()
    {
        ActionListener listener = e -> ui.showPane(((JButton) e.getSource()).getText());

        btnBuy.addActionListener(listener);
        btnDatabase.addActionListener(listener);
        btnLocal.addActionListener(listener);
        btnMarket.addActionListener(listener);
        btnNavigation.addActionListener(listener);
        btnOldData.addActionListener(listener);
        btnRares.addActionListener(listener);
        btnRun.addActionListener(listener);
        btnSell.addActionListener(listener);
        btnSettings.addActionListener(listener);
        btnShips.addActionListener(listener);
        btnShipVendor.addActionListener(listener);
        btnStation.addActionListener(listener);
        btnTrade.addActionListener(listener);
        btnCmdr.addActionListener(listener);
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
    {
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));

        btnBuy = new JButton(Constants.BUTTON_CAPTION_BUY);
        btnDatabase = new JButton(Constants.BUTTON_CAPTION_DATABASE);
        btnLocal = new JButton(Constants.BUTTON_CAPTION_LOCAL);
        btnMarket = new JButton(Constants.BUTTON_CAPTION_MARKET);
        btnNavigation = new JButton(Constants.BUTTON_CAPTION_NAVIGATION);
        btnOldData = new JButton(Constants.BUTTON_CAPTION_OLD_DATA);
        btnRares = new JButton(Constants.BUTTON_CAPTION_RARES);
        btnRun = new JButton(Constants.BUTTON_CAPTION_RUN);
        btnSell = new JButton(Constants.BUTTON_CAPTION_SELL);
        btnSettings = new JButton(Constants.BUTTON_CAPTION_SETTINGS);
        btnShips = new JButton(Constants.BUTTON_CAPTION_SHIPS);
        btnShipVendor = new JButton(Constants.BUTTON_CAPTION_SHIP_VENDOR);
        btnStation = new JButton(Constants.BUTTON_CAPTION_STATION);
        btnTrade = new JButton(Constants.BUTTON_CAPTION_TRADE);
        btnCmdr = new JButton(Constants.BUTTON_CAPTION_CMDR);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;

        addButton(btnRun, gbc);
        addButton(btnBuy, gbc);
        addButton(btnSell, gbc);
        addButton(btnRares, gbc);
        addButton(btnTrade, gbc);
        addButton(btnMarket, gbc);
        addButton(btnShipVendor, gbc);
        addButton(btnNavigation, gbc);
        addButton(btnOldData, gbc);
        addButton(btnLocal, gbc);
        addButton(btnSettings, gbc);
        addButton(btnShips, gbc);
        addButton(btnStation, gbc);
        addButton(btnCmdr, gbc);

        gbc.weighty = 1;
        addButton(btnDatabase, gbc);

        addListeners();

        btnShips.setEnabled(false);
        btnCmdr.setEnabled(false);
    }
}
