package org.example.presentation;

import org.example.businesslogic.TaskManagement;
import org.example.businesslogic.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ReportsPanel extends JPanel {
    private TaskManagement taskManagement;
    private JButton workButton;
    private JButton filterButton;

    public ReportsPanel(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
        prepareButtons();

    }
    public void prepareButtons() {
        workButton = new JButton("View updated work status for each employee");
        filterButton = new JButton("Filter employees with work duration greater than 40");
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(workButton, BorderLayout.WEST);
        buttonPanel.add(filterButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        workButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = getComponents();
                Arrays.asList(components).forEach(component -> {
                            if (component instanceof JScrollPane)
                                remove((JScrollPane) component);
                        }

                ); prepareTableWork();
            }
        });

        filterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = getComponents();
                Arrays.asList(components).forEach(component -> {
                            if (component instanceof JScrollPane)
                                remove((JScrollPane) component);
                        }
                );
                prepareTableFilter();
            }
        });

    }


    public void loadWork(DefaultTableModel model) {
        model.setRowCount(0);

        Utility utility = new Utility(taskManagement);

        taskManagement.getEmployees().forEach(employee -> {
            Map<String, Map<String, Integer>> tasks = utility.tasksStatusEmployee(employee.getIdEmployee());
            model.addRow(new Object[]{employee.getName(), tasks.get(employee.getName()).get("Completed"), tasks.get(employee.getName()).get("Uncompleted")});
        });

    }
    public void prepareTableWork(){

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Employee name");
        model.addColumn("Number of completed tasks");
        model.addColumn("Number of uncompleted tasks");
        JTable table =new JTable();
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane workScrollPane = new JScrollPane(table);
        workScrollPane.setPreferredSize(new Dimension(600, 300));
        add(workScrollPane, BorderLayout.CENTER);
        loadWork(model);

        revalidate();
        repaint();
    }
    public void prepareTableFilter(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Employee name");

        JTable table =new JTable();
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane filterScrollPane = new JScrollPane(table);
        filterScrollPane.setPreferredSize(new Dimension(300, 300));
        add(filterScrollPane, BorderLayout.CENTER);
        loadFilter(model);

        revalidate();
        repaint();

    }
    public void loadFilter(DefaultTableModel model) {
        model.setRowCount(0);

        Utility utility = new Utility(taskManagement);
        List<String> list = utility.filterEmployees();
        list.forEach(name -> {
           model.addRow(new Object[]{name});
        });



    }



}
