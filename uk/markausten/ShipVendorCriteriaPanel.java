package uk.markausten;

import javax.swing.*;
import java.awt.*;

class ShipVendorCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JLabel lblHint = new JLabel(Constants.LABEL_CAPTION_SHIP_VENDOR_HINT);

    /**
     * constructor.
     */
    public ShipVendorCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGUI();
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
    {
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        this.add(lblHint, gbc);
    }

    @Override
    public boolean processCanBeCancelled()
    {
        return false;
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_SHIP_VENDOR;
    }
}
