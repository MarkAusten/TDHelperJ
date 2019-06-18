package uk.markausten;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class DatabaseCriteriaPanel extends BaseCriteriaPanel
{
    private MainPanel ui;

    private JCheckBox chkAll = new JCheckBox(Constants.CHECKBOX_CAPTION_ALL);
    private JCheckBox chkClean = new JCheckBox(Constants.CHECKBOX_CAPTION_CLEAN);
    private JCheckBox chkFallback = new JCheckBox(Constants.CHECKBOX_CAPTION_FALLBACK);
    private JCheckBox chkForce = new JCheckBox(Constants.CHECKBOX_CAPTION_FORCE);
    private JCheckBox chkItem = new JCheckBox(Constants.CHECKBOX_CAPTION_ITEM);
    private JCheckBox chkListings = new JCheckBox(Constants.CHECKBOX_CAPTION_LISTINGS);
    private JCheckBox chkShip = new JCheckBox(Constants.CHECKBOX_CAPTION_SHIP);
    private JCheckBox chkShipvend = new JCheckBox(Constants.CHECKBOX_CAPTION_SHIPVEND);
    private JCheckBox chkSkipvend = new JCheckBox(Constants.CHECKBOX_CAPTION_SKIPVEND);
    private JCheckBox chkSolo = new JCheckBox(Constants.CHECKBOX_CAPTION_SOLO);
    private JCheckBox chkStation = new JCheckBox(Constants.CHECKBOX_CAPTION_STATION);
    private JCheckBox chkSystem = new JCheckBox(Constants.CHECKBOX_CAPTION_SYSTEM);
    private JCheckBox chkUpgrade = new JCheckBox(Constants.CHECKBOX_CAPTION_UPGRADE);
    private JCheckBox chkUpvend = new JCheckBox(Constants.CHECKBOX_CAPTION_UPVEND);

    /**
     * constructor.
     */
    public DatabaseCriteriaPanel(MainPanel ui)
    {
        super();

        this.ui = ui;

        initGUI();
    }

    private boolean atLeastOneOptionChecked()
    {
        boolean allDeselected = true;

        for (Component c : this.getComponents())
        {
            if (c instanceof JCheckBox && ((JCheckBox) c).isSelected())
            {
                allDeselected = false;
                break;
            }
        }

        return !allDeselected;
    }

    @Override
    public List<String> getCommand(List<String> command)
    {
        command.add("import");
        command.add("-P");
        command.add("eddblink");

        if (atLeastOneOptionChecked())
        {
            command.add("-O");

            StringBuilder sb = new StringBuilder();

            for (Component c : this.getComponents())
            {
                if (c instanceof JCheckBox)
                {
                    JCheckBox chk = (JCheckBox) c;

                    if (chk.isEnabled() && chk.isSelected())
                    {
                        sb.append(",")
                          .append(chk.getText()
                                     .toLowerCase());
                    }
                }
            }

            if (sb.length() > 0)
            {
                command.add(sb.toString()
                              .substring(1));
            }
        }

        return command;
    }

    /**
     * Set up the panel.
     */
    private void initGUI()
    {
        setLayout(new GridBagLayout());

        addComponentToPanel(this, chkAll, 0, 0, "WEST");
        addComponentToPanel(this, chkClean, 1, 0, "WEST");
        addComponentToPanel(this, chkFallback, 2, 0, "WEST");
        addComponentToPanel(this, chkForce, 3, 0, "WEST");
        addComponentToPanel(this, chkItem, 4, 0, "WEST");
        addComponentToPanel(this, chkListings, 5, 0, "WEST");
        addComponentToPanel(this, chkShip, 6, 0, "WEST");
        addComponentToPanel(this, chkShipvend, 0, 1, "WEST");
        addComponentToPanel(this, chkSkipvend, 1, 1, "WEST");
        addComponentToPanel(this, chkSolo, 2, 1, "WEST");
        addComponentToPanel(this, chkStation, 3, 1, "WEST");
        addComponentToPanel(this, chkSystem, 4, 1, "WEST");
        addComponentToPanel(this, chkUpgrade, 5, 1, "WEST");
        addComponentToPanel(this, chkUpvend, 6, 1, "WEST");

        chkListings.setSelected(true);

        setListings();
    }

    @Override
    String requiredButtonMode()
    {
        return Constants.REQUIRED_BUTTON_MODE_DATABASE;
    }

    private void setAll()
    {
        if (chkAll.isSelected())
        {
            unsetAllOptions();

            chkAll.setSelected(true);
            chkItem.setSelected(true);
            chkListings.setSelected(true);
            chkShip.setSelected(true);
            chkShipvend.setSelected(true);
            chkStation.setSelected(true);
            chkSystem.setSelected(true);
            chkUpgrade.setSelected(true);
            chkUpvend.setSelected(true);

            chkItem.setEnabled(false);
            chkListings.setEnabled(false);
            chkShip.setEnabled(false);
            chkShipvend.setEnabled(false);
            chkStation.setEnabled(false);
            chkSystem.setEnabled(false);
            chkUpgrade.setEnabled(false);
            chkUpvend.setEnabled(false);
        }
        else
        {
            setListings();
        }
    }

    private void setClean()
    {

        if (chkClean.isSelected())
        {
            unsetAllOptions();

            chkAll.setSelected(true);
            chkClean.setSelected(true);
            chkForce.setSelected(true);
            chkItem.setSelected(true);
            chkListings.setSelected(true);
            chkShip.setSelected(true);
            chkShipvend.setSelected(true);
            chkStation.setSelected(true);
            chkSystem.setSelected(true);
            chkUpgrade.setSelected(true);
            chkUpvend.setSelected(true);

            chkAll.setEnabled(false);
            chkForce.setEnabled(false);
            chkItem.setEnabled(false);
            chkListings.setEnabled(false);
            chkShip.setEnabled(false);
            chkShipvend.setEnabled(false);
            chkStation.setEnabled(false);
            chkSystem.setEnabled(false);
            chkUpgrade.setEnabled(false);
            chkUpvend.setEnabled(false);
        }
        else
        {
            setListings();
        }
    }

    private void setListings()
    {
        if (chkListings.isSelected())
        {
            unsetAllOptions();

            chkItem.setSelected(true);
            chkListings.setSelected(true);
            chkStation.setSelected(true);
            chkSystem.setSelected(true);

            chkItem.setEnabled(false);
            chkStation.setEnabled(false);
            chkSystem.setEnabled(false);
        }
        else
        {
            unsetAllOptions();
        }
    }

    private void setShipVend()
    {
        if (chkShipvend.isSelected())
        {
            chkShip.setSelected(true);
            chkShipvend.setSelected(true);

            chkShip.setEnabled(false);
        }
        else
        {
            setListings();
        }
    }

    private void setSkipvend()
    {
        if (chkSkipvend.isSelected())
        {
            unsetAllOptions();

            chkSkipvend.setSelected(true);

            chkShipvend.setEnabled(false);
            chkUpvend.setEnabled(false);
        }
        else
        {
            unsetAllOptions();
        }
    }

    private void setSolo()
    {
        if (chkSolo.isSelected())
        {
            unsetAllOptions();

            chkSkipvend.setSelected(true);
            chkSolo.setSelected(true);

            chkListings.setEnabled(false);
            chkShipvend.setEnabled(false);
            chkSkipvend.setEnabled(false);
            chkUpvend.setEnabled(false);
        }
        else
        {
            setListings();
        }
    }

    private void setUpvend()
    {
        if (chkUpvend.isSelected())
        {
            chkUpgrade.setSelected(true);
            chkUpgrade.setEnabled(false);
        }
        else
        {
            setListings();
        }
    }

    private void unsetAllOptions()
    {
        Utils.selectAllCheckboxes(this, false);
        Utils.enableAllCheckboxes(this, true);
    }
}
