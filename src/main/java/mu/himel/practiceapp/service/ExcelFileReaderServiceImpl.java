package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelFileReaderServiceImpl implements FileReaderService{

    @Override
    public List<Employee> readExcel(String filePath) {

        List<Employee> employees = new ArrayList<>();

        File file = new File(filePath);
        try {
            Workbook workBook = WorkbookFactory.create(file);
            Sheet sheet = workBook.getSheetAt(0);

            for(Row row : sheet){
                if(row == null){
                    break;
                }
                if(row.getRowNum()==0){
                    continue;
                }

                System.out.println(row.getLastCellNum());
                Employee employee = new Employee();
                DataFormatter formatter = new DataFormatter();
                String date = formatter.formatCellValue(row.getCell(1));
                String Id = formatter.formatCellValue(row.getCell(3));
                String hour = formatter.formatCellValue(row.getCell(8));
                String month = formatter.formatCellValue(row.getCell(0));
                String day = formatter.formatCellValue(row.getCell(2));
                String employeeName = formatter.formatCellValue(row.getCell(4));
                String department = formatter.formatCellValue(row.getCell(5));
                String inTime = formatter.formatCellValue(row.getCell(6));
                String outTime = formatter.formatCellValue(row.getCell(7));

                employee.setMonth(month);
                employee.setDate(date);
                employee.setDay(day);
                employee.setEmpId(Id);
                employee.setEmployee(employeeName);
                employee.setDepartment(department);
                employee.setInTime(inTime);
                employee.setOutTime(outTime);
                employee.setHoursOfWork(hour);
                employees.add(employee);


            }

            return employees;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
