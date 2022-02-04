package com.gangzaragas.employeeinformationsystem.controller;

import com.gangzaragas.employeeinformationsystem.exception.ResourceNotFoundException;
import com.gangzaragas.employeeinformationsystem.model.Employee;
import com.gangzaragas.employeeinformationsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeId(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee not exists with id" + id)
        );
        return ResponseEntity.ok(employee);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployeeId(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        var checkEmployee = employeeRepository.findById(id);
        if (checkEmployee.isEmpty()) {
            try {
                throw new Exception("Not found Employee Id with id" + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Employee employeeUpdate = employeeRepository.findById(id).get();

        employeeUpdate.setFirstName(employeeDetails.getFirstName());
        employeeUpdate.setLastName(employeeDetails.getLastName());
        employeeUpdate.setEmailId(employeeDetails.getEmailId());

        Employee savingData = employeeRepository.save(employeeUpdate);

        return ResponseEntity.ok(Employee.builder()
                .id(savingData.getId())
                .firstName(savingData.getFirstName())
                .lastName(savingData.getLastName())
                .emailId(savingData.getEmailId())
                .build());
    }

    @DeleteMapping("employees/{id}")
    // public ResponseEntity<String,Boolean> deleteEmployee(@PathVariable Long id) {
      /*  var checkEmployee = employeeRepository.findById(id);
        if (checkEmployee.isEmpty()) {
            try {
                throw new Exception("Can't delete employee with id " + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            employeeRepository.deleteById(id);
        }
        return (ResponseEntity<Employee>) ResponseEntity.status(HttpStatus.ACCEPTED);
    }*/
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
