package com.cybersoft.asimovapi.directors.domain.service;

import com.cybersoft.asimovapi.directors.domain.model.entity.Director;
import com.cybersoft.asimovapi.directors.domain.service.communication.RegisterDirectorRequest;
import com.cybersoft.asimovapi.security.domain.service.communication.AuthenticateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface DirectorService extends UserDetailsService {
    List<Director> getAll();
    Page<Director> getAll(Pageable pageable);
    ResponseEntity<?> authenticate(AuthenticateRequest request);
    ResponseEntity<?> register(RegisterDirectorRequest request);
    Director getById(Long directorId);
    Director create(Director director);
    Director update(Long directorId, Director director);
    ResponseEntity<?> delete(Long directorId);
}
