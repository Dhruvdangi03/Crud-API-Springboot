package com.crud_demo.repos;

import com.crud_demo.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "SELECT * FROM teacher t WHERE t.is_deleted = false", nativeQuery = true)
    List<Teacher> findAll();

    @Query(value = "SELECT t.id FROM teacher t WHERE t.username = :name AND t.is_deleted = false", nativeQuery = true)
    Long findTeacherIdByUserName(@Param("name") String name);
}
