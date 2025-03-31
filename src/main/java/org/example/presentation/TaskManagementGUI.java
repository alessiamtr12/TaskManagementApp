package org.example.presentation;

import org.example.businesslogic.TaskManagement;


import javax.swing.*;
import java.awt.*;


public class TaskManagementGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private TasksPanel tasksPanel;
    private EmployeesPanel employeesPanel;
    private ViewEmployeesAndTasksPanel viewEmployeesAndTasksPanel;
    private ReportsPanel reportsPanel;
    private TaskManagement taskManagement;


    public TaskManagementGUI(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

    }
    public void prepareGUI(){
        frame = new JFrame("Task Management Application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        employeesPanel = new EmployeesPanel(taskManagement);
        tasksPanel = new TasksPanel(taskManagement);
        viewEmployeesAndTasksPanel = new ViewEmployeesAndTasksPanel(taskManagement, tasksPanel, employeesPanel);
        reportsPanel = new ReportsPanel(taskManagement);

        mainPanel.add(employeesPanel, "Employees");
        mainPanel.add(tasksPanel, "Tasks");
        mainPanel.add(viewEmployeesAndTasksPanel, "View Employees and Tasks");
        mainPanel.add(reportsPanel, "Reports");

        JPanel buttonPanel = new JPanel();
        JButton employeeButton = new JButton("Employees");
        JButton taskButton = new JButton("Tasks");
        JButton viewEmployeesButton = new JButton("View Employees and Tasks");
        JButton reportButton = new JButton("Reports");
        buttonPanel.add(employeeButton);
        buttonPanel.add(taskButton);
        buttonPanel.add(viewEmployeesButton);
        buttonPanel.add(reportButton);

       employeeButton.addActionListener(e -> showPanel("Employees"));
       taskButton.addActionListener(e -> showPanel("Tasks"));
       viewEmployeesButton.addActionListener(e -> showPanel("View Employees and Tasks"));
       reportButton.addActionListener(e -> showPanel("Reports"));

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void showPanel(String panelName){
        cardLayout.show(mainPanel, panelName);
    }

    }


