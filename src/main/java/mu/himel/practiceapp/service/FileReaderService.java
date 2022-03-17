package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;

import java.util.List;

public interface FileReaderService {

    List<Employee> readExcel(String filePath);
}
