package com.crud_demo.services;

import com.crud_demo.DTO.LoginDTO;
import com.crud_demo.DTO.UserDTO;
import com.crud_demo.entities.User;
import com.crud_demo.exceptions.ResourceNotFoundException;
import com.crud_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRoles(new ArrayList<>());
        user.getRoles().add(userDTO.getRole());
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with" + id));

        return user;
    }

    public User updateUser(Long id, User userDetails){
        User user = getUserById(id);

        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRoles(null);
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setRoles(userDetails.getRoles());

        return userRepository.save(user);
    }

    public User getUserByUserName(String username){
        return userRepository.getUserByUsername(username);
    }

    public boolean LoginUser(LoginDTO user){
        User userInDb = userRepository.getUserByUsername(user.getUsername());
        return passwordEncoder.matches(user.getPassword(), userInDb.getPassword());
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
