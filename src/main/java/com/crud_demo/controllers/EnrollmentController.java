package com.crud_demo.controllers;

import com.crud_demo.DTO.EnrollmentRequest;
import com.crud_demo.entities.Enrollment;
import com.crud_demo.services.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/enrollments")
@Tag(name = "Enrollment APIs")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Enrolls a Student in a Course")
    public ResponseEntity<Enrollment> enrollStudent(@Valid @RequestBody EnrollmentRequest enrollmentRequest) {
        Enrollment enrollment = enrollmentService.enrollStudent(enrollmentRequest.getStudentId(), enrollmentRequest.getCourseId());
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping
    @Operation(summary = "Gets Enrollments by StudentId and CourseId")
    public ResponseEntity<List<Enrollment>> getAllEnrollments(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId) {

        List<Enrollment> enrollments;

        if (studentId != null) {
            enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        } else if (courseId != null) {
            enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        } else {
            enrollments = enrollmentService.getAllEnrollments();
        }

        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("/{EnrollmentId}")
    @Operation(summary = "Updates Enrollment by EnrollmentId")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(@PathVariable Long EnrollmentId, @RequestParam String status) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollmentStatus(EnrollmentId, status);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes Enrollment by EnrollmentId")
    public ResponseEntity<Enrollment> removeStudentFromCourse(@PathVariable Long id) {
        Enrollment updatedEnrollment = enrollmentService.removeStudentFromCourse(id);
        return ResponseEntity.ok(updatedEnrollment);
    }
}
