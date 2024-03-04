package com.example.usercreation.domain.services;

import com.example.usercreation.domain.entities.UserEntity;
import com.example.usercreation.domain.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public void validateUserEntity(UserEntity userEntity) throws ValidationException {
        if (userEntity == null) {
            handleValidationFailure("UserEntity cannot be null");
        }
        validateEmail(userEntity.getEmail());
        validatePassword(userEntity.getPassword());
        validateName(userEntity.getName());
        if (userEntity.getCreatedDate() == null) {
            handleValidationFailure("UserEntity must have a creation date");
        }
    }

    public void validateEmail(String email) throws ValidationException {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            handleValidationFailure("Email format is invalid");
        }
    }

    public void validatePassword(String password) throws ValidationException {
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            handleValidationFailure("Password does not meet strength requirements");
        }
    }

    public void validateName(String name) throws ValidationException {
        if (StringUtils.isBlank(name)) {
            handleValidationFailure("Name cannot be blank");
        }
    }

    private void handleValidationFailure(String message) throws ValidationException {
        throw new ValidationException(message);
    }
}
