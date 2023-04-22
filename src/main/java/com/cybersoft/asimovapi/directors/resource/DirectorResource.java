package com.cybersoft.asimovapi.directors.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DirectorResource {

    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String phone;
    private List<String> roles;
}
