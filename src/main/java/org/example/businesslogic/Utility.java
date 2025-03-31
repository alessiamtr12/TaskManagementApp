package org.example.businesslogic;

import org.example.dataaccess.PersistedData;
import org.example.datamodel.Employee;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    private TaskManagement taskManagement;

    public Utility(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
    }
    public Map<String, Map<String, Integer>> tasksStatusEmployee(int idEmployee) {
        Map<String, Map<String, Integer>> work = new HashMap<>();
        Employee employee = taskManagement.findEmployee(idEmployee);
        if(employee == null) {
            return work;
        }
        AtomicInteger nbCompleted = new AtomicInteger(0);
        taskManagement.getMap().get(employee).forEach(task -> {
            if (task.getStatusTask().equals("Completed")) {
                nbCompleted.getAndIncrement();
            }
        });

        AtomicInteger nbUnCompleted = new AtomicInteger(0);
        taskManagement.getMap().get(employee).forEach(task -> {
            if (task.getStatusTask().equals("Uncompleted")) {
                nbUnCompleted.getAndIncrement();
            }
        });
        Map<String, Integer> tasksStatus = new HashMap<>();
        tasksStatus.put("Completed", nbCompleted.get());
        tasksStatus.put("Uncompleted", nbUnCompleted.get());
        work.put(employee.getName(), tasksStatus);
        return work;

    }
   public List<String> filterEmployees() {
        List<String> list = new ArrayList<>();
        List<Employee> sortedEmployees = sortEmployeesByWorkDuration();
        sortedEmployees.forEach(employee -> {
            if(employee.getWorkDuration() > 40)
                list.add(employee.getName());
        });
        return list;

   }

   public List<Employee> sortEmployeesByWorkDuration(){
        List<Employee> list = new ArrayList<>();
        taskManagement.getEmployees().forEach(employee -> {
            list.add(employee);
        });
        Comparator comp = new SortByWorkDuration();
        Collections.sort(list, comp);
        return list;
   }


}
