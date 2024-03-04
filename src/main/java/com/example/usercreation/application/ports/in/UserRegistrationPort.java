package com.example.usercreation.application.ports.in;

import com.example.usercreation.application.ports.out.UserResponse;
import com.example.usercreation.domain.exceptions.UserRegistrationException;

/**
 * Primary port interface that defines the contract for the user registration process.
 */
public interface UserRegistrationPort {

    /**
     * Method to define the contract for user registration.
     * It takes a UserRegistrationRequest and returns a UserResponse.
     * In case of validation errors or registration failure, a UserRegistrationException is thrown.
     *
     * @param registrationRequest the user registration request
     * @return the user response after registration
     * @throws UserRegistrationException if there is a registration failure
     */
    UserResponse registerUser(UserRegistrationRequest registrationRequest) throws UserRegistrationException;

}