package com.example.store.service;

import com.example.store.model.Course;
import com.example.store.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public String createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setPrice(courseDTO.getPrice());
        course.setLessonNumber(Integer.parseInt(courseDTO.getLessonNumber()));

        Course savedCourse = courseRepository.save(course);
        return savedCourse != null ? "Course created successfully" : null;
    }

    public String updateCourse(Course existingCourse, CourseDTO courseDTO) {
        existingCourse.setName(courseDTO.getName());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setLessonNumber(Integer.parseInt(courseDTO.getLessonNumber()));

        Course updatedCourse = courseRepository.save(existingCourse);
        return updatedCourse != null ? "Course updated successfully" : null;
    }

    public String deleteCourse(Course course) {
        courseRepository.delete(course);
        return "Course deleted successfully";
    }
}
