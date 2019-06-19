package uk.markausten;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

class ListShipsAtStation extends JDialog
{
    private Object[][] data;
    private String source;
    private DataModel model;

    ListShipsAtStation(String source)
    {
        this.source = source;
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        setModalityType(ModalityType.APPLICATION_MODAL);

        String[] columns = {
                "Ship",
                "Cost"
        };

        populateData(this.source);

        model = new DataModel(data, columns);
        JTable tblShips = new JTable(model);

        tblShips.setShowGrid(true);
        tblShips.setGridColor(Color.BLUE);

        tblShips
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(new NumberTableCellRenderer());

        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JScrollPane scroll = new JScrollPane(tblShips);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(scroll, gbc);

        gbc.gridy = 1;
        JButton buttonOK = new JButton("Done");
        contentPane.add(buttonOK, gbc);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK()
    {
        dispose();
    }

    void populateData(String source)
    {
        List<Object[]> rows = (new Database()).getShipVendor(source);

        data = new Object[rows.size()][2];

        int i = 0;

        for (Object[] s : rows)
        {
            Object[] row = new Object[2];

            row[0] = s[0];
            row[1] = s[1];

            data[i++] = row;
        }
    }

    class DataModel extends DefaultTableModel
    {

        DataModel(
                Object[][] data,
                Object[] columnNames)
        {
            super(data, columnNames);
        }
    }

    class NumberTableCellRenderer extends DefaultTableCellRenderer
    {

        NumberTableCellRenderer()
        {
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
        {
            if (column == 1)
            {
                long numberValue = Utils.convertStringToLong((String) value);

                value = NumberFormat
                        .getNumberInstance()
                        .format(numberValue);
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

    }
}
