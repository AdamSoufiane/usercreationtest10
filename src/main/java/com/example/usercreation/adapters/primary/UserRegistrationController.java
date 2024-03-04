package com.example.usercreation.adapters.primary;

import com.example.usercreation.application.ports.in.UserRegistrationPort;
import com.example.usercreation.application.ports.in.UserRegistrationRequest;
import com.example.usercreation.application.ports.out.UserResponse;
import com.example.usercreation.application.services.UserRegistrationService;
import com.example.usercreation.domain.exceptions.UserRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UserRegistrationController.BASE_URL)
public class UserRegistrationController implements UserRegistrationPort {

    public static final String BASE_URL = "/api/users";

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserResponse response = userRegistrationService.registerUser(request);
            return ResponseEntity.ok(response);
        } catch (UserRegistrationException e) {
            log.error("Registration failed: ", e);
            return ResponseEntity.badRequest().body(new UserResponse(null, e.getMessage()));
        }
    }
}
