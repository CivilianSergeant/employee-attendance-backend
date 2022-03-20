package mu.himel.practiceapp.resources;

import mu.himel.practiceapp.service.EmployeeService;
import mu.himel.practiceapp.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> getReport(@RequestParam("id") Optional<String> id,
                                            @RequestParam("inTime") Optional<String> inTime,
                                            @RequestParam("outTime") Optional<String> outTime) throws IOException, JRException {

        byte[] reportPrint = reportService.renderReport(employeeService.getEmployees(id.orElse("")),
                inTime.orElse("23:59"),outTime.orElse("00:00"));
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=report.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(reportPrint);
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
