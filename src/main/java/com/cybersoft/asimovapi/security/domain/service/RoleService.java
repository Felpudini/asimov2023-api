package com.cybersoft.asimovapi.security.domain.service;

import com.cybersoft.asimovapi.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {
    void seed();
    List<Role> getAll();
}
