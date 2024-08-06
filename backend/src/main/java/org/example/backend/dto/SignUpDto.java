package org.example.backend.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String secondName;
    private String password;
    private String email;
}
