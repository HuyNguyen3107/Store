package com.example.store.model;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@ToString(exclude = "user")
@Entity
@Table(name = "password_tokens")
@Data
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "reset_token", unique = true)
    private String resetToken;

    @NotBlank
    @Size(max = 300)
    @Column(name = "expired")
    private String expired;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    public PasswordToken() {
    }

    public PasswordToken(String resetToken, String expired, Integer userId) {
        this.resetToken = resetToken;
        this.expired = expired;
        this.userId = userId;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getResetToken() {
        return this.resetToken;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getExpired() {
        return this.expired;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}