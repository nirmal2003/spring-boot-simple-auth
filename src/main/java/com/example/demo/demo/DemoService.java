package com.example.demo.demo;

import com.example.demo.config.JwtService;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DemoService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public DemoService(JwtService jwtService, UserRepository userRepository, UserDTO userDTO) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public UserDTO getUserId(String authHeader) {
        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user does not exits"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.isEnabled());
        return userDTO;
    }

}
