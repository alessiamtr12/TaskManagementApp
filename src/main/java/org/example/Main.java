package org.example;

import org.example.businesslogic.TaskManagement;
import org.example.businesslogic.Utility;
import org.example.dataaccess.PersistedData;

import org.example.presentation.TaskManagementGUI;

public class Main {
    public static void main(String[] args) {

        PersistedData persistedData = new PersistedData();
        TaskManagement taskManagement = new TaskManagement(persistedData);
        Utility utility= new Utility(taskManagement);

        TaskManagementGUI taskManagementGUI = new TaskManagementGUI(taskManagement);
        taskManagementGUI.prepareGUI();

    }
}