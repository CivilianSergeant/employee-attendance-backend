package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;
import mu.himel.practiceapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees(String id) {
        if(id.isEmpty()){
            return employeeRepository.findAll();
        }
        return employeeRepository.findAllByEmpId(id);
    }

    @Override
    @Transactional
    public void add(List<Employee> data) {
        employeeRepository.saveAll(data);
    }
}
