package com.cybersoft.asimovapi.teachers.middleware;

import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class TeacherDetailsImpl implements UserDetails {

    private Long id;

    private Integer point;

    private String first_name;

    private String last_name;

    private int age;

    private String email;

    @JsonIgnore
    private String password;

    private String phone;

    private Collection<? extends GrantedAuthority> authorities;

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }*/

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        TeacherDetailsImpl user = (TeacherDetailsImpl) other;
        return Objects.equals(id, user.id);
    }

    public static TeacherDetailsImpl build(Teacher user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new TeacherDetailsImpl(user.getId(), user.getPoint(), user.getFirst_name(), user.getLast_name(), user.getAge(), user.getEmail(), user.getPassword(), user.getPhone(), authorities);
    }
}
