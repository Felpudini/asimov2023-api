package com.cybersoft.asimovapi.teachers.domain.service;

import com.cybersoft.asimovapi.security.domain.service.communication.AuthenticateRequest;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.cybersoft.asimovapi.teachers.domain.service.communication.RegisterTeacherRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService extends UserDetailsService {
    List<Teacher> getAllByDirectorId(Long directorId);
    Teacher getById(Long id);
    ResponseEntity<?> authenticate(AuthenticateRequest request);
    ResponseEntity<?> register(Long directorId, RegisterTeacherRequest request);
    public ResponseEntity<?> addCourseToTeacher(Long courseId, Long teacherId);
    Teacher createTeacher(Long directorId, Teacher teacher);
    Teacher updateTeacher(Long teacherId, Teacher teacher);
    ResponseEntity<?> deleteTeacher(Long teacherId);
}

