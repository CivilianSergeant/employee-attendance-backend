package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{

    @Override
    public byte[] renderReport(List<Employee> employeeList, String inTime, String outTime) throws FileNotFoundException, JRException {
        employeeList.stream().map(e->{

            e.setInColor("red");
            return e;
        }).collect(Collectors.toList());
        File file = ResourceUtils.getFile("classpath:report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
        return data;
    }
}
