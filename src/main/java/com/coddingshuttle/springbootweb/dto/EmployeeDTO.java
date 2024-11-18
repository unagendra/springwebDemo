package com.coddingshuttle.springbootweb.dto;

import com.coddingshuttle.springbootweb.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of the employee cannot be blank")
    @Size(min=3,max=10,message = "Number of characters in name should be in the range: [3, 10]")
    private String name;

    @Email
    @NotBlank(message = "Email of the employee cannot be blank")
    private String email;


    @Min(value = 18,message ="Age of Employee cannot be less than 18" )
    @Max(value = 80,message = "Age of Employee cannot be greater than 80")
    @NotNull(message = "Age of the employee cannot be blank")
    private Integer age;

   // @Pattern(regexp = "^(ADMIN|USER)$")
    @EmployeeRoleValidation
    private String role; //ADMIN, USER

    @NotNull(message = "salary of the employee cannot be blank")
    //@Digits(integer = 6,fraction = 2,message = "The salary can be in the form XXXXX.YY")
    @DecimalMin(value = "100.50")
    @DecimalMax(value = "100000")
    @Positive(message = "Salary of Employee should be positive")
    private Double salary;

    @NotNull(message = "Date of Joining of the employee cannot be blank")
    @PastOrPresent(message = "DateOfJoining field in Employee cannot be in the future")
    private LocalDate dateOfJoining;

//    @JsonProperty("isActive")
//    @AssertTrue(message = "Employee should be active")
//    private Boolean isActive;

//    public EmployeeDTO() {
//
//    }

//    public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.dateOfJoining = dateOfJoining;
//        this.isActive = isActive;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public LocalDate getDateOfJoining() {
//        return dateOfJoining;
//    }
//
//    public void setDateOfJoining(LocalDate dateOfJoining) {
//        this.dateOfJoining = dateOfJoining;
//    }
//
//    public Boolean getisActive() {
//        return isActive;
//    }
//
//    public void setisActive(Boolean active) {
//        isActive = active;
//    }
}
