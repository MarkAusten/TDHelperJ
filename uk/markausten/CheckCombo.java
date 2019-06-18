package uk.markausten;

import javax.swing.*;
import java.awt.*;

class CheckCombo extends JComboBox
{
    CheckCombo(CheckComboStore[] stores)
    {
        super(stores);

        setRenderer(new CheckComboRenderer());

        addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
            CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
            ccr.checkBox.setSelected((store.state = !store.state));
        });
    }
}

/**
 * adapted from comment section of ListCellRenderer api
 */
class CheckComboRenderer implements ListCellRenderer
{
    JCheckBox checkBox;

    CheckComboRenderer()
    {
        checkBox = new JCheckBox();
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        CheckComboStore store = (CheckComboStore) value;

        checkBox.setText(store.id);
        checkBox.setSelected(store.state);
        checkBox.setBackground(isSelected
                                       ? Color.red
                                       : Color.white);
        checkBox.setForeground(isSelected
                                       ? Color.white
                                       : Color.black);

        return checkBox;
    }
}

class CheckComboStore
{
    String id;
    Boolean state;

    CheckComboStore(
            String id,
            Boolean state)
    {
        this.id = id;
        this.state = state;
    }
}