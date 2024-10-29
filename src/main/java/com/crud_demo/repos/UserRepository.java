package com.crud_demo.repos;

import com.crud_demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM app_user a WHERE a.username = :name", nativeQuery = true)
    User getUserByUsername(@Param("name") String name);

    @Query(value = "SELECT a.email FROM app_user a WHERE a.username = :name", nativeQuery = true)
    String findEmailByUserName(@Param("name") String name);
}
