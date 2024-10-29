package com.crud_demo.controllers;

import com.crud_demo.DTO.StudentDTO;
import com.crud_demo.DTO.StudentRegisterDTO;
import com.crud_demo.entities.Student;
import com.crud_demo.repos.UserRepository;
import com.crud_demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stud/profile")
@Tag(name = "Student APIs")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Registers a Student in Database")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRegisterDTO student){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String studentEmail = userRepository.findEmailByUserName(username);
        Student createdStudent = studentService.createStudent(student, username, studentEmail);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping
    @Operation(summary = "Gets the Student Details of the current Student")
    public ResponseEntity<StudentDTO> getStudentDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String studentEmail = userRepository.findEmailByUserName(username);
        if(studentEmail.isEmpty()) return ResponseEntity.noContent().build();
        StudentDTO studentDTO = studentService.getStudentDTO(studentEmail);

        return ResponseEntity.ok(studentDTO);
    }

    @PutMapping("/Update")
    @Operation(summary = "Updates a Student in Database")
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student studentDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long studentId = studentService.getStudentIdByUserName(username);
        if(studentId == null) return ResponseEntity.noContent().build();
        Student updatedStudent = studentService.updateStudent(studentId, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }
}

