package com.crud_demo.config;

import com.crud_demo.entities.User;
import com.crud_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(username);

        if(user == null) throw new UsernameNotFoundException("This User does not exist");

        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
    }
}
