package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTOSimple {
    private Long id;
    private String courseName;
    private String enrollmentStatus;
}
