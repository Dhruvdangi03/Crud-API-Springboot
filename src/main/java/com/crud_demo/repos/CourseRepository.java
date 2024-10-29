package com.crud_demo.repos;

import com.crud_demo.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "SELECT s.id AS student_id, s.name AS student_name, e.enrollment_status " +
            "FROM course c " +
            "JOIN enrollment e ON c.id = e.course " +
            "JOIN student s ON e.course = s.id " +
            "WHERE c.id = :courseId AND s.is_deleted = false AND c.is_deleted = false", nativeQuery = true)
    List<Object[]> fetchStudentDTOSimpleNative(@PathVariable Long courseId);

    @Query(value = "SELECT * FROM course c WHERE c.is_deleted = false", nativeQuery = true)
    List<Course> findAll();

}
