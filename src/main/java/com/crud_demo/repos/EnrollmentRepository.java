package com.crud_demo.repos;

import com.crud_demo.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Long student);
    List<Enrollment> findByCourse(Long course);
}
