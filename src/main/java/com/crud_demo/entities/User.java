package com.crud_demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;

    @Column(name = "roles")
    private List<String> roles;
}
