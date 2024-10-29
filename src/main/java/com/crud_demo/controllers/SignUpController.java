package com.crud_demo.controllers;

import com.crud_demo.DTO.UserDTO;
import com.crud_demo.entities.User;
import com.crud_demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-up")
@Tag(name = "Sign Up APIs")
public class SignUpController {
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Creates a new User in Database")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
