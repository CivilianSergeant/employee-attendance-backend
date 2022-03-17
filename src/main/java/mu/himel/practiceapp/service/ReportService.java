package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {

    byte[] renderReport(List<Employee> employeeList, String inTime, String outTime) throws FileNotFoundException, JRException;
}
