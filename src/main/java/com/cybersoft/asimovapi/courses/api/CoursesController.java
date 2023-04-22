package com.cybersoft.asimovapi.courses.api;

import com.cybersoft.asimovapi.competences.mapping.CompetenceMapper;
import com.cybersoft.asimovapi.competences.resource.CompetenceResource;
import com.cybersoft.asimovapi.courses.domain.service.CourseService;
import com.cybersoft.asimovapi.courses.mapping.CourseMapper;
import com.cybersoft.asimovapi.courses.resource.CourseResource;
import com.cybersoft.asimovapi.courses.resource.CreateCourseResource;
import com.cybersoft.asimovapi.courses.resource.UpdateCourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('DIRECTOR') or hasRole('TEACHER')")
public class CoursesController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper mapper;

    @Autowired
    private CompetenceMapper competenceMapper;

    @GetMapping("courses")
    public List<CourseResource> getAllCourses() {
        return mapper.modelListToResource(courseService.getAll());
    }

    @GetMapping("teachers/{teacherId}/courses")
    public List<CourseResource> getAllByTeacherId(@PathVariable("teacherId") Long teacherId) {
        return mapper.modelListToResource(courseService.getAllByTeacherId(teacherId));
    }

    @GetMapping("courses/{courseId}")
    public CourseResource getCourseById(@PathVariable("courseId") Long courseId) {
        return mapper.toResource(courseService.getById(courseId));
    }

    @PostMapping("courses")
    public CourseResource createCourse(@RequestBody CreateCourseResource request) {

        return mapper.toResource(courseService.create(mapper.toModel(request)));
    }

    @PostMapping("courses/{courseId}/competence/{competenceId}")
    public ResponseEntity<?> addCompetenceToCourse(@PathVariable Long courseId, @PathVariable Long competenceId) {
        return courseService.addCompetenceToCourse(competenceId, courseId);
    }

    @PutMapping("courses/{courseId}")
    public CourseResource updateCourse(@PathVariable Long courseId, @RequestBody UpdateCourseResource request) {
        return mapper.toResource(courseService.update(courseId, mapper.toModel(request)));
    }

    @DeleteMapping("courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        return courseService.delete(courseId);
    }
}
