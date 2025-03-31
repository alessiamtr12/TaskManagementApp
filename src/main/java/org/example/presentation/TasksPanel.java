package org.example.presentation;

import org.example.businesslogic.TaskManagement;
import org.example.datamodel.ComplexTask;
import org.example.datamodel.SimpleTask;
import org.example.datamodel.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TasksPanel extends JPanel {
    private TaskManagement taskManagement;
    private JTable tasksTable;
    private DefaultTableModel tableModel;


    public TasksPanel(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
        prepareTable();
        prepareButton();

    }
    public void prepareTable(){
        tasksTable = new JTable();
        setLayout(new BorderLayout());
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableModel = new DefaultTableModel();


        tableModel.addColumn("Task ID");
        tableModel.addColumn("Task Duration");
        tableModel.addColumn("Task Status");
        tableModel.addColumn("Task Type");
        tableModel.addColumn("View Subtasks");

        tasksTable.setModel(tableModel);

        tasksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tasksTable.setFillsViewportHeight(true);
        tasksTable.setRowHeight(30);
        tasksTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksScrollPane.setPreferredSize(new Dimension(600, 300));
        tasksTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tasksTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(tasksTable, taskManagement));
        add(tasksScrollPane, BorderLayout.CENTER);
        loadTasks();
    }
    public void prepareButton(){
        JButton addTaskButton = new JButton("Add Task");
        add(addTaskButton, BorderLayout.SOUTH);
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] taskType = {"Simple", "Complex"};
                int option = JOptionPane.showOptionDialog(null, "Select type of task", "Add Task", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, taskType, taskType[0]);
                if(option == JOptionPane.CLOSED_OPTION) {
                    return;
                }
                String idTaskString = JOptionPane.showInputDialog("Enter Task ID");
                int idTask;
                try{
                    idTask = Integer.parseInt(idTaskString);
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Invalid Input");
                    return;
                }

                if(option == 0) {
                   manageSimpleTask(idTask);
                }
                else if(option == 1) {
                    manageComplexTask(idTask);
                }
                loadTasks();
            }
        });
    }
    public void manageSimpleTask(int idTask){
        String startHourString = JOptionPane.showInputDialog("Enter Start Hour");
        int startHour = Integer.parseInt(startHourString);
        String endHourString = JOptionPane.showInputDialog("Enter End Hour");
        int endHour = Integer.parseInt(endHourString);
        SimpleTask simpleTask = new SimpleTask( startHour, endHour, idTask , "Uncompleted");
        taskManagement.addTask(simpleTask);
    }

    public void manageComplexTask(int idTask){
        List<Task> availableTasks = taskManagement.getAvailableTasks();
        if(availableTasks == null || availableTasks.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No Tasks Available");
            return ;
        }

        JList<Task> taskJList = new JList<>(availableTasks.toArray(new Task[availableTasks.size()]));
        ComplexTask complexTask = new ComplexTask(idTask, "Uncompleted");

        int optionAddSubtasks = JOptionPane.showConfirmDialog(null, new JScrollPane(taskJList), "Select subtasks to be added", JOptionPane.OK_CANCEL_OPTION);
        if(optionAddSubtasks == JOptionPane.OK_OPTION) {
            taskJList.getSelectedValuesList().forEach(task -> {complexTask.addTask(task);});
        }

        taskManagement.addTask(complexTask);
    }
    public void loadTasks() {
        tableModel.setRowCount(0);
        taskManagement.getTasks().forEach(task -> {
            if(task instanceof ComplexTask) {
                tableModel.addRow(new Object[]{task.getIdTask(), task.estimateDuration(), task.getStatusTask(), "Complex", "View Subtasks"});
            }
            else if(task instanceof SimpleTask) {
                tableModel.addRow(new Object[]{task.getIdTask(), task.estimateDuration(), task.getStatusTask(), "Simple", "Uncompleted"});
            }
        });

    }






}
