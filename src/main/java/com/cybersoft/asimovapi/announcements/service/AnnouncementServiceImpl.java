package com.cybersoft.asimovapi.announcements.service;

import com.cybersoft.asimovapi.announcements.domain.model.entity.Announcement;
import com.cybersoft.asimovapi.announcements.domain.persistence.AnnouncementRepository;
import com.cybersoft.asimovapi.announcements.domain.service.AnnouncementService;
import com.cybersoft.asimovapi.directors.domain.persistence.DirectorRepository;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final String ENTITY = "Announcement";

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private Validator validator;

    @Override
    public List<Announcement> getAllByDirectorId(Long directorId) {
        var existingDirector = directorRepository.findById(directorId);

        if(existingDirector.isEmpty())
            throw new ResourceNotFoundException("Director", directorId);

        return announcementRepository.findByDirectorId(directorId);
    }

    @Override
    public Page<Announcement> getAllByDirectorId(Long directorId, Pageable pageable) {
        return announcementRepository.findByDirectorId(directorId, pageable);
    }

    @Override
    public Announcement create(Long directorId, Announcement request) {
        Set<ConstraintViolation<Announcement>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return directorRepository.findById(directorId).map(director -> {
            request.setDirector(director);
            return announcementRepository.save(request);
        }).orElseThrow(() -> new ResourceNotFoundException("Director", directorId));
    }

    @Override
    public Announcement update(Long announcementId, Announcement request) {
        Set<ConstraintViolation<Announcement>> violations = validator.validate(request);

        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return announcementRepository.findById(announcementId).map(announcement ->
                announcementRepository.save(
                        announcement.withTitle(request.getTitle())
                                .withDescription(request.getDescription()))
                ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, announcementId));
    }

    @Override
    public ResponseEntity<?> delete(Long announcementId) {
        return announcementRepository.findById(announcementId).map(announcement -> {
                announcementRepository.delete(announcement);
                return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, announcementId));
    }
}
