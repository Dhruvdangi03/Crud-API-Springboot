package com.crud_demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is Mandatory")
    private String name;

    @NotBlank(message = "username is Mandatory")
    private String username;

    private int age;

    @Email(message = "Email should be Valid")
    @NotBlank(message = "Email is Mandatory")
    private String email;

    private String address;

    @Column(name = "date_of_enrollment", nullable = false)
    private LocalDate dateOfEnrollment;

    @Column(name = "time_of_creation", nullable = false)
    private LocalDate timeOfCreation;

    @Column(name = "time_of_update", nullable = false)
    private LocalDate timeOfUpdate;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();
}
