package uk.markausten;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

class ListRareItems extends JDialog
{
    private static final int CHECK_COL = 2;
    private Object[][] data;
    private String selectedItems;
    private DataModel model;
    private JTable tblCommodities;

    ListRareItems(String selectedItems)
    {
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        setModalityType(ModalityType.APPLICATION_MODAL);

        String[] columns = {
                "Categories",
                "Rare Items",
                "Selected"
        };

        populateData(selectedItems);

        model = new DataModel(data, columns);

        tblCommodities = new JTable(model)
        {
            public Component prepareRenderer(
                    TableCellRenderer renderer,
                    int row,
                    int column)
            {
                Component c = super.prepareRenderer(
                        renderer,
                        row,
                        column
                                                   );

                if (!isRowSelected(row))
                {
                    boolean value = (boolean) model.getValueAt(row, 2);

                    Color colour = value
                            ? UIManager.getColor("List.selectionBackground")
                            : getBackground();

                    c.setBackground(colour);

                    colour = value
                            ? UIManager.getColor("List.selectionForeground")
                            : getForeground();

                    c.setForeground(colour);
                }

                return c;
            }
        };

        tblCommodities.getSelectionModel().addListSelectionListener(e -> {
            setSelectedItems();
            onOK();
        });

        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JScrollPane scroll = new JScrollPane(tblCommodities);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(scroll, gbc);

        gbc.gridy = 1;
        JButton buttonOK = new JButton("Done");
        contentPane.add(buttonOK, gbc);

        buttonOK.addActionListener(e -> onOK());
    }

    String getValue()
    {
        return selectedItems;
    }

    private void onOK()
    {
        dispose();
    }

    void populateData(String selectedItems)
    {
        List<Object[]> rows = (new Database()).getRareItems();

        data = new Object[rows.size()][3];

        List<String> items = Arrays.asList(selectedItems.split(","));

        int i = 0;

        for (Object[] s : rows)
        {
            Object[] row = new Object[3];

            row[0] = s[0];
            row[1] = s[1];
            row[2] = items.contains(s[1]);

            data[i++] = row;
        }
    }

    private void setSelectedItems()
    {
        selectedItems = (String)tblCommodities.getValueAt(tblCommodities.getSelectedRow(), 1);
    }

    class DataModel extends DefaultTableModel
    {

        DataModel(
                Object[][] data,
                Object[] columnNames)
        {
            super(data, columnNames);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            if (columnIndex == CHECK_COL)
            {
                return getValueAt(0, CHECK_COL).getClass();
            }

            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(
                int row,
                int column)
        {
            return column == CHECK_COL;
        }
    }
}
