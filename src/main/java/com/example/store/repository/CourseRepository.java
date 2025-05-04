package com.example.store.repository;

import com.example.store.model.Course;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByName(String name);

    void deleteById(Integer id);
}
