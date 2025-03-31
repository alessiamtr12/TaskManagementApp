package org.example.presentation;

import org.example.businesslogic.TaskManagement;
import org.example.businesslogic.Utility;
import org.example.datamodel.Employee;
import org.example.datamodel.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewEmployeesAndTasksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaskManagement taskManagement;
    private JButton assignButton;
    private JButton modifyTaskStatusButton;
    private TasksPanel tasksPanel;
    private EmployeesPanel employeesPanel;

    public ViewEmployeesAndTasksPanel(TaskManagement taskManagement, TasksPanel tasksPanel, EmployeesPanel employeesPanel) {
        this.taskManagement = taskManagement;
        this.tasksPanel = tasksPanel;
        this.employeesPanel = employeesPanel;

        prepareTable();
        employeesPanel.loadEmployees();
        prepareButtons();
    }
    public void prepareTable(){
        table = new JTable();
        model = new DefaultTableModel();
        setLayout(new BorderLayout());

        model.addColumn("Employee ID");
        model.addColumn("Employee Name");
        model.addColumn("Task ID");
        model.addColumn("Task Status");
        model.addColumn("Task Duration");

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        loadTableData();

        add(scrollPane, BorderLayout.CENTER);
    }

    public void prepareButtons(){
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        assignButton = new JButton("Assign");
        modifyTaskStatusButton = new JButton("Modify Task Status");
        buttonPanel.add(assignButton);
        buttonPanel.add(modifyTaskStatusButton);

        add(buttonPanel, BorderLayout.SOUTH);

        assignButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageAssign(employeesPanel);
            }

        });

        modifyTaskStatusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageModify(employeesPanel, tasksPanel);
            }

        });
    }

    public void manageModify(EmployeesPanel employeesPanel, TasksPanel tasksPanel){
            String idEmployeeString = JOptionPane.showInputDialog("Enter Employee ID");
            int idEmployee;
            try{
                idEmployee = Integer.parseInt(idEmployeeString);
                if(taskManagement.findEmployee(idEmployee) == null){
                    JOptionPane.showMessageDialog(ViewEmployeesAndTasksPanel.this, "Employee does not exist");
                    return;
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Invalid Input");
                return;
            }
            List<Task> employeeTasks = taskManagement.getMap().get(taskManagement.findEmployee(idEmployee));
            JList<Task> taskJList = new JList<>(employeeTasks.toArray(new Task[employeeTasks.size()]));

            int assignTask = JOptionPane.showConfirmDialog(null, new JScrollPane(taskJList), "Select tasks to modify status", JOptionPane.OK_CANCEL_OPTION);
            if(assignTask == JOptionPane.OK_OPTION) {
                taskJList.getSelectedValuesList().forEach(task -> {
                    taskManagement.modifyTaskStatus(idEmployee, task.getIdTask());});
            }
            loadTableData();
            tasksPanel.loadTasks();
            employeesPanel.loadEmployees();


        }


    public void manageAssign(EmployeesPanel employeesPanel){
        String idEmployeeString = JOptionPane.showInputDialog("Enter Employee ID to assign task to");
        int idEmployee;
        try{
            idEmployee = Integer.parseInt(idEmployeeString);
            if(taskManagement.findEmployee(idEmployee) == null){
                JOptionPane.showMessageDialog(ViewEmployeesAndTasksPanel.this, "Employee does not exist");
                return;
            }
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null,"Invalid Input");
            return;
        }
        List<Task> availableTasks = taskManagement.getAvailableTasks();
        JList<Task> taskJList = new JList<>(availableTasks.toArray(new Task[availableTasks.size()]));

        int assignTask = JOptionPane.showConfirmDialog(null, new JScrollPane(taskJList), "Select tasks to assign", JOptionPane.OK_CANCEL_OPTION);
        if(assignTask == JOptionPane.OK_OPTION) {
            taskJList.getSelectedValuesList().forEach(task -> {
                taskManagement.assignTaskToEmployee(idEmployee, task);

            });
        }
        loadTableData();
        employeesPanel.loadEmployees();
    }
    public void loadTableData() {

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for(Map.Entry<Employee, List<Task>> entry : taskManagement.getMap().entrySet()){
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            if(tasks == null || tasks.isEmpty()){
                model.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), "-", "-", "0"});
            }
            else{
                tasks.forEach(task -> {
                   model.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), task.getIdTask(), task.getStatusTask(), task.estimateDuration()});
                });
            }
        }




    }

    }


