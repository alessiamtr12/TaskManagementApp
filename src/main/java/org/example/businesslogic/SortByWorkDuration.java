package org.example.businesslogic;

import org.example.datamodel.Employee;

import java.util.Comparator;

public class SortByWorkDuration implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        Employee e1 = (Employee) o1;
        Employee e2 = (Employee) o2;

        if(e1.getWorkDuration() < e2.getWorkDuration())
            return -1;
        else if(e1.getWorkDuration() == e2.getWorkDuration())
            return 0;
        else return 1;

    }
}
