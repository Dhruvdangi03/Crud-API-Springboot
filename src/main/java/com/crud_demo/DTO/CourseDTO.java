package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String courseName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long teacherId;
    private List<StudentDTOSimple> students;
}

