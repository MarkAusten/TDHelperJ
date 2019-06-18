package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class MarketCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblMarketType = new JLabel(Constants.LABEL_CAPTION_MARKET_TYPE);

    private JRadioButton optBuy = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_BUY);
    private JRadioButton optSell = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_SELL);
    private JRadioButton optAll = new JRadioButton(Constants.RADIO_BUTTON_CAPTION_ALL);

    private ButtonGroup optMarketTypeGroup = new ButtonGroup();

    /**
     * constructor.
     */
    public MarketCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        InitGui();
        populateGuiFromSettings();
    }

    private void InitGui()
    {
        this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        this.add(createPanel1(), gbc);
    }

    private JPanel createPanel1()
    {
        optMarketTypeGroup.add(optBuy);
        optMarketTypeGroup.add(optSell);
        optMarketTypeGroup.add(optAll);

        JPanel panel = new JPanel(new GridBagLayout());

        addComponentToPanel(panel, lblMarketType, 0, 0, "EAST");
        addComponentToPanel(panel, optBuy, 1, 0, "EAST");
        addComponentToPanel(panel, optSell, 2, 0, "EAST");
        addComponentToPanel(panel, optAll, 3, 0, "EAST");

        return panel;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("market");

        String source = ui.getSource();

        if (source != null && !source.isEmpty())
        {
            Utils.addStringParameter(source, "", command);
        }

        if (optBuy.isSelected())
        {
            command.add("--buy");
        }
        else if (optSell.isSelected())
        {
            command.add("--sell");
        }

        ui.addVerbosity(command);

        return command;
    }

    private void populateGuiFromSettings()
    {
        String rareType = TDGUI.settings.raresRareType;

        switch (rareType)
        {
            case Constants.STRING_BUY:
                optBuy.setSelected(true);
                break;

            case Constants.STRING_SELL:
                optSell.setSelected(true);
                break;

            default:
                optAll.setSelected(true);
                break;
        }
    }

    public void populateSettingsFromGui()
    {
        if (optBuy.isSelected())
        {
            TDGUI.settings.raresRareType = Constants.STRING_BUY;
        }
        else if (optSell.isSelected())
        {
            TDGUI.settings.raresRareType = Constants.STRING_SELL;
        }
        else
        {
            TDGUI.settings.raresRareType = Constants.STRING_ALL;
        }
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_MARKET;
    }
}
