package org.example.presentation;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Object type = table.getValueAt(row, 3);
        if(type.toString().equals("Complex"))
        {
            setText("View Subtasks");
        }
        else
            setText("");
        return this;
    }
}
