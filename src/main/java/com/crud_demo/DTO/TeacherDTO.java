package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeacherDTO {
    private Long id;
    private String name;
    private String subject;
    private String email;
    private String phoneNumber;
    private List<CourseInfoDTO> courses;
}

