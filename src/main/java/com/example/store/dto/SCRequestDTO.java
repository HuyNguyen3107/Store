package com.example.store.dto;
import java.util.*;

public class SCRequestDTO {
    private List<String> studentIds;
    private String classroomId;

    private List<String> deleteIds;

    public SCRequestDTO() {
    }

    public SCRequestDTO(List<String> deleteIds) {
        this.deleteIds = deleteIds;
    }

    public SCRequestDTO(List<String> studentIds, String classroomId) {
        this.studentIds = studentIds;
        this.classroomId = classroomId;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public List<String> getDeleteIds() {
        return deleteIds;
    }   

    public void setDeleteIds(List<String> deleteIds) {
        this.deleteIds = deleteIds;
    }
}