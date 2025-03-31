package org.example.dataaccess;

import org.example.datamodel.Employee;
import org.example.datamodel.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistedData {

    public void saveEmployeeData(List<Employee> employees, String filename){
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
                oos.writeObject(employees);

            }

        }

        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveMapData(Map<Employee, List<Task>> map, String filename){
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
                oos.writeObject(map);

            }

        }

        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveTaskData(List<Task> tasks, String filename){
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(tasks);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public List<Task> loadTaskData(String filename){
        File file = new File(filename);
        if(!file.exists() || file.length() == 0){
            return new ArrayList<Task>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            List<Task> tasks = (List<Task>) ois.readObject();
            return tasks;

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }

    }
    public List<Employee> loadEmployeeData(String filename){
        File file = new File(filename);
        if(!file.exists() || file.length() == 0){
            return new ArrayList<Employee>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            List<Employee> employees = (List<Employee>) ois.readObject();

            return employees;

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }

    }


    public Map<Employee, List<Task>> loadMapData(String filename){
        File file = new File(filename);
        if(!file.exists() || file.length() == 0){
            return new HashMap<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            Map<Employee, List<Task>> map = (Map<Employee, List<Task>>) ois.readObject();
            return map;

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
