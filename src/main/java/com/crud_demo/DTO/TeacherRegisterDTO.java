package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherRegisterDTO {
    private String name;
    private String subject;
    private String phoneNumber;
}