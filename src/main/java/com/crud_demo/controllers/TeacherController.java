package com.crud_demo.controllers;

import com.crud_demo.DTO.TeacherDTO;
import com.crud_demo.DTO.TeacherRegisterDTO;
import com.crud_demo.entities.Teacher;
import com.crud_demo.repos.UserRepository;
import com.crud_demo.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teach/profile")
@Tag(name = "Teacher APIs")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Creates a Teacher in Database")
    public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody TeacherRegisterDTO teacher) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String teacherEmail = userRepository.findEmailByUserName(username);
        Teacher createdTeacher = teacherService.createTeacher(teacher, username, teacherEmail);
        return ResponseEntity.ok(createdTeacher);
    }

    @GetMapping
    @Operation(summary = "Gets the Teacher's Details")
    public ResponseEntity<TeacherDTO> getTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long teacherId = teacherService.getIdByUserName(username);
        if(teacherId == null) return ResponseEntity.noContent().build();
        TeacherDTO teacherDTO = teacherService.getTeacherDTO(teacherId);

        return ResponseEntity.ok(teacherDTO);
    }

    @PutMapping("Update")
    @Operation(summary = "Updates a Teacher in Database")
    public ResponseEntity<Teacher> updateTeacher(@Valid @RequestBody Teacher teacherDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long teacherId = teacherService.getIdByUserName(username);
        Teacher updatedTeacher = teacherService.updateTeacher(teacherId, teacherDetails);
        return ResponseEntity.ok(updatedTeacher);
    }
}
