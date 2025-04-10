package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClassroomDTO {
    @NotBlank(message = "Classroom name is required")
    private String name;

    @NotBlank(message = "Classroom schedule is required")
    private String schedule;

    @NotBlank(message = "Course is required")
    private String courseId;

    @NotBlank(message = "Teacher is required")
    private String teacherId;

    @NotBlank(message = "Classroom end date is required")
    private String endDay;

    @NotBlank(message = "Classroom start date is required")
    private String startDay;

    @NotBlank(message = "Number of student is required")
    private String studentNumber;

    public ClassroomDTO() {
    }

    public ClassroomDTO(String name, String schedule, String courseId, String teacherId, String endDay, String startDay, String studentNumber) {
        this.name = name;
        this.schedule = schedule;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.endDay = endDay;
        this.startDay = startDay;
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
