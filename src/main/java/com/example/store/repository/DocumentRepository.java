package com.example.store.repository;

import com.example.store.model.Document;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
