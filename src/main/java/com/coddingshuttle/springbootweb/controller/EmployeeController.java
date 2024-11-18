package com.coddingshuttle.springbootweb.controller;

import com.coddingshuttle.springbootweb.dto.EmployeeDTO;
import com.coddingshuttle.springbootweb.entity.EmployeeEntity;
import com.coddingshuttle.springbootweb.exception.EmployeeNotFoundException;
import com.coddingshuttle.springbootweb.repository.EmployeeRepository;
import com.coddingshuttle.springbootweb.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
@AllArgsConstructor
public class EmployeeController {

 private final EmployeeService employeeService;


    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTO= employeeService.getEmployeeById(id);

        //Optional<something> use .map lambda to handle null checks and return Response entity
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: "+id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                                @RequestParam(required = false) String sortBy) {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@Valid @RequestBody EmployeeDTO employee){

        return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeDTO> updateEmployeeById(@Valid @RequestBody EmployeeDTO employee,@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(id,employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) {
        boolean gotDeleted = employeeService.deleteEmployeeById(id);
        if (gotDeleted)
            return  ResponseEntity.ok(gotDeleted);
        return  ResponseEntity.notFound().build();
    }

@PatchMapping("/{id}")
      public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable Long id) {
        EmployeeDTO employeeDTO=employeeService.updatePartialEmployeeById(id,updates);
        if (employeeDTO==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }


}
