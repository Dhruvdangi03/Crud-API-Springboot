package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CourseRegisterDTO {
    private Long id;
    private String courseName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long teacherId;
}
