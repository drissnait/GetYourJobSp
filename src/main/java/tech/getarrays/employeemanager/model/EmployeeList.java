package tech.getarrays.employeemanager.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {
    private List<Employee> employees;

    public EmployeeList() {
        employees = new ArrayList<>();
    }
    public List<Employee> getEmployees(){
        return employees;
    }

    // standard constructor and getter/setter
}
