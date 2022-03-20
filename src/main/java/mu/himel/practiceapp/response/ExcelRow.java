package mu.himel.practiceapp.response;

import mu.himel.practiceapp.entity.Employee;

import java.util.List;

public class ExcelRow{
    private List<Employee> employees;


    public ExcelRow(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


}
