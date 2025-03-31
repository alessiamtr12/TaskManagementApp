package org.example.presentation;

import org.example.businesslogic.TaskManagement;

import org.example.datamodel.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeesPanel extends JPanel {
    private TaskManagement taskManagement;
    private JTable employeesTable;
    private DefaultTableModel tableModel;



    public EmployeesPanel(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
        prepareTable();
        loadEmployees();
        prepareButtons();

    }
    public void prepareTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");
        tableModel.addColumn("Work Duration");
        setLayout(new BorderLayout());

        employeesTable = new JTable();
        employeesTable.setModel(tableModel);

        employeesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeesTable.setFillsViewportHeight(true);
        employeesTable.setRowHeight(30);
        employeesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane employeesScrollPane = new JScrollPane(employeesTable);
        employeesScrollPane.setPreferredSize(new Dimension(600, 300));
        add(employeesScrollPane, BorderLayout.CENTER);
    }
    public void prepareButtons() {
        JButton addEmployeeButton = new JButton("Add Employee");
        add(addEmployeeButton, BorderLayout.SOUTH);
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idEmployeeString = JOptionPane.showInputDialog("Enter Employee ID");
                String name = JOptionPane.showInputDialog("Enter Employee Name");


                try{
                    int idEmployee = Integer.parseInt(idEmployeeString);
                    Employee employee = new Employee(idEmployee, name);
                    taskManagement.addEmployee(employee);
                    loadEmployees();

                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Invalid Input");
                    return;
                }
            }

        });

    }
    public void loadEmployees() {
        tableModel.setRowCount(0);
        taskManagement.getEmployees().forEach(employee -> {
            tableModel.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee())
            });
            employee.setWorkDuration(taskManagement.calculateEmployeeWorkDuration(employee.getIdEmployee()));
        });



    }



}
