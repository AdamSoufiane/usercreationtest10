package com.example.usercreation.application.ports.out;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserResponse {

    private Long userId;
    private String message;

    public UserResponse(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    // equals and hashCode methods are handled by Lombok @EqualsAndHashCode
    // toString method is handled by Lombok @ToString
}
