package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/documents")
@Validated
public class DocumentController {
    private final DocumentService documentService;
    private final CourseService courseService;

    @Autowired
    public DocumentController(DocumentService documentService, CourseService courseService) {
        this.courseService = courseService;
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Integer id) {
        Document document = documentService.getDocumentById(id);
        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createDocument(@Valid @RequestBody DocumentDTO documentDTO) {
        Integer courseId = Integer.parseInt(documentDTO.getCourseId());
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.badRequest().body("Course not found");
        }
        String status = documentService.createDocument(documentDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error creating document");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDocument(@PathVariable Integer id, @Valid @RequestBody DocumentDTO documentDTO) {
        Document existingDocument = documentService.getDocumentById(id);
        if (existingDocument == null) {
            return ResponseEntity.notFound().build();
        }
        Integer courseId = Integer.parseInt(documentDTO.getCourseId());
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.badRequest().body("Course not found");
        }
        String status = documentService.updateDocument(existingDocument, documentDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error updating document");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Integer id) {
        Document existingDocument = documentService.getDocumentById(id);
        if (existingDocument == null) {
            return ResponseEntity.notFound().build();
        }
        documentService.deleteDocument(existingDocument);
        return ResponseEntity.ok("Document deleted successfully");
    }
}
