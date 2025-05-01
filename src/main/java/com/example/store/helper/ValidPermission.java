package com.example.store.helper;

import com.example.store.model.*;
import java.util.*;

public class ValidPermission {
    public static boolean hasPermission(User user, String permission) {
        List<String> permissionValues = new ArrayList<>();

        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                String value = permission.getValue();
                if (!permissionValues.contains(value)) {
                    permissionValues.add(value); // tránh trùng lặp
                }
            }
        }
        if (!permissionValues.contains(permission)) {
            return false; // Không có quyền truy cập
        }
        return true; // Có quyền truy cập
    }
}