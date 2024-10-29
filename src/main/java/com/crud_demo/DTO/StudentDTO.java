package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String address;
    private LocalDate dateOfEnrollment;
    private List<CourseDTOSimple> courses;
}

