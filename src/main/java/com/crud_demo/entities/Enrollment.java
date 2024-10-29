package com.crud_demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "enrollment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "student_id", nullable = false)
    private Long student;

    @JoinColumn(name = "course_id", nullable = false)
    private Long course;

    @NotBlank(message = "Enrollment status is mandatory")
    @Column(name = "enrollment_status", nullable = false)
    private String enrollmentStatus; // Should be 'active' or 'inactive'

    @Column(name = "date_of_enrollment", nullable = false)
    private LocalDate dateOfEnrollment;
}
