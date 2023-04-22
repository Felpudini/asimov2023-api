package com.cybersoft.asimovapi.announcements.domain.persistence;

import com.cybersoft.asimovapi.announcements.domain.model.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByDirectorId(Long directorId);
    Page<Announcement> findByDirectorId(Long directorId, Pageable pageable);
}
