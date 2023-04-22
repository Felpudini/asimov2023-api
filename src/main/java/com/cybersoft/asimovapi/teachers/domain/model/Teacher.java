package com.cybersoft.asimovapi.teachers.domain.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cybersoft.asimovapi.courses.domain.model.entity.Course;
import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import com.cybersoft.asimovapi.security.domain.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Integer point;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String first_name;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String last_name;

    @NotNull
    private Integer age;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotNull
    @NotBlank
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_roles",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "teachers_courses",
            joinColumns = {@JoinColumn(name = "teacher_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", nullable = false)})
    private List<Course> courses;
}
