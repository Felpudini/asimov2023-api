package com.cybersoft.asimovapi.teachers.api;

import com.cybersoft.asimovapi.security.domain.service.communication.AuthenticateRequest;
import com.cybersoft.asimovapi.teachers.domain.service.communication.RegisterTeacherRequest;
import com.cybersoft.asimovapi.teachers.resource.SaveTeacherResource;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import com.cybersoft.asimovapi.teachers.mapping.teacherMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class teachersController {

    private final TeacherService teacherService;

    private final teacherMapper mapper;

    public teachersController(TeacherService teacherService, teacherMapper mapper) {
        this.teacherService = teacherService;
        this.mapper = mapper;
    }

    @GetMapping("directors/{directorId}/teachers")
    @PreAuthorize("hasRole('DIRECTOR')")
    public List<TeacherResource> getTeachersByDirectorId(@PathVariable("directorId") Long directorId){
        return mapper.modelListToResource(teacherService.getAllByDirectorId(directorId));
    }

    @GetMapping("teachers/{teacherId}")
    @PreAuthorize("hasRole('DIRECTOR') or hasRole('TEACHER')")
    public TeacherResource getTeacherById(@PathVariable("teacherId") Long teacherId){
        var directorId = teacherService.getById(teacherId).getDirector().getId();
        var teacher = mapper.toResource(teacherService.getById(teacherId));
        teacher.setDirector_id(directorId);
        //return mapper.toResource(teacherService.getById(teacherId));
        return teacher;
    }
    @PostMapping("teachers/auth/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateRequest request) {
        return teacherService.authenticate(request);
    }

    @PostMapping("teachers/auth/sign-up/{directorId}")
    public ResponseEntity<?> registerUser(@PathVariable Long directorId, @Valid @RequestBody RegisterTeacherRequest request) {
        return teacherService.register(directorId, request);
    }

    @PostMapping("teachers/{teacherId}/courses/{courseId}")
    @PreAuthorize("hasRole('DIRECTOR') or hasRole('TEACHER')")
    public ResponseEntity<?> addCourseToTeacher(@PathVariable Long teacherId, @PathVariable Long courseId) {
        return teacherService.addCourseToTeacher(courseId, teacherId);
    }

    @PutMapping("teachers/{teacherId}")
    @PreAuthorize("hasRole('TEACHER')")
    public TeacherResource updateTeacher(@PathVariable("teacherId") Long teacherId, @RequestBody SaveTeacherResource resource){
        return mapper.toResource(teacherService.updateTeacher(teacherId, mapper.toModel(resource)));
    }

    @DeleteMapping("teachers/{teacherId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteTeacher(@PathVariable("teacherId") Long teacherId){
        return teacherService.deleteTeacher(teacherId);
    }
}
