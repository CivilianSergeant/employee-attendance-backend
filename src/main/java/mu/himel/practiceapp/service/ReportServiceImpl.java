package mu.himel.practiceapp.service;

import mu.himel.practiceapp.entity.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{

    @Override
    public byte[] renderReport(List<Employee> employeeList, String inTime, String outTime) throws IOException, JRException {
        employeeList.stream().map(e->{
            e.setInColor(isAfterTime(e.getInTime(),inTime)? "true":"false");
            e.setOutColor(isBeforeTime(e.getOutTime(),outTime)? "true": "false");
            return e;
        }).collect(Collectors.toList());


        JasperReport jasperReport = JasperCompileManager.compileReport(new ClassPathResource("report.jrxml").getInputStream());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
        return data;
    }

    private Boolean isAfterTime(String cTime, String refTime){
        String hour="00";
        String[] timePostfix = cTime.split(" ");
        String[] hoursMin = timePostfix[0].split(":");
        hour = hoursMin[0];
        if(hoursMin[0].equalsIgnoreCase("12"))
            hour = "00";

        if(timePostfix[1].equalsIgnoreCase("PM")){
            hour = String.valueOf(Integer.parseInt(hoursMin[0],10)+12);

        }
        String hourStr = String.valueOf(hour)+":"+hoursMin[1];
        LocalTime t1 = LocalTime.parse(refTime);
        LocalTime t2 = LocalTime.parse(hourStr);
        return t2.isAfter(t1);
    }

    private Boolean isBeforeTime(String cTime, String refTime){
        String hour="00";
        String[] timePostfix = cTime.split(" ");
        String[] hoursMin = timePostfix[0].split(":");
        hour = hoursMin[0];
        if(hoursMin[0].equalsIgnoreCase("12"))
            hour = "00";

        if(timePostfix[1].equalsIgnoreCase("PM")){
            hour = String.valueOf(Integer.parseInt(hoursMin[0],10)+12);

        }
        String hourStr = String.valueOf(hour)+":"+hoursMin[1];
        LocalTime t1 = LocalTime.parse(refTime);
        LocalTime t2 = LocalTime.parse(hourStr);
        return t2.isBefore(t1);
    }
}
