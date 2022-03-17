package mu.himel.practiceapp.resources;

import mu.himel.practiceapp.entity.Employee;
import mu.himel.practiceapp.service.EmployeeService;
import mu.himel.practiceapp.service.FileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FileReaderService fileReaderService;

    @GetMapping
    public ResponseEntity<?> getEmployees(@RequestParam("id") Optional<String> id){
        return new ResponseEntity<>(
            employeeService.getEmployees(id.orElse("")),
                HttpStatus.OK
        );
    }

    @GetMapping("/load-data")
    public ResponseEntity<?> getEmployeesFromExcel(){
        return new ResponseEntity<>(
            fileReaderService.readExcel("uploads/MOCK_DATA.xlsx"),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<?> save(){
        try {
            employeeService.add(fileReaderService.readExcel("uploads/MOCK_DATA.xlsx"));
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
