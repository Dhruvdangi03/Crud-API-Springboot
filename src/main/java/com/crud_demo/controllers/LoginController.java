package com.crud_demo.controllers;

import com.crud_demo.DTO.LoginDTO;
import com.crud_demo.DTO.UserDTO;
import com.crud_demo.entities.User;
import com.crud_demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userLogin")
@Tag(name = "Login APIs")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Login User in the API")
    public ResponseEntity<Boolean> loginUser(@Valid @RequestBody LoginDTO user){
        boolean loggedIn = userService.LoginUser(user);
        return ResponseEntity.ok(loggedIn);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change the password of User in Database")
    public ResponseEntity<User> updateUser(@Valid @RequestBody LoginDTO userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User updatedUser = userService.getUserByUserName(username);
        updatedUser.setPassword(userDetails.getPassword());
        return ResponseEntity.ok(updatedUser);
    }
}
