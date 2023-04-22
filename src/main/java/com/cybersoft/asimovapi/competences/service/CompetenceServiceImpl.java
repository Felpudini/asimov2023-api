package com.cybersoft.asimovapi.competences.service;

import com.cybersoft.asimovapi.competences.domain.model.entity.Competence;
import com.cybersoft.asimovapi.competences.domain.persistence.CompetenceRepository;
import com.cybersoft.asimovapi.competences.domain.service.CompetenceService;
import com.cybersoft.asimovapi.courses.domain.persistence.CourseRepository;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CompetenceServiceImpl implements CompetenceService {

    private static final String ENTITY = "Competence";

    private final CompetenceRepository competenceRepository;

    private final CourseRepository courseRepository;
    private final Validator validator;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CourseRepository courseRepository, Validator validator) {
        this.competenceRepository = competenceRepository;
        this.courseRepository = courseRepository;
        this.validator = validator;
    }

    @Override
    public List<Competence> getAll() {
        return competenceRepository.findAll();
    }

    @Override
    public Page<Competence> getAll(Pageable pageable) {
        return competenceRepository.findAll(pageable);
    }

    @Override
    public List<Competence> getAllByCourseId(Long courseId) {
        if(courseRepository.findById(courseId).isEmpty())
            throw new ResourceNotFoundException("Course not found");

        return competenceRepository.getAllCompetencesByCourseId(courseId);
    }

    @Override
    public Competence getById(Long competenceId) {
        return competenceRepository.findById(competenceId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, competenceId));
    }

    @Override
    public Competence create(Competence competence) {
        Set<ConstraintViolation<Competence>> violations = validator.validate(competence);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return competenceRepository.save(competence);
    }

    @Override
    public Competence update(Long competenceId, Competence request) {
        Set<ConstraintViolation<Competence>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return competenceRepository.findById(competenceId).map(competence ->
                competenceRepository.save(
                        competence.withTitle(request.getTitle())
                                .withDescription(request.getDescription()))
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, competenceId));
    }

    @Override
    public ResponseEntity<?> delete(Long competenceId) {
        return competenceRepository.findById(competenceId).map(competence -> {
            competenceRepository.delete(competence);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, competenceId));
    }
}
