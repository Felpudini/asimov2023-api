package com.cybersoft.asimovapi.competences.api;

import com.cybersoft.asimovapi.competences.domain.model.entity.Competence;
import com.cybersoft.asimovapi.competences.domain.service.CompetenceService;
import com.cybersoft.asimovapi.competences.mapping.CompetenceMapper;
import com.cybersoft.asimovapi.competences.resource.CompetenceResource;
import com.cybersoft.asimovapi.competences.resource.CreateCompetenceResource;
import com.cybersoft.asimovapi.competences.resource.UpdateCompetenceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('DIRECTOR') or hasRole('TEACHER')")
public class CompetencesController {

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private CompetenceMapper mapper;

    @GetMapping("competences")
    public List<CompetenceResource> getAllCompetences() {
        return mapper.modelListToResource(competenceService.getAll());
    }

    @GetMapping("competences/{competenceId}")
    public CompetenceResource getCompetenceById(@PathVariable("competenceId") Long competenceId) {
        return mapper.toResource(competenceService.getById(competenceId));
    }

    @GetMapping("courses/{courseId}/competences")
    public List<CompetenceResource> getAllByCourseId(@PathVariable("courseId") Long courseId) {
        return mapper.modelListToResource(competenceService.getAllByCourseId(courseId));
    }

    @PostMapping("competences")
    public CompetenceResource createCompetence(@RequestBody CreateCompetenceResource request) {

        return mapper.toResource(competenceService.create(mapper.toModel(request)));
    }

    @PutMapping("competences/{competenceId}")
    public CompetenceResource updateCompetence(@PathVariable Long competenceId, @RequestBody UpdateCompetenceResource request) {
        return mapper.toResource(competenceService.update(competenceId, mapper.toModel(request)));
    }

    @DeleteMapping("competences/{competenceId}")
    public ResponseEntity<?> deleteCompetence(@PathVariable Long competenceId) {
        return competenceService.delete(competenceId);
    }

}
