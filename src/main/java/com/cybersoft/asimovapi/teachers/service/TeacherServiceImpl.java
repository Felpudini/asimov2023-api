package com.cybersoft.asimovapi.teachers.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import com.cybersoft.asimovapi.courses.domain.model.entity.Course;
import com.cybersoft.asimovapi.courses.domain.persistence.CourseRepository;
import com.cybersoft.asimovapi.directors.domain.persistence.DirectorRepository;
import com.cybersoft.asimovapi.security.domain.model.entity.Role;
import com.cybersoft.asimovapi.security.domain.model.enumeration.Roles;
import com.cybersoft.asimovapi.security.domain.persistence.RoleRepository;
import com.cybersoft.asimovapi.security.domain.service.communication.AuthenticateRequest;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import com.cybersoft.asimovapi.shared.mapping.EnhancedModelMapper;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.cybersoft.asimovapi.teachers.domain.repository.TeacherRepository;
import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import com.cybersoft.asimovapi.teachers.domain.service.communication.AuthenticateTeacherResponse;
import com.cybersoft.asimovapi.teachers.domain.service.communication.RegisterTeacherRequest;
import com.cybersoft.asimovapi.teachers.domain.service.communication.RegisterTeacherResponse;
import com.cybersoft.asimovapi.teachers.middleware.JwtHandlerTeacher;
import com.cybersoft.asimovapi.teachers.middleware.TeacherDetailsImpl;
import com.cybersoft.asimovapi.teachers.resource.AuthenticateTeacherResource;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private static final String ENTITY = "Teacher";
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private Validator validator;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHandlerTeacher handler;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EnhancedModelMapper mapper;

    @Override
    public List<Teacher> getAllByDirectorId(Long directorId) {
        var existingDirector = directorRepository.findById(directorId);

        if(existingDirector.isEmpty())
            throw new ResourceNotFoundException("Director", directorId);

        return teacherRepository.findByDirectorId(directorId);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, id));
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = handler.generateToken(authentication);

            TeacherDetailsImpl userDetails = (TeacherDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthenticateTeacherResource resource = mapper.map(userDetails, AuthenticateTeacherResource.class);
            resource.setRoles(roles);
            resource.setToken(token);

            AuthenticateTeacherResponse response = new AuthenticateTeacherResponse(resource);

            return ResponseEntity.ok(response.getResource());


        } catch (Exception e) {
            AuthenticateTeacherResponse response = new AuthenticateTeacherResponse(String.format("An error occurred while authenticating: %s", e.getMessage()));
            return ResponseEntity.badRequest().body(response.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> register(Long directorId, RegisterTeacherRequest request) {
        var director = directorRepository.findById(directorId);
        if(director.isEmpty()) {
            AuthenticateTeacherResponse response = new AuthenticateTeacherResponse("Director not found.");
            return ResponseEntity.badRequest()
                    .body(response.getMessage());
        }

        if (teacherRepository.existsByEmail(request.getEmail())) {
            AuthenticateTeacherResponse response = new AuthenticateTeacherResponse("Email is already used.");
            return ResponseEntity.badRequest()
                    .body(response.getMessage());
        }

        try {

            Set<String> rolesStringSet = request.getRoles();
            Set<Role> roles = new HashSet<>();

            if (rolesStringSet == null) {
                roleRepository.findByName(Roles.ROLE_TEACHER)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Role not found."));
            } else {
                rolesStringSet.forEach(roleString ->
                        roleRepository.findByName(Roles.valueOf(roleString))
                                .map(roles::add)
                                .orElseThrow(() -> new RuntimeException("Role not found.")));
            }

            logger.info("Roles: {}", roles);

            Teacher user = new Teacher()
                    .withPoint(request.getPoint())
                    .withFirst_name(request.getFirst_name())
                    .withLast_name(request.getLast_name())
                    .withAge(request.getAge())
                    .withEmail(request.getEmail())
                    .withPassword(encoder.encode(request.getPassword()))
                    .withPhone(request.getPhone())
                    .withRoles(roles);
            user.setDirector(director.get());


            teacherRepository.save(user);
            TeacherResource resource = mapper.map(user, TeacherResource.class);
            RegisterTeacherResponse response = new RegisterTeacherResponse(resource);
            return ResponseEntity.ok(response.getResource());

        } catch (Exception e) {

            RegisterTeacherResponse response = new RegisterTeacherResponse(e.getMessage());
            return ResponseEntity.badRequest().body(response.getMessage());

        }


    }

    @Override
    public ResponseEntity<?> addCourseToTeacher(Long courseId, Long teacherId) {
        if (courseRepository.findById(courseId).isEmpty())
            throw new ResourceNotFoundException("course not found");
        if (teacherRepository.findById(teacherId).isEmpty())
            throw new ResourceNotFoundException("teacher not found");

        List<Course> courses = courseRepository.getAllCoursesByTeacherId(teacherId);
        if (courses.contains(courseRepository.findById(courseId).get()))
            throw new ResourceNotFoundException("course is already added for this teacher");

        teacherRepository.registerCourseToTeacher(teacherId, courseId);

        return ResponseEntity.ok("course registered for teacher");
    }

    @Override
    public Teacher createTeacher(Long directorId, Teacher request) {
        Set<ConstraintViolation<Teacher>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        var existingEmail = teacherRepository.findByEmail(request.getEmail());
        if(existingEmail != null) {
            throw new ResourceValidationException("email is already taken");
        }

        return directorRepository.findById(directorId).map(director -> {
            request.setDirector(director);
            return teacherRepository.save(request);
        }).orElseThrow(() -> new ResourceNotFoundException("Director", directorId));
    }

    @Override
    public Teacher updateTeacher(Long teacherId, Teacher request) {
        Set<ConstraintViolation<Teacher>> violations = validator.validate(request);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return teacherRepository.findById(teacherId).map(
                teacherP -> teacherRepository.save(
                        teacherP.withFirst_name(request.getFirst_name())
                                .withLast_name(request.getLast_name())
                                .withAge(request.getAge())
                                .withPoint(request.getPoint())
                                .withEmail(request.getEmail())
                                .withPhone(request.getPhone()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, teacherId));
    }

    @Override
    public ResponseEntity<?> deleteTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).map( teacherP -> {
            teacherRepository.delete(teacherP);
            return ResponseEntity.ok().build();
        })
        .orElseThrow(() -> new ResourceNotFoundException(ENTITY, teacherId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher user = teacherRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));
        return TeacherDetailsImpl.build(user);
    }
}
