package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "email", "role", "enabled"})
public class UserDTO {
    private Long Id;
    private String name;
    private String email;
    private Role role;
    private Boolean enabled;
}
