package com.example.store.repository;

import com.example.store.model.StudentClassroom;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Integer> {
}