package com.example.store.model;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public Classroom() {
    }

    public Classroom(String name, Integer teacherId, Integer courseId, Integer studentNumber, String startDay, String endDay, String schedule) {
        this.name = name;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.studentNumber = studentNumber;
        this.startDay = startDay;
        this.endDay = endDay;
        this.schedule = schedule;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentNumber() {
        return this.studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStartDay() {
        return this.startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return this.endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

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
    @JsonIgnore
    private Teacher teacher;

    @ManyToMany(mappedBy = "classrooms")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homework> homeworks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Course course;
}