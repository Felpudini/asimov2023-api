package com.cybersoft.asimovapi.directors.resource;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuthenticateDirectorResource {
    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String phone;
    private List<String> roles;
    private String token;
}
