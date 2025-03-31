package org.example.businesslogic;

import org.example.dataaccess.PersistedData;
import org.example.datamodel.Employee;
import org.example.datamodel.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManagement implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Employee, List<Task>> map;
    private List<Task> tasks;
    private List<Employee> employees;
    private PersistedData persistedData;
    private static final String filenameEmployees = "src/main/resources/employeesdata.ser";
    private static final String filenameTasks = "src/main/resources/tasksdata.ser";
    private static final String filenameMap = "src/main/resources/mapdata.ser";

    public TaskManagement(PersistedData persistedData) {

        map = new HashMap<>();
        tasks = new ArrayList<>();
        employees = new ArrayList<>();

        employees =persistedData.loadEmployeeData(filenameEmployees);
        tasks = persistedData.loadTaskData(filenameTasks);
        map = persistedData.loadMapData(filenameMap);
        this.persistedData = persistedData;


    }

    public void addEmployee(Employee e) {

        employees.add(e);
        map.put(e, new ArrayList<Task>());
        persistedData.saveEmployeeData(employees, filenameEmployees);
        persistedData.saveMapData(map, filenameMap);

    }


    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {

        Employee employee = findEmployee(idEmployee);
        if(employee == null) {
            System.out.println("Employee not found");
            return;
        }
        else {
            map.get(employee).add(task);
            persistedData.saveMapData(map, filenameMap);

        }
    }

    public int calculateEmployeeWorkDuration(int idEmployee){
        Employee employee = findEmployee(idEmployee);
        if(employee==null) {
            System.out.println("Employee not found");
            return 0;
        }
        AtomicInteger totalDuration = new AtomicInteger();
        map.get(employee).forEach(task -> {
            if(task.getStatusTask().equals("Completed"))
                totalDuration.addAndGet(task.estimateDuration());
        });
        employee.setWorkDuration(totalDuration.get());
        return totalDuration.get();
    }

    public Employee findEmployee(int idEmployee) {
        final Employee[] foundEmployee = {null};
        map.keySet().forEach(key -> {
            if(key.getIdEmployee() == idEmployee)
                foundEmployee[0] = key;});
        return foundEmployee[0];
    }
    public Task findTaskForEmployee(int idTask, Employee employee) {
        final Task[] foundTask = {null};
        map.get(employee).forEach(task -> {
            if(task.getIdTask() == idTask)
                 foundTask[0] = task;});
        return foundTask[0];
    }
    public void updateStatusTaskList(int idTask, String status) {
        tasks.forEach(task -> {
            if(task.getIdTask() == idTask)
            {
                task.setStatusTask(status);
            }
        });

    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = findEmployee(idEmployee);
        if(employee==null) {
            System.out.println("Employee not found");
            return;
        }
        Task task = findTaskForEmployee(idTask, employee);
        if(task==null) {
            System.out.println("Task not found");
            return;
        }
        String status = task.getStatusTask();
        if(task.getStatusTask().equals("Completed")) {
            {
                task.setStatusTask("Uncompleted");
                status = "Uncompleted";
            }
        }
        else
        {
            task.setStatusTask("Completed");
            status = "Completed";
        }
        updateStatusTaskList(idTask, status);
        persistedData.saveMapData(map, filenameMap);
        persistedData.saveTaskData(tasks, filenameTasks);

    }

    public void addTask(Task task) {
        tasks.add(task);
        persistedData.saveTaskData(tasks, filenameTasks);
    }


    public List<Employee> getEmployees() {
        return employees;
    }
    public List<Task> getTasks(){
        return tasks;
    }

    public List<Task> getAvailableTasks(){
        List<Task> availableTasks = new ArrayList<>();
        this.getTasks().forEach(task -> {
            if (task.getStatusTask().equals("Uncompleted")) {
                availableTasks.add(task);
            }
        });

        return availableTasks;
    }



}
