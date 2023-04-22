package com.cybersoft.asimovapi.teachers.resource;

import lombok.Data;

import java.util.List;

@Data
public class TeacherResource {
    private Long id;
    private String first_name;
    private String last_name;
    private Integer point;
    private Integer age;
    private String email;
    private String phone;
    private Long director_id;
    private List<String> roles;
}
