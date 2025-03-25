package com.example.store.model;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "classrooms")
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "teacher_id")
    private Integer teacherId;

    @NotNull
    @Column(name = "course_id", insertable = false, updatable = false)
    private Integer courseId;

    @NotNull
    @Column(name = "student_number")
    private Integer studentNumber;

    @NotBlank
    @Size(max = 50)
    @Column(name = "start_day")
    private String startDay;

    @NotBlank
    @Size(max = 50)
    @Column(name = "end_day")
    private String endDay;

    @NotBlank
    @Size(max = 200)
    @Column(name = "schedule")
    private String schedule;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Teacher teacher;

    @ManyToMany(mappedBy = "classrooms")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homework> homeworks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Course course;
}