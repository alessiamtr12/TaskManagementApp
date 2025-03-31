package org.example.presentation;

import org.example.businesslogic.TaskManagement;
import org.example.datamodel.ComplexTask;
import org.example.datamodel.Task;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private JTable table;
    private TaskManagement taskManagement;
    private int selectedRow;

    public ButtonEditor(JTable table, TaskManagement taskManagement) {
        this.table = table;
        this.taskManagement = taskManagement;
        button = new JButton("View Subtasks");
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSubtasks(selectedRow);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        selectedRow = row;
        Object type = table.getValueAt(row, 3);
        if(type.toString().equals("Complex"))
        {
            button.setText("View Subtasks");
        }
        else
            button.setText("");

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "View Subtasks";
    }


    public void viewSubtasks(int selectedRow) {
        int taskId = (int) table.getValueAt(selectedRow, 0);
        taskManagement.getTasks().forEach(task -> {
            if(task.getIdTask() == taskId && task instanceof ComplexTask){
                StringBuilder subtasks = new StringBuilder("Subtasks:\n");
                ComplexTask complexTask = (ComplexTask) task;
                complexTask.getTasks().forEach(subtask -> { subtasks.append("TaskId: ").append(subtask.getIdTask()).append(" Duration: ").append(subtask.estimateDuration()).append(" Status: ").append(subtask.getStatusTask()).append("\n");
                });
                JOptionPane.showMessageDialog(null, subtasks.toString(), "Subtasks", JOptionPane.INFORMATION_MESSAGE);
            }
                    return;
        }
        );
    }


}
