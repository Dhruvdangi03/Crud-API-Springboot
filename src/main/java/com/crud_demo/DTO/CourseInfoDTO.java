package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CourseInfoDTO {
    private Long id;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
}
