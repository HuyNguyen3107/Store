package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.helper.*;
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
    private final UserService userService;

    @Autowired
    public DocumentController(DocumentService documentService, CourseService courseService, UserService userService) {
        this.userService = userService;
        this.courseService = courseService;
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "documents.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "documents.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Document document = documentService.getDocumentById(id);
        if (document != null) {
            return ResponseEntity.ok(document);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createDocument(@Valid @RequestBody DocumentDTO documentDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "documents.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> updateDocument(@PathVariable Integer id, @Valid @RequestBody DocumentDTO documentDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "documents.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> deleteDocument(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "documents.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Document existingDocument = documentService.getDocumentById(id);
        if (existingDocument == null) {
            return ResponseEntity.notFound().build();
        }
        documentService.deleteDocument(existingDocument);
        return ResponseEntity.ok("Document deleted successfully");
    }
}
