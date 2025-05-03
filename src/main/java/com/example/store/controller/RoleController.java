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
@RequestMapping("/roles")
@Validated
public class RoleController {
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final UserService userService;

    @Autowired
    public RoleController(RoleService roleService, RolePermissionService rolePermissionService, UserService userService) {
        this.userService = userService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Role role = roleService.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createRole(@Valid @RequestBody RoleDTO roleDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        String status = roleService.createRole(roleDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create role");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleDTO roleDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Role existingRole = roleService.getRoleById(id);
        if (existingRole == null) {
            return ResponseEntity.notFound().build();
        }

        String status = roleService.updateRole(existingRole, roleDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update role");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Role existingRole = roleService.getRoleById(id);
        if (existingRole == null) {
            return ResponseEntity.notFound().build();
        }

        String status = roleService.deleteRole(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/update-permissions")
    public ResponseEntity<String> updatePermissionsToRole(@Valid @RequestBody RolePermissionDTO rolePermissionDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "roles.update_permissions");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        String status = rolePermissionService.updatePermissionsToRole(rolePermissionDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update permissions for role");
        }
    }
}
