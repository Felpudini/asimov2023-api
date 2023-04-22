package com.cybersoft.asimovapi.directors.domain.model.entity;

import com.cybersoft.asimovapi.announcements.domain.model.entity.Announcement;
import com.cybersoft.asimovapi.security.domain.model.entity.Role;
import com.cybersoft.asimovapi.shared.domain.model.AuditModel;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "directors")
public class Director extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String first_name;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String last_name;

    private int age;

    @NotNull
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 20)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "director_roles",
            joinColumns = @JoinColumn(name = "director_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //relation with announcements
    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Announcement> announcements;

    //relation with teachers
    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Teacher> teachers;
}
