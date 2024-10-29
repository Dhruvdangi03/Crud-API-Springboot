package com.crud_demo.controllers;

import com.crud_demo.DTO.CourseDTO;
import com.crud_demo.DTO.CourseRegisterDTO;
import com.crud_demo.entities.Course;
import com.crud_demo.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/courses")
@Tag(name = "Course APIs")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "Creates a Course in Database")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseRegisterDTO course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.ok(createdCourse);
    }

    @GetMapping
    @Operation(summary = "Get all Courses from Database")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if(courses == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Gets a Course by CourseId")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId) {
        CourseDTO courseDTO = courseService.getCourseDTO(courseId);
        if(courseDTO == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(courseDTO);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Updates a Course from Database")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @Valid @RequestBody Course courseDetails){
        Course course = courseService.updateCourse(courseId, courseDetails);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Deletes a Course by CourseId")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long courseId){
//        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
