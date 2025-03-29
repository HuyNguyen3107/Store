package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseDTO {
    @NotBlank(message = "Course name is required")
    private String name;

    @NotBlank(message = "Course price is required")
    @Size(min = 1, message = "Course price should be at least 1 character long")
    private String price;

    @NotBlank(message = "Lesson number is required")
    @Size(min = 1, message = "Lesson number should be at least 1 character long")
    private String lessonNumber;

    public CourseDTO() {
    }

    public CourseDTO(String name, String price, String lessonNumber) {
        this.name = name;
        this.price = price;
        this.lessonNumber = lessonNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLessonNumber() {
        return this.lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }
}
