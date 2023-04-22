package com.cybersoft.asimovapi.announcements.domain.service;

import com.cybersoft.asimovapi.announcements.domain.model.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllByDirectorId(Long directorId);
    Page<Announcement> getAllByDirectorId(Long directorId, Pageable pageable);
    Announcement create(Long directorId, Announcement request);
    Announcement update(Long announcementId, Announcement request);
    ResponseEntity<?> delete(Long announcementId);
}
