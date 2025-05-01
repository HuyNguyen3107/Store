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
@RequestMapping("/permissions")
@Validated
public class PermissionController {
    private final PermissionService permissionService;
    private final UserService userService;

    @Autowired
    public PermissionController(PermissionService permissionService, UserService userService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "permissions.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "permissions.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Permission permission = permissionService.getPermissionById(id);
        if (permission != null) {
            return ResponseEntity.ok(permission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPermission(@Valid @RequestBody PermissionDTO permissionDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "permissions.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        String status = permissionService.createPermission(permissionDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create permission");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePermission(@PathVariable Integer id, @Valid @RequestBody PermissionDTO permissionDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "permissions.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Permission existingPermission = permissionService.getPermissionById(id);
        if (existingPermission == null) {
            return ResponseEntity.notFound().build();
        }

        String status = permissionService.updatePermission(existingPermission, permissionDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update permission");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "permissions.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Permission existingPermission = permissionService.getPermissionById(id);
        if (existingPermission == null) {
            return ResponseEntity.notFound().build();
        }

        String status = permissionService.deletePermission(existingPermission);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete permission");
        }
    }
}
