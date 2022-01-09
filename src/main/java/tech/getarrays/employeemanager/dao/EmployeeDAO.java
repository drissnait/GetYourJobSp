package tech.getarrays.employeemanager.dao;

import tech.getarrays.employeemanager.model.Employee;

import java.util.List;


public interface EmployeeDAO {

    public List<Employee> findAll();
}