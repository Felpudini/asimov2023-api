package com.cybersoft.asimovapi.courses.domain.persistence;

import com.cybersoft.asimovapi.courses.domain.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select d from Course d where d.name = ?1")
    Course findByName(String name);

    @Modifying
    @Query(value = "insert into courses_competences(course_id, competence_id) values (?1, ?2)", nativeQuery = true)
    @Transactional
    void registerCompetenceToCourse(Long courseId, Long CompetenceId);
    @Query(value = "select co.id, co.created_at, co.updated_at, co.name, co.description, co.state from teachers te join teachers_courses tc on te.id = tc.teacher_id join courses co on tc.course_id = co.id where te.id = ?1", nativeQuery = true)
    List<Course> getAllCoursesByTeacherId(Long teacherId);
}
