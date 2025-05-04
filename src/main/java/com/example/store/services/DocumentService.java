package com.example.store.service;

import com.example.store.model.Document;
import com.example.store.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    public String createDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setTitle(documentDTO.getTitle());
        document.setDescription(documentDTO.getDescription());
        document.setCourseId(Integer.parseInt(documentDTO.getCourseId()));
        document.setContent(documentDTO.getContent());

        Document savedDocument = documentRepository.save(document);
        return savedDocument != null ? "Document created successfully" : null;
    }

    public String updateDocument(Document existingDocument, DocumentDTO documentDTO) {
        existingDocument.setTitle(documentDTO.getTitle());
        existingDocument.setDescription(documentDTO.getDescription());
        existingDocument.setCourseId(Integer.parseInt(documentDTO.getCourseId()));
        existingDocument.setContent(documentDTO.getContent());

        Document updatedDocument = documentRepository.save(existingDocument);
        return updatedDocument != null ? "Document updated successfully" : null;
    }

    public String deleteDocument(Document existingDocument) {
        documentRepository.delete(existingDocument);
        return "Document deleted successfully";
    }

    public String updateCourseIdToNull(int[] documentIds) {
        for (int documentId : documentIds) {
            Document document = documentRepository.findById(documentId).orElse(null);
            if (document != null) {
                document.setCourseId(null);
                documentRepository.save(document);
            }
        }
        return "Course IDs updated to null successfully";
    }
}
