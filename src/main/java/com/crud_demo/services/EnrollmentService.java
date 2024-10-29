package com.crud_demo.services;

import com.crud_demo.entities.Course;
import com.crud_demo.entities.Enrollment;
import com.crud_demo.exceptions.ResourceNotFoundException;
import com.crud_demo.repos.CourseRepository;
import com.crud_demo.repos.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Enrollment enrollStudent(Long studentId, Long courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

        // Check if course has not ended
        if (course.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot enroll in a course that has already ended");
        }

        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudent(studentId);
        boolean alreadyEnrolled = existingEnrollments.stream()
                .anyMatch(enrollment -> enrollment.getCourse().equals(courseId));

        if (alreadyEnrolled) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(studentId)
                .course(courseId)
                .enrollmentStatus("active")
                .dateOfEnrollment(LocalDate.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudent(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourse(courseId);
    }

    public Enrollment updateEnrollmentStatus(Long enrollmentId, String status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + enrollmentId));

        if (!status.equalsIgnoreCase("active") && !status.equalsIgnoreCase("inactive")) {
            throw new IllegalArgumentException("Invalid enrollment status");
        }

        enrollment.setEnrollmentStatus(status.toLowerCase());
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment removeStudentFromCourse(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id " + enrollmentId));

        enrollment.setEnrollmentStatus("inactive");
        return enrollmentRepository.save(enrollment);
    }
}
