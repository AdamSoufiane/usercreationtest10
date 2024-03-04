package com.example.usercreation.application.services;

import com.example.usercreation.application.ports.in.UserRegistrationRequest;
import com.example.usercreation.application.ports.out.UserResponse;
import com.example.usercreation.domain.entities.UserEntity;
import com.example.usercreation.domain.exceptions.ValidationException;
import com.example.usercreation.domain.ports.out.UserRepositoryPort;
import com.example.usercreation.domain.services.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRegistrationService {

    private final UserValidator userValidator;
    private final UserRepositoryPort userRepositoryPort;

    @Autowired
    public UserRegistrationService(UserValidator userValidator, UserRepositoryPort userRepositoryPort) {
        this.userValidator = userValidator;
        this.userRepositoryPort = userRepositoryPort;
    }

    public UserResponse registerUser(UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The user registration request cannot be null.");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(request.getPassword());
        userEntity.setCreatedDate(LocalDateTime.now());

        if (!userValidator.validateUserEntity(userEntity)) {
            throw new ValidationException("User validation failed.");
        }

        UserEntity savedUser = userRepositoryPort.save(userEntity);
        return new UserResponse(savedUser.getId(), "User successfully registered.");
    }
}
