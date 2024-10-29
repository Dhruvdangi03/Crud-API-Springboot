package com.crud_demo.controllers;

import com.crud_demo.DTO.StudentDTO;
import com.crud_demo.DTO.TeacherDTO;
import com.crud_demo.entities.Student;
import com.crud_demo.entities.Teacher;
import com.crud_demo.entities.User;
import com.crud_demo.services.StudentService;
import com.crud_demo.services.TeacherService;
import com.crud_demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @GetMapping
    @Operation(summary = "Gets the current Admin's Details")
    public ResponseEntity<User> getAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User admin = userService.getUserByUserName(username);
        if(admin == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all-users")
    @Operation(summary = "Get all User from Database")
    public ResponseEntity<?> allUsers() {
        List<User> users = userService.getAllUsers();
        if(users == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/students")
    @Operation(summary = "Get all Students from Database")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> list = studentService.getAllStudent();
        if(list == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "Get a Students by StudentId")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId){
        String email = studentService.getEmailByStudentId(studentId);
        if(email.isEmpty()) return ResponseEntity.noContent().build();
        StudentDTO studentDTO = studentService.getStudentDTO(email);

        return ResponseEntity.ok(studentDTO);
    }

    @DeleteMapping("/students/{studentId}")
    @Operation(summary = "Deletes a Students by studentId")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/teachers")
//    @Operation(summary = "Creates a Teacher in Database")
//    public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody Teacher teacher) {
//        Teacher createdTeacher = teacherService.createTeacher(teacher);
//        return ResponseEntity.ok(createdTeacher);
//    }

    @GetMapping("/teachers")
    @Operation(summary = "Gets all Teacher from Database")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/{teacherId}")
    @Operation(summary = "Gets a Teacher by Id")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long teacherId) {
        TeacherDTO teacherDTO = teacherService.getTeacherDTO(teacherId);

        return ResponseEntity.ok(teacherDTO);
    }

    @DeleteMapping("/teachers/{teacherId}")
    @Operation(summary = "Deletes a Teacher in Database")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }
}
