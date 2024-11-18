package com.coddingshuttle.springbootweb.service;

import com.coddingshuttle.springbootweb.dto.EmployeeDTO;
import com.coddingshuttle.springbootweb.entity.EmployeeEntity;
import com.coddingshuttle.springbootweb.exception.EmployeeNotFoundException;
import com.coddingshuttle.springbootweb.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;


    public Optional<EmployeeDTO> getEmployeeById(Long id) {
//      Optional<EmployeeEntity> employeeEntity= employeeRepository.findById(id);
//      //return optional<EmployeeDTO> using map lambda exp
//       return  employeeEntity.map(employeeEntity1->mapper.map(employeeEntity, EmployeeDTO.class));
       //return mapper.map(employeeEntity, EmployeeDTO.class);
        return employeeRepository.findById(id)
                .map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList= employeeRepository.findAll();
                //Converting List to stream()
        return employeeEntityList.stream()
                //map function to go through every element in the stream and apply mapper to it
                .map(employeeEntity-> mapper.map(employeeEntity,EmployeeDTO.class))
                //Stream back to list using Terminal operations .collect
                .toList();
                //.collect(Collector.toList())
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTP) {
        //Convert DTO to Entity
       EmployeeEntity employeeEntity= mapper.map(employeeDTP,EmployeeEntity.class);
       //Save(Entity)
        EmployeeEntity savedemployeeEntity = employeeRepository.save(employeeEntity);
        //convert Entity Back to DTO and returning to Controller
        return mapper.map(savedemployeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
            isExistsInRepository(id);
        EmployeeEntity employeeEntity=mapper.map(employeeDTO,EmployeeEntity.class);
        //updating the id to employee entity if it does not exists in datasource
        employeeEntity.setId(id);
        EmployeeEntity savedemployeeEntity =  employeeRepository.save(employeeEntity);
        //convert Entity Back to DTO and returning to Controller
        return mapper.map(savedemployeeEntity,EmployeeDTO.class);
    }

    public void isExistsInRepository(Long id){
            boolean exists=employeeRepository.existsById(id);
            if(!exists) throw new EmployeeNotFoundException("Employee not found with id: "+id);
    }

    public boolean deleteEmployeeById(Long id) {

         isExistsInRepository(id);
       employeeRepository.deleteById(id);
       return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long id, Map<String, Object> updates) {
        isExistsInRepository(id);

        EmployeeEntity employeeEntity=employeeRepository.findById(id).get();

        //Find the field to be updated in the EmployeeEntity.class using Reflection utility
        //loop through map
        updates.forEach((field,value)->{
          Field fieldToBeUpdated= ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
          //Make fieldToBeUpdated public
            fieldToBeUpdated.setAccessible(true);
            //updated the field for the employeeEntity(id=2) with the value
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

          EmployeeEntity updatedEntity=  employeeRepository.save(employeeEntity);

          return mapper.map(updatedEntity,EmployeeDTO.class);




    }
}
