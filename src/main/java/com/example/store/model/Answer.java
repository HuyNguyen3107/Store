package com.example.store.model;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@ToString(exclude = {"user", "homework"})
@Entity
@Table(name = "answers")
@Data

public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank
    private String content;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "homework_id")
    private Integer homeworkId;

    public Answer() {
    }

    public Answer(String content, Integer userId, Integer homeworkId) {
        this.content = content;
        this.userId = userId;
        this.homeworkId = homeworkId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHomeworkId() {
        return this.homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Homework homework;
}

