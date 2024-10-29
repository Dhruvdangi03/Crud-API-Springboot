package com.crud_demo.repos;

import com.crud_demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT c.id AS course_id, c.name AS course_name, e.enrollment_status " +
            "FROM student s " +
            "JOIN enrollment e ON s.id = e.student " +
            "JOIN course c ON e.course = c.id " +
            "WHERE s.id = :studentId AND s.is_deleted = false AND c.is_deleted = false", nativeQuery = true)
    List<Object[]> fetchCourseDTOSimpleNative(@PathVariable Long studentId);

    @Query(value = "SELECT * FROM student s WHERE s.is_deleted = false AND s.is_deleted = false", nativeQuery = true)
    List<Student> findAll();

    @Query(value = "SELECT * FROM student s WHERE s.email = :mail AND s.is_deleted = false", nativeQuery = true)
    Student findStudentByEmail(@Param("mail") String mail);

    @Query(value = "SELECT s.email FROM student s WHERE s.id = :studentId AND s.is_deleted = false", nativeQuery = true)
    String findEmailByStudentId(@Param("studentId") Long studentId);

    @Query(value = "SELECT s.id FROM student s WHERE s.username = :name AND s.is_deleted = false", nativeQuery = true)
    Long findStudentIdByUserName(@Param("name") String name);
}

