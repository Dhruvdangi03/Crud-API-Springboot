package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDTOSimple {
    private Long id;
    private String name;
    private String enrollmentStatus;
}
