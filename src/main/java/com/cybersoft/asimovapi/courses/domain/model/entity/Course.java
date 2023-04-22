package com.cybersoft.asimovapi.courses.domain.model.entity;

import com.cybersoft.asimovapi.competences.domain.model.entity.Competence;
import com.cybersoft.asimovapi.items.domain.model.entity.Item;
import com.cybersoft.asimovapi.shared.domain.model.AuditModel;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "courses")
public class Course extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 120)
    private String name;

    @NotNull
    @NotBlank
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private Boolean state;

    @OneToMany( mappedBy = "course", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Item> items;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "courses_competences",
    joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
    inverseJoinColumns = {@JoinColumn(name = "competence_id", nullable = false)})
    private List<Competence> competences;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "courses")
    private List<Teacher> teachers;

    public void addCompetence(Competence competence) {
        if(this.competences == null)
            this.competences = new ArrayList<>();
        this.competences.add(competence);
    }
}
