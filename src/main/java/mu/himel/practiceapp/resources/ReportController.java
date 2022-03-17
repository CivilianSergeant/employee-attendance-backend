package mu.himel.practiceapp.resources;

import mu.himel.practiceapp.entity.Employee;
import mu.himel.practiceapp.response.ExcelRow;
import mu.himel.practiceapp.service.EmployeeService;
import mu.himel.practiceapp.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;
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
                                            @RequestParam("outTime") Optional<String> outTime) throws FileNotFoundException, JRException {

        byte[] reportPrint = reportService.renderReport(employeeService.getEmployees(id.orElse("")),
                inTime.orElse(""),outTime.orElse(""));
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=report.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(reportPrint);
    }
}
