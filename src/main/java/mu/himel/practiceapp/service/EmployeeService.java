package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees(String id);

    void add(List<Employee> data);
}
