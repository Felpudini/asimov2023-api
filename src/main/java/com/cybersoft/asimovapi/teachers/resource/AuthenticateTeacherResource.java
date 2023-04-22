package com.cybersoft.asimovapi.teachers.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuthenticateTeacherResource {
    private Long id;
    private Integer point;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String phone;
    private List<String> roles;
    private String token;
}
