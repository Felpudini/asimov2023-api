package com.cybersoft.asimovapi.competences.domain.persistence;

import com.cybersoft.asimovapi.competences.domain.model.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    @Query(value = "select com.id, com.created_at, com.updated_at, com.title, com.description from courses cor join courses_competences cc on cor.id = cc.course_id join competences com on cc.competence_id = com.id where cor.id = ?1", nativeQuery = true)
    List<Competence> getAllCompetencesByCourseId(Long courseId);
}
