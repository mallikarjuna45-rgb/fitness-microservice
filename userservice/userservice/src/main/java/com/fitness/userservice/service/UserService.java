package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exception.AlreadyExists;
import com.fitness.userservice.model.User;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.rmi.AlreadyBoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new AlreadyExists("Email already exists");
        }
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();
        User saved =userRepository.save(user);
        UserResponse userResponse =UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .password(saved.getPassword())
                .email(saved.getEmail())
                .createdDate(saved.getCreatedDate())
                .updatedDate(saved.getUpdatedDate())
                .build();
        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User saved =userRepository.findById(userId).orElseThrow(()->new AlreadyExists("User not found"));
        UserResponse userResponse =UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .password(saved.getPassword())
                .email(saved.getEmail())
                .createdDate(saved.getCreatedDate())
                .updatedDate(saved.getUpdatedDate())
                .build();
        return userResponse;
    }

    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }
}
