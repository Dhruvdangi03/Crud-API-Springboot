package com.crud_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentRegisterDTO {
    private String name;
    private int age;
    private String address;
}
